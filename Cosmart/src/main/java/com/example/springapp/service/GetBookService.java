package com.example.springapp.service;

import com.example.springapp.model.Book;
import com.example.springapp.serviceinterface.Action;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GetBookService implements Action<List<Book>, String> {
    protected static final String BOOK_URL = "http://openlibrary.org/subjects/love.json";

    @Override
    public List<Book> process(String subject) throws Exception {
        log.info("request {}", subject);
        return process(subject, BOOK_URL);
    }

    //for testing purpose
    public List<Book> process(String subject, String urlLink) throws Exception{
        log.info("request {}", subject);
        List<Book> listBook = new ArrayList<>();
        try {
            URL url = new URL(urlLink);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonBody = jsonParser.parse(response.toString()).getAsJsonObject();
            if (jsonBody != null) {
                JsonArray books = jsonBody.getAsJsonArray("works");
                books.forEach(item -> {
                    JsonObject bookElement = item.getAsJsonObject();
                    JsonArray subjects = bookElement.getAsJsonArray("subject");
                    for (JsonElement e : subjects) {
                        if (e.getAsString().equalsIgnoreCase(subject)) {
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
            log.warn("failed to get list of book, reason {}", ex.getMessage());
            throw new Exception(ex.getMessage());
        }
        return listBook;
    }
}
