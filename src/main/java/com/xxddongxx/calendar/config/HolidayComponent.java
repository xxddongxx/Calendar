package com.xxddongxx.calendar.config;

import com.xxddongxx.calendar.model.Holiday;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class HolidayComponent {
    @Value("${apis.data}")
    private String path;

    @Value("${apis.service.key}")
    private String key;

    private String HOLIDAY_PATH = "getHoliDeInfo";
    private String TYPE = "json";
    private String SOL_YEAR = String.valueOf(LocalDate.now().getYear());
    private String SOL_MONTH = String.format("%02d", LocalDate.now().getMonthValue());
    private String NUM_OF_ROWS = String.valueOf(1000);

    public StringBuilder getPath() throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder(path+HOLIDAY_PATH);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + key);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(NUM_OF_ROWS, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode(TYPE, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode(SOL_YEAR, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("solMonth", "UTF-8") + "=" + URLEncoder.encode(SOL_MONTH, "UTF-8"));

        return urlBuilder;
    }

    public StringBuilder getPath(String year, String month) throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder(path+HOLIDAY_PATH);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + key);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(NUM_OF_ROWS, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode(TYPE, "UTF-8"));

        if(year.isEmpty()){
            urlBuilder.append("&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode(SOL_YEAR, "UTF-8"));
        } else {
            urlBuilder.append("&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode(year, "UTF-8"));

            if(!month.isEmpty()){
                urlBuilder.append("&" + URLEncoder.encode("solMonth", "UTF-8") + "=" + URLEncoder.encode(month, "UTF-8"));
            }
        }

        return urlBuilder;
    }

    public List<Holiday> getHoliday(String urlStr){
        List<Holiday> holidayList = new ArrayList<>();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader reader = null;

            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(response.toString());

            JSONObject responseObj = (JSONObject) root.get("response");
            JSONObject body = (JSONObject) responseObj.get("body");

            int totalCount = Integer.parseInt(body.get("totalCount").toString());

            if(totalCount > 0){
                JSONObject items = (JSONObject) body.get("items");
                Object obj = items.get("item");
                JSONArray itemArray = new JSONArray();

                if (obj instanceof JSONArray) {
                    itemArray = (JSONArray) obj;
                } else if (obj instanceof JSONObject) {
                    itemArray.add(obj);
                }

                for (Object itemObj : itemArray) {
                    JSONObject item = (JSONObject) itemObj;
                    String dateName = (String) item.get("dateName");
                    boolean isHoliday = item.get("isHoliday").equals("Y") ? true : false;
                    Long locdate = (Long) item.get("locdate");

                    String dateStr = String.valueOf(locdate);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                    LocalDate localDate = LocalDate.parse(dateStr, formatter);

                    Holiday holiday = Holiday.builder()
                            .dateName(dateName)
                            .holidate(localDate)
                            .isHoliday(isHoliday)
                            .build();
                    holidayList.add(holiday);
                }
            }
        } catch (Exception ex) {
            ex.getMessage();
        }
        return holidayList;
    }
}
