# Bad Word Detector Discord Bot

## Pre-reqs

Make sure you have the following installed:
1. Java 11
2. Maven

And have the following set as an environment variable:
- Key: `DiscordBot_BadWordDetector_Token` -- Value: `{bot-token-here}`

## How to run?

1. Run `mvn clean package spring-boot:repackage && java -jar target/BadWordDetectorDiscordBot-1.0-SNAPSHOT.jar`
