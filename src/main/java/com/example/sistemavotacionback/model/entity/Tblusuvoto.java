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
@Table(name = "TBLUSUVOTO")
public class Tblusuvoto {

    @Id
    @Column(name = "CVEUSUVOTO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cveusuvoto;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVEUSER", nullable = false)
    private Tbluser tbluser;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVEVOTO", nullable = false)
    private Tblvoto tblvoto;
}
