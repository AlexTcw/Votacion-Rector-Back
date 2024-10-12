package com.example.sistemavotacionback.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "TBLUSER")
public class Tbluser {

    @Id
    @Column(name = "CVEUSER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cveuser;

    // Cambia "TBLUSER" por "tbluser"
    @OneToMany(mappedBy = "tbluser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Tblusurol> tblusurols;

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
}
