package xyz.tahakhan.badworddetector.auth;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Log4j2
public class TokenGrabber {
    public String getToken() {
        log.info("Obtaining token from env variables");
        String token = System.getenv("DiscordBot.BadWordDetector.Token");
        if (!StringUtils.hasText(token))
        {
            log.error("Unable to obtain token from System Variables, make sure the bot token value is set under the \"DiscordBot.BadWordDetector.Token\" environment variable.");
            throw new IllegalStateException("No token found in environment variables.");
        }

        log.info("Token obtained");
        return token;
    }
}
