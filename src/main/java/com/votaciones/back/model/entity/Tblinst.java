package com.votaciones.back.model.entity;

import jakarta.persistence.*;
import lombok.*;

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
