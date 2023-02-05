package com.example.springapp.service;

import com.example.springapp.model.Book;
import com.example.springapp.rqrs.Reservation;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final Map<String, Reservation> reservationMap = new HashMap<>();

    public List<Book> getBooks(String subject) throws Exception {
        StringBuilder response = new StringBuilder();
        JsonParser jsonParser = new JsonParser();
        List<Book> listBook = new ArrayList<>();
        try {
            URL url = new URL("http://openlibrary.org/subjects/love.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JsonObject jsonBody = jsonParser.parse(response.toString()).getAsJsonObject();
            if (jsonBody != null) {
                JsonArray books = jsonBody.getAsJsonArray("works");
                books.forEach(item -> {
                    JsonObject bookElement = item.getAsJsonObject();
                    JsonArray subjects = bookElement.getAsJsonArray("subject");
                    for (JsonElement e : subjects) {
                        if (e.toString().contains(subject)) {
                            Book newBook = new Book()
                                .setTitle(bookElement.get("title").getAsString())
                                .setEditionNumber(bookElement.get("edition_count").getAsString());
                            JsonArray authors = bookElement.getAsJsonArray("authors");
                            for (JsonElement author : authors) {
                                if (newBook.getAuthor() == null) {
                                    newBook.setAuthor(
                                        author.getAsJsonObject().get("name").getAsString());
                                } else {
                                    newBook.setAuthor(
                                        newBook.getAuthor() + "," + author.getAsJsonObject()
                                            .get("name").getAsString());
                                }
                            }
                            listBook.add(newBook);
                            break;
                        }
                    }
                });
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return listBook;
    }

    public Reservation reserveBook(Reservation request) throws Exception {
        String reservationId = UUID.randomUUID().toString();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        cal.set(year, month, day + 1, 0, 0, 0);
        Timestamp minDate = new Timestamp(cal.getTime().getTime());
        if (request.getReservationDate().before(minDate)) {
           throw new Exception("reservation date at least 1 day after now");
        }
        request.setReservationId(reservationId);
        reservationMap.put(reservationId, request);
        return reservationMap.get(reservationId);
    }
}
