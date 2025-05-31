package paka.tinder.tinderclient.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import paka.tinder.tinderclient.User;

public class AuthenticationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL = "http://localhost:8080/";

    public void registerUser(User user) {
        ResponseEntity<User> entity = restTemplate.postForEntity(URL + "register", user, User.class);
        if (entity.getStatusCode().equals(HttpStatus.CONFLICT)) throw new RuntimeException("User with such email already exists");
    }

    public void authorizeUser(User user) {
        ResponseEntity<User> entity = restTemplate.postForEntity(URL + "authorize", user, User.class);
        if (entity.getStatusCode().equals(HttpStatus.CONFLICT)) throw new RuntimeException("Invalid email or password");
    }
}
