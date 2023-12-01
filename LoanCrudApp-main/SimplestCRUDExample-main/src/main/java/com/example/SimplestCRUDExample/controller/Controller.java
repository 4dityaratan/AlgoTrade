package com.example.SimplestCRUDExample.controller;

import com.example.SimplestCRUDExample.Model.Data;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controller {

    Map<String, Object> metaData;
    Map<String, Object> timeSeries;

    @GetMapping("/alphavantage-data")
    public Mono<ResponseEntity<?>> getAlphaVantageData() {
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=RELIANCE.BSE&outputsize=full&apikey=demo";

        WebClient webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(5 * 1024 * 1024))
                .build();

        Mono<ResponseEntity<?>> responseEntityMono = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .map(responseBody -> {

                    System.out.println(responseBody);
                    if (responseBody != null) {
                        // Process the raw JSON object if needed
                        metaData = (Map<String, Object>) responseBody.get("Meta Data");
                        timeSeries = (Map<String, Object>) responseBody.get("Time Series (Daily)");

                        metaData.get("2. Symbol");
                        metaData.get("3. Last Refreshed");

                        int count = 0;
                        ArrayList<Object> dataList = new ArrayList<>();
                        for (Map.Entry<String, Object> entry : timeSeries.entrySet()) {
                            if(count==10)break;
                            Map<String, Object> dailyData = (Map<String, Object>) entry.getValue();
                            Object high = dailyData.get("2. high");
                            Object open = dailyData.get("1. open");
                            Object low = dailyData.get("3. low");
                            Object close = dailyData.get("4. close");
                            Object volume = dailyData.get("5. volume");
                            Data data = Data.builder()
                                    .high((String)high)
                                    .low((String)low)
                                    .open((String)open)
                                    .close((String)close)
                                    .volume((String)volume)
                                    .build();

                            dataList.add(data);

                            count++;
                        }

                        return ResponseEntity.ok(responseBody);
                    } else {
                        return ResponseEntity.status(500).body("Error retrieving data from Alpha Vantage");
                    }
                })
                .doOnNext(updatedResponseEntity -> {
                    // Additional operations can be added here
                    // For example, modifying the headers, status code, etc.
                    System.out.println(updatedResponseEntity);
                });
        return responseEntityMono;
    }
}
