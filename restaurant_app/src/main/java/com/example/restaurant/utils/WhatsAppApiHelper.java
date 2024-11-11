package com.example.restaurant.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.restaurant.service.OrderService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.restaurant.AppConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class WhatsAppApiHelper {
	
    private static final Logger logger = LoggerFactory.getLogger(WhatsAppApiHelper.class);

	
    @Value("${whatsapp.api.url}")
    private String apiUrl;

    @Value("${whatsapp.api.token}")
    private String apiToken;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    @Autowired
    public WhatsAppApiHelper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
		this.objectMapper = new ObjectMapper();
    }

    public WhatsAppApiHelper(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Send a message to a WhatsApp contact.
     */
    public JsonNode sendMessage(String phoneNumber, String message) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiToken);

            // Define the payload as a String
            String payload = String.format(
                "{ \"messaging_product\": \"whatsapp\", \"to\": \"%s\", \"type\": \"text\", \"text\": { \"body\": \"%s\" } }",
                phoneNumber, message
            );

            // Specify <String> in HttpEntity
            HttpEntity<String> request = new HttpEntity<String>(payload, headers);

            // Send the request
            String response = restTemplate.postForObject(apiUrl, request, String.class);
            return objectMapper.readTree(response);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Process incoming messages.
     */
    public void processIncomingMessage(String incomingJson, OrderService orderService) {
        try {
        	
            logger.debug("Processing incoming message JSON: {}", incomingJson);

            JsonNode jsonNode = objectMapper.readTree(incomingJson);
            String phoneNumber = jsonNode.get("from").asText();
            String message = jsonNode.get("text").get("body").asText();

            // Assume that customers send their orders as text
            orderService.createOrder(phoneNumber, message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
