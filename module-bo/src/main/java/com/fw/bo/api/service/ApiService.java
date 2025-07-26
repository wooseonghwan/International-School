package com.fw.bo.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fw.core.dto.bo.ApiIntegrateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiService {

    @Value("${api.url}")
    private String API_DOMAIN;

    private final RestTemplate restTemplate;

    public void integrate(boolean isSell, List<ApiIntegrateDTO> integrateDTOList) {
        String endpoint = isSell ? API_DOMAIN + "/api/integrate/open" : API_DOMAIN + "/api/integrate/block";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("body", integrateDTOList);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(reqMap, headers);

            ResponseEntity<Map> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, requestEntity, Map.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> responseBody = responseEntity.getBody();
                if (responseBody != null) {
                    String message = (String) responseBody.get("message");
                    String code = (String) responseBody.get("code");
                    Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
                    long time = ((Number) responseBody.get("time")).longValue();

                    log.info("Message: " + message);
                    log.info("Code: " + code);
                    log.info("Data: " + data);
                    log.info("Time: " + time);

                    // Example processing
                    if ("SUCCESS".equalsIgnoreCase(message) && "200".equals(code)) {
                        log.info("SUCCESS");
                    }
                }
            } else {
                log.info("Failed to integrate. Status code: " + responseEntity.getStatusCode());
                throw new RuntimeException();
            }
        } catch (Exception e) {
            log.error("Error occurred when integrating", e);
            throw new RuntimeException();
        }
    }

}
