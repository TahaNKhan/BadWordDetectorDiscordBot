package xyz.tahakhan.badworddetector.logic;

import com.google.common.collect.ImmutableList;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.tahakhan.badworddetector.utils.StreamUtils;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Component
public class BadWordProcessor {
    private static final int SECONDS_IN_MINUTE = 60;
    private List<String> badWordList;

    private Instant nextRefreshTime;

    private final HttpClient webClient;

    @Autowired
    public BadWordProcessor(HttpClient httpClient) {
        this.webClient = httpClient;
    }

    private static final Object lock = new Object();

    private void refreshWordSet() throws IOException {
        String url = "http://www.bannedwordlist.com/lists/swearWords.csv";
        HttpGet getRequest = new HttpGet(url);
        HttpResponse response;
        do {
            response = webClient.execute(getRequest);
        } while (response.getStatusLine().getStatusCode() != 200);
        String responseAsString = StreamUtils.readAsString(response.getEntity().getContent());
        badWordList = ImmutableList.copyOf(responseAsString.split(","));
        nextRefreshTime = Instant.now().plusSeconds(5 * SECONDS_IN_MINUTE);
    }

    public boolean containsBadWord(String sentence) throws IOException {
        synchronized (lock) {
            if (badWordList == null || Instant.now().isAfter(nextRefreshTime)) {
                refreshWordSet();
            }
        }

        for (String word: badWordList) {
            if (sentence.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
