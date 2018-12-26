package com.example.hoangquocphu.demosqlite.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class JsonReader {
    public static String readAll(Reader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        int read;
        while ((read = reader.read()) != -1) {
            builder.append((char) read);
        }
        return builder.toString();
    }

    /**
     * Hàm trả về JSONObject
     *
     * @param url - Truyền link URL có định dạng JSON
     * @return - Trả về JSONOBject
     */

    public static JSONObject readFileJsonFromUrl(String url) throws IOException, JSONException {
        InputStream inputStream = new URL(url).openStream();
        try {
            // đọc nội dung file với Unicode:
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String jsonData = readAll(reader);
            JSONObject jsonObject = new JSONObject(jsonData);
            return jsonObject;
        } finally {
            inputStream.close();
        }
    }
}
