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
@Table(name = "TBLUSUINST")
public class Tblusuinst {

    @Id
    @Column(name = "CVEUSUINST")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cveusuinst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVEUSER", nullable = false)
    @JsonBackReference
    private Tbluser tbluser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVEINST", nullable = false)
    @JsonBackReference
    private Tblinst tblinst;
}
