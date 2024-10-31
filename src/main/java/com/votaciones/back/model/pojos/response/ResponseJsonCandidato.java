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
public class ResponseJsonCandidato {
    private Long cveuser;
    private Long cvecan;
    private String name;
    private String lastName;
    private String email;
    private List<String> instList;
    private String plantilla;
    private String resumen;

}
