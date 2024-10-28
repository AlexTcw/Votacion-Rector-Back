package com.votaciones.back.model.pojos.consume;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ConsumeJsonUsuario {
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String numCuenta;
    private String genero;
    private List<Integer> instList;
    private List<Integer> rolList;

}
