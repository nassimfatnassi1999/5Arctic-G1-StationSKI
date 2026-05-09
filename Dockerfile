# Utilise une image officielle Java Runtime comme image de base
FROM alpine:3.18

RUN apk add openjdk17-jre
# Télécharge le .jar depuis Nexus
#RUN curl -f -o app.jar "http://192.168.33.11:8081/repository/maven-releases/tn/esprit/spring/gestion-station-ski/1.0/gestion-station-ski-1.0.jar"

# Vérifie que le fichier JAR est bien téléchargé
#RUN test -f app.jar || (echo "Le fichier app.jar n'a pas pu être téléchargé" && exit 1)

# Expose le port sur lequel l'application écoute
#EXPOSE 9001

# Commande pour lancer l'application
ADD target/5Arctic-G1-StationSKI.jar  StationSKI.jar
ENTRYPOINT ["java", "-jar", "StationSKI.jar"]
