package com.votaciones.back.model.pojos.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ResponseJsonLongString {
    private Long id;
    private String key;
}
