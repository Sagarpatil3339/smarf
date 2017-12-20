package com.example.akshay.smarf.data;

import org.json.JSONObject;

/**
 * Created by akshay on 13/12/17.
 */

public class Units implements JSONPopulator {

    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature= data.optString("temperature");

    }
}
