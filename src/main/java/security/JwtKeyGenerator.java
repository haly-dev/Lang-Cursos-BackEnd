package com.example.projetolangcursos.util; // Substitua pelo pacote correto

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Base64;

public class JwtKeyGenerator {

    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Chave secreta gerada: " + secretKey);
    }
}