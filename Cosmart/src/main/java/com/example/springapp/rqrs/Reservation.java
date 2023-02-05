package com.example.springapp.rqrs;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Reservation {
    private String reservationId;
    @NotBlank(message = "Book title can't null or empty")
    private String title;
    @NotBlank(message = "author can't null or empty")
    private String author;
    @NotBlank(message = "editionNumber can't null or empty")
    private String editionNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "reservation date can't null")
    private Timestamp reservationDate;
    private Boolean complete = false;
}
