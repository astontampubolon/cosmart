package com.example.springapp.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Book {
    private String title;
    private String author;
    private String editionNumber;

}
