package com.rrenzett.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

public class Client {

    protected static String httpsGet(String baseUrl, List<NameValuePair> pairs) throws IOException, URISyntaxException {
        URL url = new URL(buildUrl(baseUrl, pairs));
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
            throw new IOException("Failed : HTTP error code : " + conn.getResponseMessage());
        }

        String result = readResponse(conn);

        conn.disconnect();

        return result;
    }

    private static String buildUrl(String baseUrl, List<NameValuePair> pairs) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(baseUrl);

        for (NameValuePair pair : pairs) {
            builder.addParameter(pair.getName(), pair.getValue());
        }

        return builder.build().toString();
    }

    protected static String readResponse(HttpsURLConnection conn) throws IOException {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }

        return sb.toString();
    }
}
