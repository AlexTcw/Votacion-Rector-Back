package com.votaciones.back.model.pojos.consume;

import lombok.*;

import java.util.Map;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ConsumeJsonGeneric {
    private Map<String, Object> datos;
}
