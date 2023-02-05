package com.example.springapp.rqrs;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Response {
    private Object data;
    private String errorMessage;
    private boolean success = true;
}
