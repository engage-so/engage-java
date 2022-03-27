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
    public String key;
    public String secret;

    public enum ResourceType {
        USER,
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    public EngageClient(String key, String secret){
        this.key = key;
        this.secret = secret;
    }

    public Resource getResource(ResourceType resource){
        // change to switch statement when conditions/resources increase
        if (resource == ResourceType.USER) {
            return new UserResource(this);
        }
        throw new InvalidParameterException("The resource does not exist");
    }
}
