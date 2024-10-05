# Utiliser une image OpenJDK Runtime comme image parent
FROM openjdk:17-jdk-alpine

# Copier le fichier JAR généré dans le répertoire de travail
COPY target/5Arctic-G1-StationSKI.jar .

# Exposer le port sur lequel l'application va écouter
EXPOSE 9012

# Commande pour exécuter l'application Spring Boot
ENTRYPOINT ["java", "-jar", "5Arctic-G1-StationSKI.jar"]
