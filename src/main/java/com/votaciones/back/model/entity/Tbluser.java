package com.votaciones.back.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBLUSER")
public class Tbluser {

    @Id
    @Column(name = "CVEUSER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cveuser;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USU_ROL", joinColumns = @JoinColumn(name = "CVEUSER"), inverseJoinColumns = @JoinColumn(name = "CVEROL"))
    private Set<Tblrol> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USU_INST", joinColumns = @JoinColumn(name = "CVEUSER"), inverseJoinColumns = @JoinColumn(name = "CVEINST"))
    private Set<Tblinst> instituciones = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USU_CAN", joinColumns = @JoinColumn(name = "CVEUSER"), inverseJoinColumns = @JoinColumn(name = "CVECAN"))
    private Set<Tblcandidato> candidaturas = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USU_VOT", joinColumns = @JoinColumn(name = "CVEUSER"), inverseJoinColumns = @JoinColumn(name = "CVEUSUVOTO"))
    private Set<Tblvoto> votos = new HashSet<>();

    @NonNull
    @Column(name = "NAMEUSR", length = 200)
    private String nameusr;

    @NonNull
    @Column(name = "APEUSER", length = 200)
    private String apeuser;

    @NonNull
    @Column(name = "EMAILUSER", length = 200, unique = true)
    private String emailuser;

    @NonNull
    @Column(name = "PASSWORDUSER", length = 200)
    private String passworduser;

    @NonNull
    @Column(name = "NUMCUNETAUSER", length = 20)
    private String numcunetauser;

    @NonNull
    @Pattern(regexp = "^[MF]$", message = "El género debe ser 'M' o 'F'")
    @Column(name = "GENEROUSER", length = 1, nullable = false)
    private String generouser;
}
