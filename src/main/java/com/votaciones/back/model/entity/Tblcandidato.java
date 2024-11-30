package com.votaciones.back.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(mappedBy = "candidaturas", fetch = FetchType.EAGER)
    private Set<Tbluser> usuarios = new HashSet<>();

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

    @Column(name = "RESUMEN", columnDefinition = "LONGTEXT")
    private String resumen;

    @Column(name = "INST1")
    private Integer inst1;

    @Column(name = "INST2")
    private Integer inst2;

    @Column(name = "INST3")
    private Integer inst3;

    @Column(name = "ALUMNOCOUNT")
    private Integer alumnoCount;

    @Column(name = "MAESTROCOUNT")
    private Integer maestroCount;

    @Column(name = "ADMINCOUNT")
    private Integer adminCount;
}
