package com.spreys.motorbikedirectory.Network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spreys.motorbikedirectory.Model.Make;
import com.spreys.motorbikedirectory.Model.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vspreys on 18/06/16.
 */

public class ApiWrapper {
    private static final String apiUrl = "http://10.0.2.2:9998/motorbikes";

    public static List<Make> GetAllMakes() {
        return new Gson().fromJson(GetStringFromUrl(apiUrl), new TypeToken<ArrayList<Make>>(){}.getType());
    }

    public static boolean AddModel(Model model, String makeName) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("PUT");

        //Set the parameters
        httpCon.setRequestProperty("make", makeName);
        httpCon.setRequestProperty("model", model.getName());
        httpCon.setRequestProperty("modelClass", model.getModelClass());
        httpCon.setRequestProperty("engineSize", model.getEngineSize());

        OutputStreamWriter out = new OutputStreamWriter(
                httpCon.getOutputStream());
        out.write("Resource content");
        out.close();
        String result = ApiWrapper.ConvertStreamToString(httpCon.getInputStream());

        return false;
    }

    public static JSONObject GetJsonObjectFromUrl(String url) {
        return GetJsonObjectFromUrl(url, null);
    }

    private static JSONObject GetJsonObjectFromUrl(String url, HashMap<String, String> properties) {
        try {
            String apiResponse = GetStringFromUrl(url, properties);
            if (apiResponse != null) {
                return new JSONObject(GetStringFromUrl(url, properties));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String GetStringFromUrl(String url) {
        return GetStringFromUrl(url, null);
    }

    private static String GetStringFromUrl(String url, HashMap<String, String> properties) {
        URL myURL;
        try {
            myURL = new URL(url);
            URLConnection conn = myURL.openConnection();

            if (properties != null) {
                for(Map.Entry<String, String> parameter: properties.entrySet()){
                    conn.setRequestProperty(parameter.getKey(), parameter.getValue());
                }
            }
            conn.setDoOutput(false);
            conn.setDoInput(true);

            InputStream is = conn.getInputStream();
            return ConvertStreamToString(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String ConvertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
