package api;

import entity.User;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import java.io.IOException;
import java.util.*;


public class MongoChatDB implements ChatDB{
    private static final String API_URL = "https://api-D851D713-E982-45AD-B2C6-DCC681162BE3.sendbird.com";
    private static final String API_TOKEN = "091c2733716fa125980c894cc067b64e1d50fd7e";
    public static String getApiToken() { return API_TOKEN; }


    public User createUser(String user_id, String nickname, String profileUrl, String profileFile,
                           String issueAccessToken, List<String> discoveryKeys, JSONObject metadata) throws JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("user_id", user_id);
        requestBody.put("nickname", nickname);
        requestBody.put("profile_url", profileUrl);
        requestBody.put("profile_file", profileFile);
        requestBody.put("issue_access_token", issueAccessToken);
        requestBody.put("discovery_keys", new JSONArray(discoveryKeys));
        requestBody.put("metadata", metadata);

        RequestBody body = RequestBody.create(mediaType, requestBody.toString());

        Request request = new Request.Builder()
                .url(API_URL + "/v3/users")
                .method("POST", body)
                .addHeader("Api-Token", API_TOKEN)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBodyString = response.body().string();
            System.out.println("Response Body: " + responseBodyString);
            JSONObject responseBody = new JSONObject(responseBodyString);

            if (response.isSuccessful()) {
                JSONObject user = responseBody;

                return User.builder()
                        .user_id(user.getString("user_id"))
                        .nickname(user.getString("nickname"))
                        .profileUrl(user.getString("profile_url"))
                        .issueAccessToken(responseBody.getString("access_token"))
                        .discoveryKeys(Arrays.asList(user.getJSONArray("discovery_keys").toString()))
                        .metadata(user.getJSONObject("metadata"))
                        .build();
            } else {
                throw new RuntimeException("API Request Failed. Status Code: " + response.code() + ", Response: "
                        + responseBodyString);
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred: " + e.getMessage());
        } catch (JSONException e) {
            throw new RuntimeException("Error: JSON Exception occurred - " + e.getMessage());
            // Handle the exception appropriately (logging, error response, etc.)
        }
    }

    public static void main(String[] args) {
        // Example input parameters
        String userId = "csc207";
        String nickname = "Jun yang";
        String profileUrl = "https://example.com/profile.jpg";
        String profileFile = "profile.jpg";
        String issueAccessToken = "True";
        List<String> discoveryKeys = new ArrayList<>();
        discoveryKeys.add("key1");
        discoveryKeys.add("key2");


        // Example metadata as a JSON object
        JSONObject metadata = new JSONObject();
        metadata.put("key", "value");

        // Creating an instance of MongoChatDB
        ChatDB chatDB = new MongoChatDB();

        try {
            // Calling createUser method to test

            User user = ((MongoChatDB) chatDB).createUser(userId, nickname, profileUrl, profileFile,
                    issueAccessToken, discoveryKeys, metadata);

            // Printing the user details
            System.out.println("User created successfully:");
            System.out.println("User ID: " + user.getUser_Id());
            System.out.println("Nickname: " + user.getNickname());
            System.out.println("Profile URL: " + user.getProfileUrl());
            System.out.println("Profile File: " + user.getProfileFile());
            System.out.println("Discovery Keys: " + user.getDiscoveryKeys());
            System.out.println("Issue Access Token: " + user.isIssueAccessToken());
            System.out.println("Metadata: " + user.getMetadata());
        } catch (Exception e) {
            // Handling exceptions
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}


