package com.example.sistemavotacionback.model.pojos.consume;

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
    private String numCuenta;
    private List<Integer> instList;
    private List<Integer> rolList;

}
