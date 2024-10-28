package com.votaciones.back.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "TBLVOTO")
public class Tblvoto {

    @Id
    @Column(name = "CVEVOTO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cvevoto;

    @Column(name = "FECHAVOTA")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechacan;
}
