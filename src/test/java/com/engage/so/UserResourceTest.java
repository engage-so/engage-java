package com.engage.so;

import com.engage.so.resources.UserResource;
import kong.unirest.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;

import static org.junit.Assert.*;

public class UserResourceTest {
    private EngageClient client;
    @Before
    public void setup(){
        client = new EngageClient("key", "secret");
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void ItShouldThrowIfNoData(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Pass the user data");
        UserResource userResource = new UserResource(client);
        userResource.identify(new HashMap<>());
    }

    @Test
    public void ItShouldThrowIfNoValidId(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The user data must contain id");
        UserResource userResource = new UserResource(client);
        userResource.identify(new HashMap<String, Object>(){{
            put("address","Mainland Lagos");
        }});
    }

    @Test
    public void ItShouldThrowIfNoValidEmail(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The user data must contain a valid email");
        UserResource userResource = new UserResource(client);
        userResource.identify(new HashMap<String, Object>(){{
            put("id","U4245");
            put("email","ghetes.c");
        }});
    }

    @Test
    public void ItShouldReturnError(){
        HashMap<String, Object> data = new HashMap<>(){{
            put("id","U424");
            put("email","newuser@gmail.com");
            put("skills","diving");
            put("hobby","skating");
        }};
        UserResource userResource = new UserResource(client);
        // Set new client
        userResource.setClient(new EngageClient("test", "test"));
        JSONObject response = userResource.identify(data);
        assertTrue(response.has("error"));
        assertEquals(response.get("error"), "Wrong authentication credentials");
    }

    //indirect test for formatData private function
    @Test
    public void ItShouldReturnFormatedUserJson(){
        HashMap<String, Object> data = new HashMap<>(){{
            put("id","U424");
            put("email","newuser@gmail.com");
            put("skill","moving");
            put("hobby","skating");
        }};
        UserResource userResource = new UserResource(client);
        // Set new client and add your key
        userResource.setClient(new EngageClient("", ""));
        JSONObject response = userResource.identify(data);
        System.out.println(response);
        assertTrue(response.has("meta"));
        assertEquals(response.get("email"), "newuser@gmail.com");
    }

    @Test
    public void ItShouldReturnAddAttr(){
        long l = Long.parseLong("6008514751443");
        HashMap<String, Object> data = new HashMap<>(){{
            put("first_name","testing");
            put("number", l);
        }};
        UserResource userResource = new UserResource(client);
        // Set new client and add your key
        userResource.setClient(new EngageClient("", ""));
        JSONObject response = userResource.addAttribute("U424", data);
        System.out.println(response);
        assertTrue(response.has("number"));
        assertEquals(response.get("number"), "6008514751443");
    }

    // Tracking the user
    @Test
    public void ItShouldReturnOk(){
        HashMap<String, Object> data = new HashMap<>(){{
            put("event","new sign");
            put("value", true);
        }};
        UserResource userResource = new UserResource(client);
        // Set new client and add your key
        userResource.setClient(new EngageClient("", ""));
        JSONObject response = userResource.track("U424", data);
        assertTrue(response.has("status"));
        assertEquals(response.get("status"), "ok");
    }
}
