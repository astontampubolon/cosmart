package com.example.springapp.service;

import com.example.springapp.rqrs.Reservation;
import com.example.springapp.serviceinterface.Action;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReservationService implements Action<Reservation, Reservation> {
    private final Map<String, Reservation> reservationMap = new HashMap<>();

    @Override
    public Reservation process(Reservation request) throws Exception {
        log.info("request {}", request);
        String reservationId = UUID.randomUUID().toString();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        cal.set(year, month, day + 1, 0, 0, 0);
        Timestamp minDate = new Timestamp(cal.getTime().getTime());
        if (request.getReservationDate().before(minDate)) {
            log.warn("Min reservation date {}", cal.getTime());
            throw new Exception("reservation date at least 1 day after now");
        }
        request.setReservationId(reservationId);
        reservationMap.put(reservationId, request);
        return reservationMap.get(reservationId);
    }
}
