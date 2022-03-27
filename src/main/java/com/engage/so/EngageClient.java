package com.engage.so;

import com.engage.so.resources.Resource;
import com.engage.so.resources.UserResource;

import java.security.InvalidParameterException;

/**
 * Hello world!
 *
 */
public class EngageClient
{
    private String key;
    private String secret;

    public EngageClient(String key, String secret){
        verifyClientParams(key,secret);
        this.key = key;
        this.secret = secret;
    }

    protected void setCredentials(String key, String secret){
        verifyClientParams(key,secret);
        this.key = key;
        this.secret = secret;
    }

    private void verifyClientParams(String key, String secret){
        if(key == null || key.trim().isEmpty()){
            throw new IllegalArgumentException("Pass a valid key");
        }
        if(secret == null || secret.trim().isEmpty()){
            throw new IllegalArgumentException("Pass a valid secret");
        }
    }

    public String getKey() {
        return key;
    }

    public String getSecret() {
        return secret;
    }
}
