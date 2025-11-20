# --- Estágio 1: Build (Construção) ---
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o arquivo de dependências
COPY pom.xml .

# Baixa as dependências (para cachear e ser mais rápido nas próximas vezes)
# Nota: Se der erro de dependência, pode comentar a linha abaixo, mas ela ajuda na velocidade
RUN mvn dependency:go-offline -B

# Copia todo o código fonte
COPY src ./src

# Compila o projeto e gera o .jar (pula testes para ser rápido)
RUN mvn clean package -DskipTests

# --- Estágio 2: Runtime (Execução) ---
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copia o .jar gerado no estágio anterior para a imagem final
# O nome do arquivo gerado pode variar, usamos asterisco para garantir
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta
EXPOSE 8080

# Comando para rodar
ENTRYPOINT ["java", "-jar", "app.jar"]