package com.votaciones.back.model.pojos.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ResponseJsonGeneric {
    private Map<String,Object> data;
}
