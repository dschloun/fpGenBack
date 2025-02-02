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
import java.util.UUID;

/**
 * Service class to interact with Keycloak API
 */
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

    /**
     * Get access token for admin user
     * @return access token
     */
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

    /**
     * Create a new user in Keycloak
     * @param author Author object
     */
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
        userMap.put("emailVerified", true);
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

    /**
     * Update user status
     * @param enabled true if user is enabled, false otherwise
     * @param userName username of the user
     */
    @Transactional
    public void updateUserStatus(final boolean enabled, final String userName){
        // 0. get admin access token
        String accessToken = getAdminAccessToken();

        // 2. get userId
        String userId = searchUserByUsername(userName).toString();

        // 1. prepare request
        String url = keycloakUrl + "/admin/realms/" + realm + "/users/" + userId;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        // 2. update user status
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("enabled", enabled);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userMap, headers);
        try{
        restTemplate.put(url, request, String.class);
        } catch (Exception e){
            System.out.println("Failed to update user status: " + e.getMessage());

        }
        System.out.println("User status updated successfully");
    }

    /**
     * Search user by username
     * @param username username of the user
     * @return userId
     */
    @Transactional
    public UUID searchUserByUsername(String username) {
        String accessToken = getAdminAccessToken();
        String url = keycloakUrl + "/admin/realms/" + realm + "/users?username=" + username;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Map[]> response = restTemplate.exchange(url, HttpMethod.GET, request, Map[].class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody().length > 0) {
            Map<String, Object> user = response.getBody()[0];
            String userId = (String) user.get("id");
            return UUID.fromString(userId);
        } else {
            System.out.println("User not found: " + response.getStatusCode() + " - " + response.getBody());
            throw new RuntimeException("User not found");
        }
    }

}
