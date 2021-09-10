package xyz.tahakhan.badworddetector.listeners;

import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;


@Component
@Log4j2
public class ReadyListener implements EventListener {

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof ReadyEvent) {
            log.info("Ready!");
        }
    }
}
