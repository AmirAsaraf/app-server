package com.app.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.WebApplicationException;
import java.io.Serializable;

public class FilterParameter implements Serializable {

    private JSONObject jsonObject = null;
    private JSONParser parser = new JSONParser();

    public FilterParameter(String filterString) throws WebApplicationException, ParseException {
        if (filterString.isEmpty()) {
            return;
        }

        jsonObject = (JSONObject) parser.parse(filterString);
    }

    public JSONObject getEntries() {
        return jsonObject;
    }
}