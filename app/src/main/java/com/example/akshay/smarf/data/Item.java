package com.example.akshay.smarf.data;

import org.json.JSONObject;

/**
 * Created by akshay on 13/12/17.
 */

public class Item implements JSONPopulator {

    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {

        condition= new Condition();
        condition.populate(data.optJSONObject("condition"));

    }
}
