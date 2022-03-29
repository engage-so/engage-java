package com.engage.so;

public class EngageClient {
    /**
     * Key from Engage Dashboard.
     */
    private String key;
    /**
     * Secret from Engage Dashboard.
     */
    private String secret;

    /**
     * @param key String
     * @param secret String
     */
    public EngageClient(String key, String secret) {
        verifyClientParams(key, secret);
        this.key = key;
        this.secret = secret;
    }

    /**
     * @param key String
     * @param secret String
     */
    protected void setCredentials(String key, String secret) {
        verifyClientParams(key, secret);
        this.key = key;
        this.secret = secret;
    }

    private void verifyClientParams(String key, String secret) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Pass a valid key");
        }
        if (secret == null || secret.trim().isEmpty()) {
            throw new IllegalArgumentException("Pass a valid secret");
        }
    }

    /**
     * @return String Key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return String secret
     */
    public String getSecret() {
        return secret;
    }
}
