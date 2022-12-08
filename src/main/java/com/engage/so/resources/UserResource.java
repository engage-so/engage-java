package com.engage.so.resources;

import com.engage.so.EngageClient;
import com.engage.so.api.ApiConnection;
import kong.unirest.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

public class UserResource extends ApiConnection implements Resource {

    /**
     * Resources to access Users.
     * @param client EngageClient instance.
     */
    public UserResource(final EngageClient client) {
        super(client);
    }

    /**
     * Identify a user - creates a new user if the user does not exist.
     * @param data params for identification.
     * @return JSONObject
     */
    public JSONObject identify(HashMap<String, Object> data) {
        if (data.isEmpty()) {
            throw new IllegalArgumentException("Pass the user data");
        }
        if (!data.containsKey("id")) {
            throw new IllegalArgumentException("The user data must contain id");
        }
        if (data.containsKey("email") && !checkValidMail((String) data.get("email"))) {
            throw new IllegalArgumentException("The user data must contain a valid email");
        }
        data = formatData(data);
        return this.makeRequest("put","/users/" + data.get("id"), data);
    }

    /**
     * Change the data attributes of a particular user.
     * @param uid unique ID of user
     * @param data params to add for attributes
     * @return JSONObject
     */
    public JSONObject addAttribute(String uid, HashMap<String, Object> data) {
        if (uid == null || uid.trim().isEmpty()) {
            throw new IllegalArgumentException("The user data must contain uid");
        }
        if (data.isEmpty()) {
            throw new IllegalArgumentException("Pass the user data");
        }
        data = formatData(data);
        return this.makeRequest("put","/users/" + uid, data);
    }

    /**
     * post a new event for a user.
     * @param uid unique ID of user
     * @param data event data to be added
     * @return JSONObject
     */
    public JSONObject track(String uid, HashMap<String, Object> data) {
        if (uid == null || uid.trim().isEmpty()) {
            throw new IllegalArgumentException("The user data must contain uid");
        }
        if (data.isEmpty()) {
            throw new IllegalArgumentException("Pass the user data");
        }
        if (!data.containsKey("event")) {
            throw new IllegalArgumentException("Event key is not defined");
        }
        return this.makeRequest("post","/users/" + uid + "/events", data);
    }

    /**
     * post a new event string for a user
     * @param uid unique ID of user
     * @param event String for event
     * @return JSONObject
     */
    public JSONObject track(String uid, String event) {
        if (uid == null || uid.trim().isEmpty()) {
            throw new IllegalArgumentException("The user data must contain uid");
        }
        if (event == null || event.trim().isEmpty()) {
            throw new IllegalArgumentException("Event type is empty");
        }
        HashMap<String, Object> eventObj = new HashMap<String, Object>() {{
            put("event", event);
            put("value", true);
        }};
        return this.makeRequest("post","/users/" + uid + "/events", eventObj);
    }

    /**
     * Add a user to an account
     * @param userId unique id of user
     * @param accountId unique id of account
     * @param role role to add the user as
     * @return JSONObject
     */
    public JSONObject addToAccount(String userId, String accountId, String role) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User id is missing");
        }
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new IllegalArgumentException("Account id is missing");
        }
        HashMap<String, Object> obj = new HashMap<String, Object>() {{
            put("accounts", new HashMap[] {
                    new HashMap<String, Object>() {{
                        put("id", accountId);
                        put("role", role);
                    }}
            });
        }};
        return this.makeRequest("post", "/users/" + userId + "/accounts", obj);
    }

    /**
     * Add a user to an account
     * @param userId unique id of user
     * @param accountId unique id of account
     * @return JSONObject
     */
    public JSONObject addToAccount(String userId, String accountId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User id is missing");
        }
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new IllegalArgumentException("Account id is missing");
        }
        HashMap<String, Object> obj = new HashMap<String, Object>() {{
            put("accounts", new HashMap[] {
                    new HashMap<String, Object>() {{
                        put("id", accountId);
                    }}
            });
        }};
        return this.makeRequest("post", "/users/" + userId + "/accounts", obj);
    }

    /**
     * Remove a user from an account
     * @param userId unique user id
     * @param accountId unique account id
     * @return JSONObject
     */
    public JSONObject removeFromAccount(String userId, String accountId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User id is missing");
        }
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new IllegalArgumentException("Account id is missing");
        }
        return this.makeRequest("delete", "/users/" + userId + "/accounts", null);
    }

    /**
     * This changes the user's role
     * @param userId unique user id
     * @param accountId account id
     * @param role user role
     * @return JSONObject
     */
    public JSONObject changeUserRole(String userId, String accountId, String role) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User id is missing");
        }
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new IllegalArgumentException("Account id is missing");
        }
        return this.makeRequest("put", "/users/" + userId + "/accounts/"  + accountId, new HashMap<>() {{
            put("role", role);
        }});
    }

    /**
     * Convert users from customer to account
     * @param userId unique user id
     * @return JSONObject
     */
    public JSONObject convertToAccount(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User id is missing");
        }
        return this.makeRequest("POST", "/users/" + userId + "/convert", new HashMap<>() {{
            put("type", "account");
        }});
    }

    /**
     * Convert users from account to customer
     * @param userId unique user ID
     * @return JSONObject
     */
    public JSONObject convertToCustomer(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User id is missing");
        }
        return this.makeRequest("POST", "/users/" + userId + "/convert", new HashMap<>() {{
            put("type", "customer");
        }});
    }

    private boolean checkValidMail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    private HashMap<String, Object> formatData(HashMap<String, Object> data) {
        final String[] allowed = {"id", "email", "device_token", "device_platform", "number", "created_at", "first_name", "is_account", "last_name"};
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> newData = new HashMap<>();
        data.forEach((key, value) -> {
            if (!Arrays.asList(allowed).contains(key)) {
                params.put(key, value);
                return;
            }
            newData.put(key, value);
        });
        if (params.isEmpty()) return newData;
        newData.put("meta", params);
        return newData;
    }
}
