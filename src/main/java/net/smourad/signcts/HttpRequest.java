package net.smourad.signcts;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;

public class HttpRequest {

    private final String url;
    private final String token;
    private final String password;

    public HttpRequest(final String url, final String token, final String password) {
        this.url = url;
        this.token = token;
        this.password = password;
    }

    public String readUrl(String IDSAE) throws Exception {
        authentication();

        StringBuilder out = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url + IDSAE).openStream(), "UTF8"));
        String str;

        while ((str = in.readLine()) != null) {
            out.append(str);
        }

        in.close();
        return out.toString();
    }

    public JsonObject readJsonFromUrl(String IDSAE) throws Exception {
        return new JsonParser().parse(readUrl(IDSAE)).getAsJsonObject();
    }

    private void authentication() {
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(token, password.toCharArray());
            }
        });
    }

}

