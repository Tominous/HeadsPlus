package io.github.thatsmusic99.headsplus;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class UpdateChecker {

    private final static String versionURL = "https://api.spiget.org/v2/resources/40265/versions?size=1000";
    private final static String descriptionURL = "https://api.spiget.org/v2/resources/40265/updates?size=1000";

    static Object[] getUpdate() throws IOException {

        URL url = new URL(versionURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("User-Agent", "HeadsPlusPluginAgent");
        InputStream inputStream = connection.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream);
        JSONArray versionsArray = null;
        try {
            versionsArray = (JSONArray) new JSONParser().parse(reader);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        Double lastVersion;
        try {
            lastVersion = Double.parseDouble(((JSONObject) versionsArray.get(versionsArray.size() - 1)).get("name").toString());
        } catch (NumberFormatException e) {
            String[] s = ((JSONObject) versionsArray.get(versionsArray.size() - 1)).get("name").toString().split("\\.");
            StringBuilder v = new StringBuilder();
            for (int i = 1; i < s.length; i++) {
                if (s[i].matches("^[0-9]+$")) {
                    v.append(s[i]);
                }
            }
            lastVersion = Double.valueOf(s[0] + "." + v.toString());
        }
        Double currentVersion;
        try {
            currentVersion = Double.parseDouble(HeadsPlus.getInstance().getDescription().getVersion());
        } catch (NumberFormatException e) {
            String[] s = HeadsPlus.getInstance().getDescription().getVersion().split("\\.");
            StringBuilder v = new StringBuilder();
            for (int i = 1; i < s.length; i++) {
                if (s[i].matches("^[0-9]+$")) {
                    v.append(s[i]);
                }
            }
            currentVersion = Double.valueOf(s[0] + "." + v.toString());
        }
        String latestVersionString = ((JSONObject) versionsArray.get(versionsArray.size() - 1)).get("name").toString();

        url = new URL(descriptionURL);
        connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("User-Agent", "HeadsPlusPluginAgent");
        inputStream = connection.getInputStream();
        reader = new InputStreamReader(inputStream);
        JSONArray updatesArray = null;
        try {
            updatesArray = (JSONArray) new JSONParser().parse(reader);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        if (lastVersion > currentVersion) {

            String updateName = ((JSONObject) updatesArray.get(updatesArray.size() - 1)).get("title").toString();
            return new Object[]{lastVersion, updateName, latestVersionString};
        }
        return null;
    }
}
