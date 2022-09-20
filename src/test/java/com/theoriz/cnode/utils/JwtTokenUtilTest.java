package com.theoriz.cnode.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class JwtTokenUtilTest {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static String validToken;
    private static String inValidToken;
    private static String secret = "mySecret";
    private static int expiration = 60;

    @BeforeAll
    public static void generateToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "TestUsername");
        claims.put("created", new Date());
        validToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        inValidToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, "wrongSecret")
                .compact();
    }

    @Test
    public void TestValidateToken(){
        Assertions.assertTrue(jwtTokenUtil.validateToken(validToken));
        Assertions.assertFalse(jwtTokenUtil.validateToken(inValidToken));
    }

    @Test
    public void TestGetUsernameFromToken(){
        Assertions.assertEquals("TestUsername", jwtTokenUtil.getUserNameFromToken(validToken));
        Assertions.assertThrows(SignatureException.class, () -> jwtTokenUtil.getUserNameFromToken(inValidToken));
    }
}
