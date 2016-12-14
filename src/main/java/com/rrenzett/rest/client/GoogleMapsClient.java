package com.rrenzett.rest.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GoogleMapsClient extends Client {

    private static final String key = "AIzaSyClbxC6DIC4oyxp8TwkNd1R86jEa9WAuBU";
    private static final String baseURL = "https://maps.googleapis.com/maps/api/geocode/json";

    public static String httpsGet(final String latitude, final String longitude)
            throws IOException, URISyntaxException, ParseException {
        String response = httpsGet(baseURL, buildParamterList(latitude, longitude));
        return getAddressFromResponse(response);
    }

    private static List<NameValuePair> buildParamterList(String latitude, String longitude) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("key", key));
        pairs.add(new BasicNameValuePair("latlng", latitude + "," + longitude));
        return pairs;
    }

    private static String getAddressFromResponse(String response) throws ParseException {
        String ret = null;

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
        JSONArray resultsArray = (JSONArray) jsonObject.get("results");

        if (resultsArray != null) {
            Iterator<?> arrayItems = resultsArray.iterator();

            while (arrayItems.hasNext()) {
                JSONObject arrayItem = (JSONObject) arrayItems.next();

                if (arrayItem.containsKey("formatted_address")) {
                    ret = (String) arrayItem.get("formatted_address");
                    break;
                }
            }
        }

        return ret;
    }
}
