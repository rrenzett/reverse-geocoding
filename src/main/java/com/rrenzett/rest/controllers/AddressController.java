package com.rrenzett.rest.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rrenzett.rest.client.GoogleMapsClient;
import com.rrenzett.rest.client.cache.AddressCache;

@RestController
@RequestMapping("/address")
public class AddressController {

    private AddressCache addressCache = new AddressCache();

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = { "application/json" })
    @ResponseBody
    public String getAddress(@RequestParam(value = "latitude") String latitude,
            @RequestParam(value = "longitude") String longitude)
            throws IOException, URISyntaxException, ParseException {

        String address = GoogleMapsClient.httpsGet(latitude, longitude);
        addressCache.add(latitude, longitude, address, new Date().toString());
        return buildJsonMsg(address);
    }

    @RequestMapping(value = "/cache/", method = RequestMethod.GET, produces = { "application/json" })
    @ResponseBody
    public String getAddressCache() {

        return addressCache.getCacheAsJson();
    }

    private static String buildJsonMsg(String address) {
        String json = "{}";

        if (address != null && !address.isEmpty()) {
            json = "{\n\"address\": \"" + address + "\"\n}";
        }

        return json;
    }
}
