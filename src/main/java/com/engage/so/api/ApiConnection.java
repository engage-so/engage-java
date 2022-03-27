package com.engage.so.api;

import com.engage.so.EngageClient;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.util.HashMap;

public class ApiConnection {
    public final String rootUrl = "https://api.engage.so";
    private EngageClient client;

    public ApiConnection (EngageClient client){
        this.client = client;
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
                .basicAuth(client.getKey(), client.getSecret())
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
                .basicAuth(client.getKey(), client.getSecret())
                .queryString(query)
                .fields(body)
                .asJson();

        return response.getBody().getObject();
    }

    protected void setClient(EngageClient client){
        this.client = client;
    }

}
