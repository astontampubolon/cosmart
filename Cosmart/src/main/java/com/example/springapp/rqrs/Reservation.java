package com.example.springapp.rqrs;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
import net.minidev.json.annotate.JsonIgnore;

@Data
@Accessors(chain = true)
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
