package com.votaciones.back.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBLPERMISSION")
public class Tblpermission {

    @Id
    @Column(name = "cvepermission")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cvepermission;

    @Column(unique = true, nullable = false, updatable = false)
    private String namePermission;
}
