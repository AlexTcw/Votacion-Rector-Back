package com.votaciones.back.model.exception;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private int status;
}
