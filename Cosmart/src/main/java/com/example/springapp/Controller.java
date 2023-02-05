package com.example.springapp;

import com.example.springapp.model.Book;
import com.example.springapp.rqrs.Reservation;
import com.example.springapp.rqrs.Response;
import com.example.springapp.service.ReservationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

    private final ReservationService reservationService;

    @Autowired
    public Controller(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/book")
    public ResponseEntity<Response> getBookList(@RequestParam("subject" ) String subject) {
        Response response = new Response();
        try {
            response.setData(reservationService.getBooks(subject));
        } catch (Exception ex) {
            response.setSuccess(false);
            response.setErrorMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/book")
    public ResponseEntity<Response> reserveBook(@RequestBody Reservation request) {
        Response response = new Response();
        try {
            response.setData(reservationService.reserveBook(request));
        } catch (Exception ex) {
            response.setSuccess(false);
            response.setErrorMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
