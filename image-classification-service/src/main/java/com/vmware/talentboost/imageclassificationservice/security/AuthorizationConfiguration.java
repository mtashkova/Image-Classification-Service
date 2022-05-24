package com.vmware.talentboost.imageclassificationservice.security;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Configuration
public class AuthorizationConfiguration {
    private final  String auth = "acc_80525c69f34204d:8efdf7a0073fc081569f56221e826a66";

    public AuthorizationConfiguration() {
    }

    public HttpURLConnection isAuthorized(URL url) throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
            String authHeaderValue = "Basic " + new String(encodedAuth);
            connection.setRequestProperty("Authorization", authHeaderValue);
        } catch(IOException io) {
            throw new IOException("Connection with url " + url + " was not successful!");
        }
        return connection;
    }
}
