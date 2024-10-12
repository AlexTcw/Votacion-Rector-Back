package com.example.sistemavotacionback.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "TBLINST")
public class Tblinst {

    @Id
    @Column(name = "CVEINST")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cveinst;

    @OneToMany(mappedBy = "tblinst", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Tblusuinst> tblusuinsts;

    @NonNull
    @Column(name = "NAMEINST", length = 200)
    private String nameinst;

    @NonNull
    @Column(name = "DIRINST", length = 200)
    private String dirinst;

    @NonNull
    @Column(name = "TELINST", length = 20)
    private String telinst;

    @NonNull
    @Column(name = "EMAILINST", length = 200)
    private String emailinst;
}