package xyz.tahakhan.badworddetector;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.tahakhan.badworddetector.auth.TokenGrabber;
import xyz.tahakhan.badworddetector.listeners.BadMessageListener;
import xyz.tahakhan.badworddetector.listeners.ReadyListener;

@Component
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

}
