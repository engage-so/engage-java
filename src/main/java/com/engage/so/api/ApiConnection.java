package com.engage.so.api;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.util.HashMap;

public class ApiConnection {
    public final String rootUrl = "https://api.engage.so";
    private String key;
    private String secret;

    public ApiConnection (String key, String secret){
        this.key = key;
        this.secret = secret;
        Unirest.config()
                .defaultBaseUrl(rootUrl)
                .setDefaultHeader("Accept", "application/json")
                .setDefaultHeader("User-Agent", "Engage.so Java Client");
    }

    /**
     * Make API request
     * @param method
     * @param url
     * @param body
     * @return
     */
    protected JSONObject makeRequest(String method, String url, HashMap<String, Object> body){
        HttpResponse<JsonNode> response = Unirest.request(method, url)
                .basicAuth(key,secret)
                .fields(body)
                .asJson();

        return response.getBody().getObject();
    }

    /**
     * Make a request with query parameters
     * @param method
     * @param url
     * @param query
     * @param body
     * @return
     */
    protected JSONObject makeRequestWithQuery(String method, String url, HashMap<String, Object> query, HashMap<String, Object> body){
        HttpResponse<JsonNode> response = Unirest.request(method, url)
                .basicAuth(key,secret)
                .queryString(query)
                .fields(body)
                .asJson();

        return response.getBody().getObject();
    }

    protected void setCredentials(String key, String secret){
        this.key = key;
        this.secret = secret;
    }

}
