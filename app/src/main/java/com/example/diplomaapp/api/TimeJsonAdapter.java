package com.example.diplomaapp.api;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.Time;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;

public class TimeJsonAdapter extends TypeAdapter<Time> {


    @Override
    public void write(JsonWriter writer, Time value) throws IOException {

        if (value == null) {
            writer.nullValue();
            return;
        }
        Format formatter = new SimpleDateFormat("HH:mm:ss");
        String s = formatter.format(value);
        writer.value(s);
    }

    @Override
    public Time read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        String date = reader.nextString();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        Time time2;
        System.out.println(date);
        try {
           time2 = new Time(formatter.parse(date).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return time2;
    }


}