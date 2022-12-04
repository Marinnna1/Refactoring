package ru.javaschool.JavaSchoolBackend1.service;


import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import ru.javaschool.JavaSchoolBackend1.controller.MessageController;
import ru.javaschool.JavaSchoolBackend1.dto.EventDto;
import ru.javaschool.JavaSchoolBackend1.mq.CustomMessage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;



@Service
@RequiredArgsConstructor
public class EventsService {


    private final MessageController messageController;



    public Long getEventsCount() throws IOException {
        EventDto eventDto = new EventDto();
        eventDto.setStatus("Planned");

        String jsonInputForEventsCount = "{\"status\": \"Planned\", \"filter\": \"Today\"}";

        URL url = new URL("http://localhost:9000/events/events_count");
        HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();

        sendRequest(httpURLConnection, jsonInputForEventsCount);

        StringBuilder response = getResponse(httpURLConnection);

       return Long.parseLong(response.toString());
    }


    public List<EventDto> getAll(Integer pageNumber) throws IOException {
        String jsonInputForEventsCount = "{\"status\": \"Planned\", \"filter\": \"Today\", \"pageNumber\": \"" +
              pageNumber +   "\"}";

        URL url = new URL("http://localhost:9000/events/all");
        HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();

        sendRequest(httpURLConnection, jsonInputForEventsCount);

        StringBuilder response = getResponse(httpURLConnection);

        List<EventDto> eventDtos = new ArrayList<>();

        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(response.toString());
            JSONArray jsonArray = (JSONArray) object;

            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                String json = jsonObject.toJSONString();
                EventDto eventDto = new Gson().fromJson(json, EventDto.class);
                eventDtos.add(eventDto);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return eventDtos;
    }


    private void sendRequest(HttpURLConnection httpURLConnection, String input) throws IOException {
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpURLConnection.setDoOutput(true);

        //Adding Post Data
        OutputStream outputStream=httpURLConnection.getOutputStream();
        outputStream.write(input.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private StringBuilder getResponse(HttpURLConnection httpURLConnection) throws IOException {
        String line="";
        InputStreamReader inputStreamReader=new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        StringBuilder response=new StringBuilder();
        while ((line=bufferedReader.readLine())!=null){
            response.append(line);
        }
        bufferedReader.close();
        return response;
    }



    public void send(CustomMessage customMessage) {
        messageController.recMessage(customMessage);
    }
}
