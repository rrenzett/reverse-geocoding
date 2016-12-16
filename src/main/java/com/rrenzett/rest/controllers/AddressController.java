package com.rrenzett.rest.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rrenzett.rest.client.GoogleMapsClient;
import com.rrenzett.rest.client.cache.AddressCache;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = "API to lookup addresses")
@RequestMapping("/address")
public class AddressController {

    private AddressCache addressCache = new AddressCache();

    @ApiOperation(value = "Lookup an address", notes = "Use this operation to lookup an address.")
    @ApiResponses({ @ApiResponse(code = 200, message = "The address is included in the response."),
            @ApiResponse(code = 400, message = "The request validation failed.", response = Exception.class) })
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = { "application/json" })
    @ResponseBody
    public ResponseEntity<String> getAddress(
            @ApiParam("The latitude coodinate") @RequestParam(value = "latitude", required = true) String latitude,
            @ApiParam("The longitude coodinate") @RequestParam(value = "longitude", required = true) String longitude)
            throws IOException, URISyntaxException, ParseException {

        if (latitude == null || latitude.trim().isEmpty() || longitude == null || longitude.trim().isEmpty()) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        String address = GoogleMapsClient.httpsGet(latitude, longitude);
        addressCache.add(latitude, longitude, address, new Date().toString());
        return new ResponseEntity<String>(buildJsonMsg(address), HttpStatus.OK);
    }

    @ApiOperation(value = "Get the list of addresses", notes = "Use this to look at the list of recently looked up addresses.")
    @ApiResponses({ @ApiResponse(code = 200, message = "The list of addresses is included in the response.") })
    @RequestMapping(value = "/cache/", method = RequestMethod.GET, produces = { "application/json" })
    @ResponseBody
    public ResponseEntity<String> getAddressCache() {

        return new ResponseEntity<String>(addressCache.getCacheAsJson(), HttpStatus.OK);
    }

    private static String buildJsonMsg(String address) {
        String json = "{}";

        if (address != null && !address.isEmpty()) {
            json = "{\n\"address\": \"" + address + "\"\n}";
        }

        return json;
    }
}
