package com.example.sistemavotacionback.service.util;

import com.example.sistemavotacionback.model.exception.JsonNullException;
import com.example.sistemavotacionback.model.pojos.consume.ConsumeJsonGeneric;
import com.example.sistemavotacionback.model.pojos.consume.ConsumeJsonUsuario;

public class ValidUtils {

    public static void validateConsume(Object consume){
        if (consume == null){
            throw new JsonNullException("El json es nulo o esta malformado");
        }

        if (consume instanceof ConsumeJsonUsuario consumeJsonUsuario){
            if (consumeJsonUsuario.getRolList() == null) {
                throw new JsonNullException("faltan los roles del usuario");
            } else if (consumeJsonUsuario.getInstList() == null) {
                throw new JsonNullException("faltan las instituciones del usuario");
            } else if (consumeJsonUsuario.getName() == null || consumeJsonUsuario.getName().isEmpty()
                    || consumeJsonUsuario.getEmail() == null  || consumeJsonUsuario.getEmail().isEmpty()
                    || consumeJsonUsuario.getNumCuenta() == null || consumeJsonUsuario.getNumCuenta().isEmpty()) {
                throw new JsonNullException("faltan datos personales del usuario");
            }
        }

        if (consume instanceof ConsumeJsonGeneric consumeJsonGeneric){
            if (consumeJsonGeneric.getDatos() == null) {
                throw new JsonNullException("Falta el campo datos en el json");
            }
        }
    }
}
