package com.netflix.security;

import com.netflix.dao.LoginDao;
import com.netflix.dto.LoginDto;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class JWTUtils {
    private static final String JWT_HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";

    private static String encode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String calculateHmac(String encodedHeaderAndPayload, String secretKey, String algorithm) {
        try {
            byte[] hash = secretKey.getBytes(StandardCharsets.UTF_8);
            Mac mac = Mac.getInstance(algorithm);
            SecretKeySpec keySpec = new SecretKeySpec(hash, algorithm);
            mac.init(keySpec);
            byte[] signedBytes = mac.doFinal(encodedHeaderAndPayload.getBytes(StandardCharsets.UTF_8));
            return encode(signedBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            return null;
        }
    }

    public String createJWT(LoginDto loginDto) {
        System.out.println("Creating token..");
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + TimeUnit.MINUTES.toMillis(30);
        JSONObject payload = new JSONObject();
        payload.put("iat", nowMillis);
        payload.put("exp", expMillis);
        payload.put("sub", loginDto.getUsername());

        LoginDao loginDao = new LoginDao();
        ApplicationUserRole userRole = loginDao.getUserRole(loginDto);
        payload.put("aud", userRole);

        System.out.println(JWT_HEADER);
        System.out.println(payload);
        // Crate JWT payload
        String secretKey = "securesecuresecuresecuresecure";
        String encodedHeaderAndPayload = encode(JWT_HEADER.getBytes()) + "." + encode(payload.toString().getBytes());
        String algorithm = "HmacSHA256";
        String signature = calculateHmac(encodedHeaderAndPayload, secretKey, algorithm);
        return encode(JWT_HEADER.getBytes()) + "." + encode(payload.toString().getBytes()) + "." + signature;
    }

    public boolean isValidToken(String token) {
        boolean isValidTok = false;
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return isValidTok;
            }
            String encodedHeader = parts[0];
            String encodedPayload = parts[1];
            String signature = parts[2];

            // decode the header and payload
            String header = new String(Base64.getUrlDecoder().decode(encodedHeader));
            String payload = new String(Base64.getUrlDecoder().decode(encodedPayload));

            JSONObject headerJson = new JSONObject(header);
            JSONObject payloadJson = new JSONObject(payload);

            // check the algorithm in the header
            if (!headerJson.getString("alg").equals("HS256")) {
                System.out.println("Invalid algorithm..");
                return isValidTok;
            }

            // check the expiration time in the payload
            long expMillis = payloadJson.getLong("exp");
            long nowMillis = System.currentTimeMillis();
            if (expMillis < nowMillis) {
                System.out.println("token expired..");
                return isValidTok;
            }

            // calculate the HMAC of the encoded header and payload
            String secretKey = "securesecuresecuresecuresecure";
            String encodedHeaderAndPayload = encodedHeader + "." + encodedPayload;
            String algorithm = "HmacSHA256";
            String calculatedSignature = calculateHmac(encodedHeaderAndPayload, secretKey, algorithm);
            // calculatedSignature = null;
            // compare the calculated signature with the one in the JWT
            if (calculatedSignature != null && calculatedSignature.equals(signature)) {
                System.out.println("Valid signature..");
                isValidTok = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValidTok;
    }


    public JSONObject parsClaims(String token) {
        try {
            String[] parts = token.split("\\.");
            String decodedPayload = new String(Base64.getUrlDecoder().decode(parts[1]));
            return new JSONObject(decodedPayload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
