package com.fenix.temperatura;

import com.google.gson.annotations.Expose;

/**
 * Created by Adilmar Dantas on 05/01/2017.
 */

public class Result {

    @Expose
    private Current current;
    @Expose
    private Location location;

    public class Current {

        @Expose
        private String name;

    }

    public class Location {

    }
}
