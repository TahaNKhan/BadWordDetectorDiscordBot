package xyz.tahakhan.badworddetector;

import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.tahakhan.badworddetector.auth.TokenGrabber;
import xyz.tahakhan.badworddetector.listeners.BadMessageListener;
import xyz.tahakhan.badworddetector.listeners.ReadyListener;

import javax.security.auth.login.LoginException;

@Component
@Log4j2
public class BadWordDetector {

    private JDA jda;
    private final TokenGrabber tokenGrabber;
    private final ReadyListener readyListener;
    private final BadMessageListener badMessageListener;

    @Autowired
    public BadWordDetector(TokenGrabber tokenGrabber, ReadyListener readyListener,
                           BadMessageListener badMessageListener) {
        this.tokenGrabber = tokenGrabber;
        this.badMessageListener = badMessageListener;
        this.readyListener = readyListener;
    }

    public void initialize() {
        try {
            jda = JDABuilder.createDefault(tokenGrabber.getToken())
                    .addEventListeners(readyListener, badMessageListener)
                    .build();

            jda.awaitReady();
        } catch (InterruptedException | LoginException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void shutdown() {
        log.info("Shutting down! Closing connection.");
        jda.shutdownNow();
        log.info("Finished closing connection.");
    }
}
