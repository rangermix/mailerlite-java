package net.digibi.mailerlite;

import org.json.JSONObject;

import kong.unirest.*;

/**
 * MailerLiteClient
 */
public class MailerLiteClient {

    private static final String API_END_POINT = "https://api.mailerlite.com/api/v2/";
    private final String API_KEY;

    public MailerLiteClient(String apiKey) {
        API_KEY = apiKey;
    }

    public boolean subscribeUser(long groupId, String userBody) {
        HttpResponse<String> response = Unirest
            .post(API_END_POINT + "groups/" + groupId + "/subscribers")
            .header("content-type", "application/json")
            .header("x-mailerlite-apikey", API_KEY)
            .body(userBody)
            .asString();
        return response.isSuccess();
    }

    public boolean subscribeUser(long groupId, String email, String name) {
        return subscribeUser(groupId, createUserBodyToSend(email, name, null));
    }

    public boolean subscribeUser(long groupId, String email, String name, String fields) {
        return subscribeUser(groupId, createUserBodyToSend(email, name, fields));
    }

    public static String createUserBodyToSend(String email, String name, String fields) {
        JSONObject jsonObject = new JSONObject().put("email", email).put("name", name);
        if (fields!=null) {
            jsonObject.put("fields", fields);
        }
        return jsonObject.toString();
    }

}