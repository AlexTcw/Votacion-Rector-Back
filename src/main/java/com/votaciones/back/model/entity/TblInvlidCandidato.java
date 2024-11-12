package com.votaciones.back.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBLINVALIDCANDIDATO")
public class TblInvlidCandidato {

    @Id
    @Column(name = "CVEINVCAN")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cveinvcan;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "INV_CAN", joinColumns = @JoinColumn(name = "CVEINVCAN"), inverseJoinColumns = @JoinColumn(name = "CVECAN"))
    private Set<Tblcandidato> candidaturas = new HashSet<>();

    @Column(name = "NAMEINVALID", length = 500)
    private String nameinvalid;
}
