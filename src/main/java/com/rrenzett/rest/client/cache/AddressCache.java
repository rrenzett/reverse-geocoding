package com.rrenzett.rest.client.cache;

import java.util.Iterator;
import java.util.LinkedList;

public class AddressCache {
    private int cacheSize = 10;
    private LinkedList<CacheItem> cache = new LinkedList<CacheItem>();

    public synchronized void add(String latitude, String longitude, String address, String time) {
        CacheItem item = new CacheItem(latitude, longitude, address, time);

        if (cache.contains(item)) {
            // If it is there then move to the first item in the list
            cache.remove(item);
            cache.addFirst(item);
        } else {

            // Check size before we add
            if (cache.size() == cacheSize) {
                // If adding would go over max size then remove last
                cache.removeLast();
            }

            cache.addFirst(item);
        }
    }

    public synchronized String getCacheAsJson() {
        String ret = "{}";

        if (!cache.isEmpty()) {

            ret = "{\n  [\n";

            Iterator<CacheItem> items = cache.iterator();

            while (items.hasNext()) {
                CacheItem item = items.next();
                ret += "    { \"latitude\": \"" + item.getLatitude() + "\", \"longitude\": \"" + item.getLongitude()
                        + "\", \"address\": \"" + item.getAddress() + "\", \"time\": \"" + item.getTime() + "\" }";

                if (items.hasNext()) {
                    ret += ",\n";
                } else {
                    ret += "\n";
                }

            }

            ret += "  ]\n}";
        }

        return ret;
    }

    private class CacheItem {

        private String latitude;
        private String longitude;
        private String address;
        private String time;

        public CacheItem(String latitude, String longitude, String address, String time) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.address = address;
            this.time = time;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getAddress() {
            return address;
        }

        public String getTime() {
            return time;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
            result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null) {
                return false;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            CacheItem other = (CacheItem) obj;

            if (latitude == null) {

                if (other.latitude != null) {
                    return false;
                }
            } else if (!latitude.equals(other.latitude)) {
                return false;
            }

            if (longitude == null) {

                if (other.longitude != null) {
                    return false;
                }
            } else if (!longitude.equals(other.longitude)) {
                return false;
            }

            return true;
        }
    }
}
