package com.fenix.temperatura;

import com.google.gson.annotations.Expose;

/**
 * Created by Adilmar Dantas on 05/01/2017.
 */

public class Tempo {

    @Expose
    private Location location;

    public class Location {
        @Expose
        private String name;
        @Expose
        private String region;
        @Expose
        private String country;
        @Expose
        private String lat;
        @Expose
        private String lon;
        @Expose
        private String tz_id;
        @Expose
        private String localtime_epoch;
        @Expose
        private String localtime;

        public String getLocaltime() {
            return localtime;
        }

        public void setLocaltime(String localtime) {
            this.localtime = localtime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getTz_id() {
            return tz_id;
        }

        public void setTz_id(String tz_id) {
            this.tz_id = tz_id;
        }

        public String getLocaltime_epoch() {
            return localtime_epoch;
        }

        public void setLocaltime_epoch(String localtime_epoch) {
            this.localtime_epoch = localtime_epoch;
        }
    }
}
