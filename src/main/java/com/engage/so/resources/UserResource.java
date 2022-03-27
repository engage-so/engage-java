package com.engage.so.resources;

import com.engage.so.EngageClient;
import com.engage.so.api.ApiConnection;
import kong.unirest.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

public class UserResource extends ApiConnection implements Resource {

    public UserResource(EngageClient client) {
        super(client);
    }

    /**
     * Identify a user - creates a new user if the user does not exist
     * @param data
     * @return
     */
    public JSONObject identify(HashMap<String, Object> data){
        if(data.isEmpty()){
            throw new IllegalArgumentException("Pass the user data");
        }
        if(!data.containsValue("uid")){
            throw new IllegalArgumentException("The user data must contain uid");
        }
        if(!data.containsValue("email") || !checkValidMail((String) data.get("email"))){
            throw new IllegalArgumentException("The user data must contain a valid email");
        }
        data = formatData(data);
        return this.makeRequest("put","/users/"+data.get("uid"), data);
    }

    /**
     * Change the data attributes of a particular user
     * @param uid
     * @param data
     * @return
     */
    public JSONObject addAttribute(String uid, HashMap<String, Object> data){
        if(uid == null || uid.trim().isEmpty()){
            throw new IllegalArgumentException("The user data must contain uid");
        }
        if(data.isEmpty()){
            throw new IllegalArgumentException("Pass the user data");
        }
        data = formatData(data);
        return this.makeRequest("put","/users/"+uid, data);
    }

    /**
     * post a new event for a user
     * @param uid
     * @param data
     * @return
     */
    public JSONObject track(String uid, HashMap<String, Object> data){
        if(uid == null || uid.trim().isEmpty()){
            throw new IllegalArgumentException("The user data must contain uid");
        }
        if(data.isEmpty()){
            throw new IllegalArgumentException("Pass the user data");
        }
        if(!data.containsValue("event")){
            throw new IllegalArgumentException("Event key is not defined");
        }
        return this.makeRequest("post","/users/"+uid+"/event", data);
    }

    /**
     * post a new event string for a user
     * @param uid
     * @param event
     * @return
     */
    public JSONObject track(String uid, String event){
        if(uid == null || uid.trim().isEmpty()){
            throw new IllegalArgumentException("The user data must contain uid");
        }
        if(event == null || event.trim().isEmpty()){
            throw new IllegalArgumentException("Event type is empty");
        }
        HashMap<String, Object> eventObj = new HashMap<String, Object>(){{
            put("event", event);
            put("value", true);
        }};
        return this.makeRequest("post","/users/"+uid+"/event", eventObj);
    }

    private boolean checkValidMail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    private HashMap<String, Object> formatData(HashMap<String, Object> data){
        final String[] allowed = {"id", "email", "device_token", "device_platform", "number", "created_at", "first_name", "last_name"};
        HashMap<String, Object> params = new HashMap<>();
        data.forEach((key, value)->{
            if(!Arrays.asList(allowed).contains(value)){
                params.put(key, value);
                data.remove(key);
            }
        });
        if(params.isEmpty()) return data;
        data.put("meta",params);
        return data;
    }
}
