package com.schedule.util;

import io.jsonwebtoken.*;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    private static long time = 1000*60*60*2*99;//单位：毫秒
    private static String signature = "admin";

    public static String createToken(String username,Long id){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //header
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //payload
                .claim("username",username)
                .claim("id",id.toString())
                .setSubject("username")//可以自定义
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString())
                //signature
                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();
        return jwtToken;
    }

    public static boolean checkToken(String token){
        if(token == null)return false;
        try{
            Jws<Claims> claimsJws =Jwts.parser().setSigningKey(signature)//通过signature签名进行解密
                    .parseClaimsJws(token);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public static void parse(String token){
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature)//通过signature签名进行解密
                .parseClaimsJws(token);//解析token
        Claims claims = claimsJws.getBody();
        System.out.println("username:" + claims.get("username"));

    }

    public static String getUsername(String token){
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature)//通过signature签名进行解密
                .parseClaimsJws(token);//解析token
        Claims claims = claimsJws.getBody();
        return claims.get("username").toString();
    }

    public static Long getUserId(String token){
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature)//通过signature签名进行解密
                .parseClaimsJws(token);//解析token
        Claims claims = claimsJws.getBody();
        return Long.valueOf(claims.get("id").toString());
    }

}
