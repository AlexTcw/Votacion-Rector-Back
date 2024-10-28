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
@Table(name = "TBLROL")
public class Tblrol {

    @Id
    @Column(name = "CVEROL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cverol;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TBLROL_PERMISSION", joinColumns = @JoinColumn(name = "CVEROL"), inverseJoinColumns = @JoinColumn(name = "cvepermission"))
    private Set<Tblpermission> permissions = new HashSet<>();

    @NonNull
    @Column(name = "NAMEROL", length = 200)
    private String namerol;
}
