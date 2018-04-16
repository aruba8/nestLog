package ca.codeless;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class ScheduledRunner {


    private static final Logger log = LoggerFactory.getLogger(ScheduledRunner.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${authToken}")
    private String token;

    @Value("${url}")
    private String url;

    @Value("${thermostatId}")
    private String thermostatId;

    private final MongoRepo mongoRepo;

    @Autowired
    public ScheduledRunner(MongoRepo mongoRepo) {
        this.mongoRepo = mongoRepo;
    }

    @Scheduled(fixedRate = 5000)
    public void scheduledRequest() {
        JSONObject resp = sendRequest();
        saveData(resp);
        log.info("Log saved {} ", dateFormat.format(new Date()));
    }


    private JSONObject sendRequest() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", token);
        JSONObject result = null;
        try {
            HttpResponse<JsonNode> resp = Unirest.get(url + thermostatId + "/").headers(headers).asJson();
            if (resp.getStatus() == 200) {
                log.info("Got nest data {}", dateFormat.format(new Date()));
                result = resp.getBody().getObject();
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return result;
    }

    private void saveData(JSONObject data) {
        mongoRepo.saveData(data);
    }


}
