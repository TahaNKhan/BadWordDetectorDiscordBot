#!/bin/sh

# Get latest
git pull origin main

# Cleand an build
mvn clean package spring-boot:repackage
java -jar target/BadWordDetectorDiscordBot-1.0-SNAPSHOT.jar