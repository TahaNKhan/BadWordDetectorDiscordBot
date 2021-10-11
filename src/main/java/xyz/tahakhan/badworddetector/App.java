package xyz.tahakhan.badworddetector;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class App implements CommandLineRunner {

    @Autowired
    private BadWordDetector badWordDetector;

    public static void main(String[] args) throws Exception {
        log.info("STARTING THE APPLICATION");

        SpringApplication app = new SpringApplication(App.class);
        // disable spring banner
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String[] args) {
        log.info("EXECUTING : Starting the bot...");
        badWordDetector.initialize();

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    public void shutdown() {
        badWordDetector.shutdown();
        log.info("APPLICATION FINISHED!");
    }
}
