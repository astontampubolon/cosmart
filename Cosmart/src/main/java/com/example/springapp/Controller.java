package com.example.springapp;

import com.example.springapp.error.BadRequesException;
import com.example.springapp.rqrs.Reservation;
import com.example.springapp.rqrs.Response;
import com.example.springapp.service.GetBookService;
import com.example.springapp.service.ReservationService;
import com.example.springapp.serviceinterface.Action;
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
    private final GetBookService getBookService;

    @Autowired
    public Controller(ReservationService reservationService,
                      GetBookService getBookService) {
        this.reservationService = reservationService;
        this.getBookService = getBookService;
    }

    @GetMapping("/book")
    public ResponseEntity<Response> getBookList(@RequestParam(value = "subject", required = false)
                                                    String subject) {
        return handleRequest(getBookService, subject);
    }

    @PostMapping("/book")
    public ResponseEntity<Response> reserveBook(@RequestBody Reservation request) {
        return handleRequest(reservationService, request);
    }

    private ResponseEntity<Response> handleRequest(Action service,
                                                   Object request) {
        Response response = new Response();
        try {
            response.setData(service.process(request));
        } catch (BadRequesException ex) {
            response.setSuccess(false);
            response.setErrorMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            response.setSuccess(false);
            response.setErrorMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
