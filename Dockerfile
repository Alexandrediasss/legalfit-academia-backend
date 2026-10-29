# Receita para o Render (ou qualquer servidor com Docker) rodar a aplicação.
# Ela tem duas etapas: primeiro compila o projeto, depois roda só o resultado.

# ===== Etapa 1: compilar =====
# Imagem com o JDK 17 (Java completo, com compilador)
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copia o Maven Wrapper e o pom.xml primeiro e baixa as dependências.
# Assim o Docker reaproveita esse passo quando só o código muda.
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw -B dependency:go-offline

# Copia o código e gera o .jar (sem rodar os testes, para ficar mais rápido)
COPY src src
RUN ./mvnw -B package -DskipTests

# ===== Etapa 2: rodar =====
# Imagem só com o JRE 17 (Java para rodar, sem compilador) - fica bem menor
FROM eclipse-temurin:17-jre
WORKDIR /app

# Pega apenas o .jar gerado na etapa 1
COPY --from=build /app/target/*.jar app.jar

# O plano grátis do Render tem 512 MB de memória: limitamos o Java a 75% disso
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75"

# Porta usada quando a variável PORT não existir (o Render define a PORT sozinho)
EXPOSE 8081

CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
