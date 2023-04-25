package com.example.diplomaapp.api;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateJsonAdapter extends TypeAdapter<Date> {


    @Override
    public void write(JsonWriter writer, Date value) throws IOException {

        if (value == null) {
            writer.nullValue();
            return;
        }
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter.format(value);

        writer.value(s);
    }

    @Override
    public Date read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        String date = reader.nextString();
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date2;
        try {
            date2 = formatter1.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date2;
    }


}