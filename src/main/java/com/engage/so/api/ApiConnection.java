package com.engage.so.api;

import com.engage.so.EngageClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
                .setDefaultHeader("Content-Type", "application/json")
                .setDefaultHeader("User-Agent", "Engage.so Java Client");
    }

    /**
     * Make API request
     * @param method
     * @param url
     * @param bodyData
     * @return
     */
    protected JSONObject makeRequest(String method, String url, HashMap<String, Object> bodyData){
        String data;
        try {
            ObjectMapper mapper = new ObjectMapper();
            data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bodyData);
        } catch (JsonProcessingException e){
            throw new IllegalArgumentException("Pass a valid HashMap");
        }
        HttpResponse<JsonNode> response = Unirest.request(method, url)
                .basicAuth(client.getKey(), client.getSecret())
                .body(data)
                .asJson();

        return response.getBody().getObject();
    }

    /**
     * Make a request with query parameters
     * @param method
     * @param url
     * @param query
     * @param bodyData
     * @return
     */
    protected JSONObject makeRequestWithQuery(String method, String url, HashMap<String, Object> query, HashMap<String, Object> bodyData){
        String data;
        try {
            ObjectMapper mapper = new ObjectMapper();
            data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bodyData);
        } catch (JsonProcessingException e){
            throw new IllegalArgumentException("Pass a valid HashMap");
        }
        HttpResponse<JsonNode> response = Unirest.request(method, url)
                .basicAuth(client.getKey(), client.getSecret())
                .queryString(query)
                .body(data)
                .asJson();

        return response.getBody().getObject();
    }

    public void setClient(EngageClient client){
        this.client = client;
    }

}
