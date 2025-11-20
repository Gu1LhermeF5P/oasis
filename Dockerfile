# 1. Imagem base com Java 17
FROM eclipse-temurin:17-jdk-alpine

# 2. Define pasta de trabalho
WORKDIR /app

# 3. Copia o arquivo .jar gerado para dentro do container
# (O nome do jar deve bater com o que está no seu target)
COPY target/oasis-core-1.0.0.jar app.jar

# 4. Expõe a porta 8080
EXPOSE 8080

# 5. Comando para rodar
ENTRYPOINT ["java", "-jar", "app.jar"]