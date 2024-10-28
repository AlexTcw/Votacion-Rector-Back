package com.votaciones.back.model.pojos.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ResponseJsonUsuario {
    private Long cveuser;
    private String name;
    private String lastName;
    private String email;
    private String numCuenta;
    private List<String> instList;
    private List<String> rolList;
}
