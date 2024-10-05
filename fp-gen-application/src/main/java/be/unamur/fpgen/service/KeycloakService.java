package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.context.UserContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class KeycloakService {
    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak-admin-username}")
    private String adminUsername;

    @Value("${keycloak-admin-password}")
    private String adminPassword;

    @Value("${default-password}")
    private String defaultPassword;

    @Transactional
    public String getAdminAccessToken(){
        RestTemplate restTemplate = new RestTemplate();
        String keycloakFinalUrl = keycloakUrl + "/realms/master/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(UserContextHolder.getContext().getToken());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", "admin-cli");
        body.add("username", adminUsername);
        body.add("password", adminPassword);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(keycloakFinalUrl, request, Map.class);
        return  response.getBody().get("access_token").toString();
    }

    @Transactional
    public void createUser(Author author) {
        // 0. get admin access token
        String accessToken = getAdminAccessToken();

        // 1. prepare request
        String url = keycloakUrl + "/admin/realms/" + realm + "/users";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        // 2. create user with default password
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", author.getTrigram());
        userMap.put("email", author.getEmail());
        userMap.put("enabled", true);
        userMap.put("firstName", author.getFirstName());
        userMap.put("lastName", author.getLastName());

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value", defaultPassword);
        credentials.put("temporary", true);

        userMap.put("credentials", Collections.singletonList(credentials));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userMap, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("User created successfully");
        } else {
            System.out.println("Failed to create user: " + response.getBody());
        }
    }

}
