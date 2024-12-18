package com.votaciones.back.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "TBLRECTOR")
public class Tblrector {

    @Id
    @Column(name = "CVERECTOR")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cverector;

    @OneToOne
    @JoinColumn(name = "CVECAN", referencedColumnName = "CVECAN")
    private Tblcandidato candidato;

    @Column(name = "FECHAINICIO")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechainicio;

    @Column(name = "FECHAFIN")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechafin;

    @Column(name = "DESCRIPCION", columnDefinition = "LONGTEXT")
    private String descripcion;
}
