package xyz.tahakhan.badworddetector.listeners;

import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.tahakhan.badworddetector.logic.BadWordProcessor;

import java.io.IOException;
import java.util.Locale;
import java.util.Random;

@Component
@Log4j2
public class BadMessageListener implements EventListener {

    private static final String[] names = { "PITA", "dumbass", "nerd" };
    private static final Random RANDOM = new Random();

    private final BadWordProcessor badWordSet;

    @Autowired
    public BadMessageListener(BadWordProcessor badWordProcessor) {
        this.badWordSet = badWordProcessor;
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (!isMessageEvent(event)) {
            return;
        }

        MessageReceivedEvent messageEvent = (MessageReceivedEvent) event;
        String message = messageEvent.getMessage().getContentRaw();
        Member member = messageEvent.getMessage().getMember();

        if (member == null) {
            log.info("Could not retrieve member info, event::{}", messageEvent.getMessageId());
            return;
        }
        if (isUserABot(member)) {
            log.info("Message is from bot ignoring, event:: {}", messageEvent.getMessageId());
            return;
        }

        String username = member.getEffectiveName();
        try {
            if (badWordSet.containsBadWord(message.toLowerCase(Locale.ROOT))) {
                log.info("Bad word found! User::{}, message:: {}", username, message);
                sendBadReply(member, messageEvent);
            } else {
                log.info("Message did not contain a bad word, user::{}, message:: {}", username, message);
            }
        } catch (IOException e) {
            log.error("Unable to read message.", e);
        }
    }

    private boolean isMessageEvent(@NotNull GenericEvent event) {
        return event instanceof MessageReceivedEvent;
    }

    private void sendBadReply(Member member, MessageReceivedEvent event) {
        event.getChannel().sendMessage(buildMessage(member.getId())).queue();
    }

    private String buildMessage(String id) {
        return "<@" + id + ">, stop being a " + pickBadName() + ", no swearing here.";
    }

    private String pickBadName() {
        return names[RANDOM.nextInt(names.length)];
    }

    private void sendGoodReply(MessageReceivedEvent event) {
        event.getChannel().sendMessage("ok").queue();
    }

    private boolean isUserABot(Member member) {
        String username = member.getEffectiveName().toLowerCase(Locale.ROOT);
        return username.contains("bot") || username.contains("detector");
    }
}
