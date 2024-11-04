# Utilise une image officielle Java Runtime comme image de base
FROM eclipse-temurin:17-jre-jammy

# Fixe le répertoire de travail
WORKDIR /app

# Installe curl pour télécharger le fichier JAR
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Télécharge le .jar depuis Nexus
RUN curl -f -o app.jar "http://192.168.33.11:8081/repository/maven-releases/tn/esprit/spring/gestion-station-ski/1.1/gestion-station-ski-1.1.jar"

# Vérifie que le fichier JAR est bien téléchargé
RUN test -f app.jar || (echo "Le fichier app.jar n'a pas pu être téléchargé" && exit 1)

# Expose le port sur lequel l'application écoute
EXPOSE 9001

# Commande pour lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
