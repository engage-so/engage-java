package com.engage.so.api;

import com.engage.so.EngageClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.util.HashMap;

public class ApiConnection {
    /**
     * The engage api root url.
     */
    private final String rootUrl = "https://api.engage.so";

    /**
     * EngageClient.
     */
    private EngageClient client;

    /**
     * Communication with the API.
     * @param engageClient EngageClient
     */
    public ApiConnection(final EngageClient engageClient) {
        this.client = engageClient;
        Unirest.config()
                .defaultBaseUrl(rootUrl)
                .setDefaultHeader("Accept", "application/json")
                .setDefaultHeader("Content-Type", "application/json")
                .setDefaultHeader("User-Agent", "Engage.so Java Client");
    }

    /**
     * Make API request.
     * @param method String
     * @param url String
     * @param bodyData Params
     * @return JSONObject
     */
    protected JSONObject makeRequest(String method, String url, HashMap<String, Object> bodyData) {
        if (bodyData == null) {
            HttpResponse<JsonNode> response = Unirest.request(method, url)
                    .basicAuth(client.getKey(), client.getSecret())
                    .asJson();
            return response.getBody().getObject();
        }

        String data;
        try {
            ObjectMapper mapper = new ObjectMapper();
            data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bodyData);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Pass a valid HashMap");
        }
        HttpResponse<JsonNode> response = Unirest.request(method, url)
                .basicAuth(client.getKey(), client.getSecret())
                .body(data)
                .asJson();

        return response.getBody().getObject();
    }

    /**
     * Make a request with query parameters.
     * @param method String
     * @param url String
     * @param query String
     * @param bodyData params
     * @return JSONObject
     */
    protected JSONObject makeRequestWithQuery(String method, String url, HashMap<String, Object> query, HashMap<String, Object> bodyData) {
        String data;
        try {
            ObjectMapper mapper = new ObjectMapper();
            data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bodyData);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Pass a valid HashMap");
        }
        HttpResponse<JsonNode> response = Unirest.request(method, url)
                .basicAuth(client.getKey(), client.getSecret())
                .queryString(query)
                .body(data)
                .asJson();

        return response.getBody().getObject();
    }

    /**
     * Set new Client.
     * @param engageClient EngageClient
     */
    public void setClient(final EngageClient engageClient) {
        this.client = engageClient;
    }

}
