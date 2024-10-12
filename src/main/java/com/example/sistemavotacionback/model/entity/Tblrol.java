package com.example.sistemavotacionback.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "TBLROL")
public class Tblrol {

    @Id
    @Column(name = "CVEROL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cverol;

    @OneToMany(mappedBy = "tblrol", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Tblusurol> tblusurols;

    @NonNull
    @Column(name = "NAMEROL", length = 200)
    private String namerol;
}
