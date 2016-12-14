package com.rrenzett.rest.client.cache;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AddressCacheTest {

    private String testLat1 = "lat1";
    private String testLat2 = "lat2";
    private String testLat3 = "lat3";
    private String testLat4 = "lat4";
    private String testLat5 = "lat5";
    private String testLat6 = "lat6";
    private String testLat7 = "lat7";

    private String testLon1 = "lon1";
    private String testLon2 = "lon2";
    private String testLon3 = "lon3";
    private String testLon4 = "lon4";
    private String testLon5 = "lon5";
    private String testLon6 = "lon6";
    private String testLon7 = "lon7";

    private String testAddr1 = "addr1";
    private String testAddr2 = "addr2";
    private String testAddr3 = "addr3";
    private String testAddr4 = "addr4";
    private String testAddr5 = "addr5";
    private String testAddr6 = "addr6";
    private String testAddr7 = "addr7";

    private String testTime1 = "time1";
    private String testTime2 = "time2";
    private String testTime3 = "time3";
    private String testTime4 = "time4";
    private String testTime5 = "time5";
    private String testTime6 = "time6";
    private String testTime7 = "time7";

    @Test
    public void testAdd() {
        // create cache of size 5
        AddressCache cache = new AddressCache(5);

        // Cache is empty
        assertEquals(cache.cache.size(), 0);

        // Add some and test size
        cache.add(testLat1, testLon1, testAddr1, testTime1);
        cache.add(testLat2, testLon2, testAddr2, testTime2);
        cache.add(testLat3, testLon3, testAddr3, testTime3);
        assertEquals(cache.cache.size(), 3);

        // Add same object in again and show size doesn't increase
        cache.add(testLat3, testLon3, testAddr3, testTime3);
        assertEquals(cache.cache.size(), 3);

        // Add same lat and lon but different addr and time and show size doesn't increase
        cache.add(testLat3, testLon3, testAddr7, testTime7);
        assertEquals(cache.cache.size(), 3);

        // Fill cache
        cache.add(testLat4, testLon4, testAddr4, testTime4);
        cache.add(testLat5, testLon5, testAddr5, testTime5);
        assertEquals(cache.cache.size(), 5);

        // Add 1 item to full cache
        cache.add(testLat6, testLon6, testAddr6, testTime6);
        assertEquals(cache.cache.size(), 5);
    }

    @Test
    public void testgetCacheAsJson() {
        AddressCache cache = new AddressCache(5);
        cache.add(testLat1, testLon1, testAddr1, testTime1);
        cache.add(testLat2, testLon2, testAddr2, testTime2);
        cache.add(testLat3, testLon3, testAddr3, testTime3);
        cache.add(testLat4, testLon4, testAddr4, testTime4);
        cache.add(testLat5, testLon5, testAddr5, testTime5);
        assertEquals(cache.getCacheAsJson(),
                "{\n  [\n    { \"latitude\": \"lat5\", \"longitude\": \"lon5\", \"address\": \"addr5\", \"time\": \"time5\" },\n    { \"latitude\": \"lat4\", \"longitude\": \"lon4\", \"address\": \"addr4\", \"time\": \"time4\" },\n    { \"latitude\": \"lat3\", \"longitude\": \"lon3\", \"address\": \"addr3\", \"time\": \"time3\" },\n    { \"latitude\": \"lat2\", \"longitude\": \"lon2\", \"address\": \"addr2\", \"time\": \"time2\" },\n    { \"latitude\": \"lat1\", \"longitude\": \"lon1\", \"address\": \"addr1\", \"time\": \"time1\" }\n  ]\n}");
    }

}
