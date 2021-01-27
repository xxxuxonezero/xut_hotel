package com.xut.util;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {
    private static final String SIGN_KEY = "xutianIdentidyJWT";

    public static String generateJwt(Object o)  {
        try {
            ObjectMapper om = new ObjectMapper();
            String objStr = om.writeValueAsString(o);
            JwtBuilder builder = Jwts.builder()
                    .setPayload(objStr)
                    .signWith(SignatureAlgorithm.HS256, SIGN_KEY);
            return builder.compact();
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }

    public static Object getObject(String jwt, Class c) {
        boolean flag = isTrueJwt(jwt);
        if (!flag) {
            return null;
        }
        String content = getContent(jwt);
        if (!StringUtils.isEmpty(content)) {
            ObjectMapper om = new ObjectMapper();
            try {
                Object o = om.readValue(content, c);
                return o;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String getContent(String jwt) {
        Map<String, String> map = getJwtMap(jwt);
        String content = null;
        String payload = map.get("payload");
        if (!StringUtils.isEmpty(payload)) {
            content = new String(Base64.getDecoder().decode(payload.getBytes()));
        }
        return content;
    }

    private static Map<String, String> getJwtMap(String jwt) {
        if (StringUtils.isEmpty(jwt)) {
            return null;
        }
        String[] str = jwt.split("\\.");
        if (str.length < 3) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        map.put("header", str[0]);
        map.put("payload", str[1]);
        map.put("signature", str[2]);
        return map;
    }

    private static boolean isTrueJwt(String jwt) {
        Jws<Claims> claimsJws = null;
        try {
            claimsJws = Jwts.parser().setSigningKey(SIGN_KEY).parseClaimsJws(jwt);
        } catch (Exception e) {
            claimsJws = null;
        }
        if (claimsJws == null) {
            return false;
        }
        return true;
    }
}
