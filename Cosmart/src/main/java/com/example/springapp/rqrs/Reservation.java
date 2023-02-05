package com.example.springapp.rqrs;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Reservation {
    private String reservationId;
    @NonNull
    private String title;
    @NonNull
    private String author;
    @NonNull
    private String editionNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp reservationDate;
    private Boolean complete = false;
}
