package com.example.springapp.servicetest;

import com.example.springapp.rqrs.Reservation;
import com.example.springapp.service.ReservationService;
import java.sql.Timestamp;
import java.util.Calendar;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void testReserveBookSuccess() throws Exception {
        Reservation reservation = reservationService.process(mockReservation(1));
        Assert.assertNotNull(reservation);
        Assert.assertNotNull(reservation.getReservationId());
        Assert.assertFalse(reservation.getComplete());
    }

    @Test
    void testReserveBookReserveDateNotValid() throws Exception {
        try {
            reservationService.process(mockReservation(0));
        } catch (Exception ex) {
            Assert.assertEquals("reservation date at least 1 day after now", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    private Reservation mockReservation(int day) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        return new Reservation()
            .setAuthor("Aston")
            .setEditionNumber("200")
            .setReservationDate(new Timestamp(cal.getTime().getTime()));
    }
}
