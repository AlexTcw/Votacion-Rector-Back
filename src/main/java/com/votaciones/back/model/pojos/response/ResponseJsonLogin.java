package com.votaciones.back.model.pojos.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseJsonLogin {

    private Long cveuser;
    private String name;
    private String lastName;
    private String email;
    private String numCuenta;
    private List<String> instList;
    private List<String> rolList;
    private String token;
}
