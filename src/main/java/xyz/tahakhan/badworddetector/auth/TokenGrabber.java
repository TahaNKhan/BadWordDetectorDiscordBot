package xyz.tahakhan.badworddetector.auth;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Log4j2
public class TokenGrabber {
    private static final String TOKEN_KEY = "DiscordBot_BadWordDetector_Token";
    public String getToken() {
        log.info("Obtaining token from env variables");
        String token = System.getenv(TOKEN_KEY);
        if (!StringUtils.hasText(token))
        {
            log.error("Unable to obtain token from System Variables, make sure the bot token value is set under the \""+ TOKEN_KEY  + "\" environment variable.");
            throw new IllegalStateException("No token found in environment variables.");
        }

        log.info("Token obtained");
        return token;
    }
}
