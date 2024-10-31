package com.votaciones.back.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "TBLCANDIDATO")
public class Tblcandidato {

    @Id
    @Column(name = "CVECAN")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cvecan;

    @OneToOne(mappedBy = "candidato")
    private Tblrector rector;

    @NonNull
    @Column(name= "PLANTILLA", length = 200)
    private String plantilla;

    @Column(name = "VOTOS")
    private Long votos;

    @Column(name = "FECHACAN")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechacan;

    @Column(name = "ANIOCAN")
    private Integer aniocan;

    @Column(name = "RESUMEN")
    private String resumen;
}
