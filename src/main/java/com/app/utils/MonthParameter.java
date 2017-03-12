package com.app.utils;

import javax.ws.rs.WebApplicationException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MonthParameter implements Serializable {

    public static final String FORMAT = "yyyy-MM";

    private Date date = null;

    public MonthParameter(String dateStr) throws WebApplicationException {
        if (dateStr.isEmpty()) {
            this.date = null;
            return;
        }
        final DateFormat dateFormat = new SimpleDateFormat(FORMAT);
        try {
            this.date = dateFormat.parse(dateStr);
        } catch (ParseException e) {

        }
    }

    public Date getDate() {
        return date;
    }
}