package com.ramelon.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/27 16:23
 */
public class JoseJwt {
    public static void main(String[] args) throws JOSEException, ParseException {
        // 创建JWS
        // 1. 创建Payload，存放有效信息
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("username", "zhangsan");
        // 2. 创建JWS
        JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256),
                new Payload(jsonObject));

        // 3. 创建秘钥，并签名
        byte[] sharedKey = new byte[32];
        new SecureRandom().nextBytes(sharedKey);
        jwsObject.sign(new MACSigner(sharedKey));
        // 4. 序列化为令牌
        String token = jwsObject.serialize();
        System.out.println(token);

        // 解析JWS
        JWSObject plainObject = JWSObject.parse(token);
        System.out.println(plainObject.getHeader());
        System.out.println(plainObject.getPayload());

        // 验签
        MACVerifier macVerifier = new MACVerifier(sharedKey);
        boolean verify = plainObject.verify(macVerifier);
        System.out.println("验签结果：" + verify);
    }

}