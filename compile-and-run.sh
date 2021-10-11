#!/bin/sh
# Create a log directory if it doesn't exist
if [ ! -d "$HOME/logs" ]; then
  mkdir "$HOME/logs"
fi

# Get latest
git pull origin main

# Cleand an build
mvn clean package spring-boot:repackage
java -jar target/BadWordDetectorDiscordBot-1.0-SNAPSHOT.jar