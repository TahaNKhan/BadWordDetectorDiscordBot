package xyz.tahakhan.badworddetector;

import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.tahakhan.badworddetector.auth.TokenGrabber;
import xyz.tahakhan.badworddetector.listeners.BadMessageListener;
import xyz.tahakhan.badworddetector.listeners.ReadyListener;

@Component
@Log4j2
public class BadWordDetector {

    private JDA jda;

    @Autowired
    private TokenGrabber tokenGrabber;

    @Autowired
    private ReadyListener readyListener;

    @Autowired
    private BadMessageListener badMessageListener;

    public void initialize() {
        try {
            jda = JDABuilder.createDefault(tokenGrabber.getToken())
                    .addEventListeners(readyListener)
                    .build();

            jda.awaitReady();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void initializeBadMessageListener() {
        jda.addEventListener(badMessageListener);
    }

    public boolean isRunning() {
        return jda.getStatus().isInit();
    }

    public void shutdown() {
        log.info("Shutting down! Closing connection.");
        System.out.println("SHUTTING DOWN!");
        jda.shutdownNow();
        log.info("Finished closing connection.");
    }
}
