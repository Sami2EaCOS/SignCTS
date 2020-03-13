package net.smourad.signcts;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;

public class HttpRequest {

    private final String url;
    private final String token;

    public HttpRequest(final String url, final String token) {
        this.url = url;
        this.token = token;
    }

    public String readUrl(String IDSAE) throws Exception {
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(token, "".toCharArray());
            }
        });

        StringBuilder out = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url + IDSAE).openStream(), "UTF8"));
        String str;

        while ((str = in.readLine()) != null) {
            out.append(str);
        }

        in.close();
        return out.toString();
    }
}

