package com.votaciones.back.service.users.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Map;

public interface JwtService {

    String getToken(UserDetails userDetails);

    String getToken(Map<String,Object> extraClaim, UserDetails user);

    Key getKey();

    String getUserNameFromToken(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
