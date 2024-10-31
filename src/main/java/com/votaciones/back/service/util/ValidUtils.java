package com.votaciones.back.service.util;

import com.votaciones.back.model.exception.JsonNullException;
import com.votaciones.back.model.pojos.consume.ConsumeJsonCandidato;
import com.votaciones.back.model.pojos.consume.ConsumeJsonGeneric;
import com.votaciones.back.model.pojos.consume.ConsumeJsonUsuario;

public class ValidUtils {

    public static void validateConsume(Object consume){
        if (consume == null){
            throw new JsonNullException("El json es nulo o esta malformado");
        }

        if (consume instanceof ConsumeJsonGeneric consumeJsonGeneric){
            if (consumeJsonGeneric.getDatos() == null) {
                throw new JsonNullException("Falta el campo datos en el json");
            }
        }

        if (consume instanceof ConsumeJsonUsuario consumeJsonUsuario){
            if (consumeJsonUsuario.getRolList() == null) {
                throw new JsonNullException("faltan los roles del usuario");
            } else if (consumeJsonUsuario.getInstList() == null) {
                throw new JsonNullException("faltan las instituciones del usuario");
            } else if (consumeJsonUsuario.getName() == null || consumeJsonUsuario.getName().isEmpty()
                    || consumeJsonUsuario.getEmail() == null  || consumeJsonUsuario.getEmail().isEmpty()
                    || consumeJsonUsuario.getNumCuenta() == null || consumeJsonUsuario.getNumCuenta().isEmpty()
                    || consumeJsonUsuario.getGenero() == null || consumeJsonUsuario.getGenero().isEmpty()) {
                throw new JsonNullException("faltan datos personales del usuario");
            } else if (consumeJsonUsuario.getPassword() == null) {
                throw new JsonNullException("faltan la contraseña del usuario");
            }
        }

        if (consume instanceof ConsumeJsonCandidato consumeJsonCandidato){
            if (consumeJsonCandidato.getCveuser() == null || consumeJsonCandidato.getCveuser() == 0){
            throw new JsonNullException("falta la clave del usuario");
            } else if (consumeJsonCandidato.getPlantilla() == null || consumeJsonCandidato.getPlantilla().isEmpty()
                || consumeJsonCandidato.getResumen() == null || consumeJsonCandidato.getResumen().isEmpty()){
                    throw new JsonNullException("faltan datos del candidato");
                }
            }
        }
}
