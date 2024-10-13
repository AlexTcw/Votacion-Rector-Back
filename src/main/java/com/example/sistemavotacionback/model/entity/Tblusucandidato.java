package com.example.sistemavotacionback.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "TBLUSUCANDIDATO")
public class Tblusucandidato {

    @Id
    @Column(name = "CVEUSUCAN")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cveusucan;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVEUSER", nullable = false)
    private Tbluser tbluser;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVECAN", nullable = false)
    private Tblcandidato tblcandidato;
}
