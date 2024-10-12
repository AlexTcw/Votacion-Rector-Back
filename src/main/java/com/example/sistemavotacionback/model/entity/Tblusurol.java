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
    @Table(name = "TBLUSUROL")
    public class Tblusurol {

        @Id
        @Column(name = "CVEUSUROL")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long cveusurol;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "CVEUSER", nullable = false)
        @JsonBackReference
        private Tbluser tbluser;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "CVEROL", nullable = false)
        @JsonBackReference
        private Tblrol tblrol;
    }

