package com.example.sistemavotacionback.model.pojos.response;

import com.example.sistemavotacionback.model.entity.Tblinst;
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
    private String password;
    private List<String> instList;
    private List<String> rolList;
}
