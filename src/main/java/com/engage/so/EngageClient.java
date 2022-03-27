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

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    public EngageClient(String key, String secret){
        this.key = key;
        this.secret = secret;
    }

    protected void setCredentials(String key, String secret){
        this.key = key;
        this.secret = secret;
    }

    public String getKey() {
        return key;
    }

    public String getSecret() {
        return secret;
    }
}
