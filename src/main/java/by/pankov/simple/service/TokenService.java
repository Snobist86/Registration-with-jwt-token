package by.pankov.simple.service;

public interface TokenService {

    String generateToken(String email);

    boolean isTokenValid(String token);

    String getEmail(String token);
}
