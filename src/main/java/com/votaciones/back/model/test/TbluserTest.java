package com.votaciones.back.model.test;

import com.votaciones.back.model.entity.Tblinst;
import com.votaciones.back.model.entity.Tblrol;
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
@Table(name = "TBLUSER_TEST")
public class TbluserTest {

    @Id
    @Column(name = "CVEUSERTEST")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cveusertest;

    @NonNull
    @Column(name = "EMAILUSERTEST", length = 200, unique = true)
    private String emailusertest;

    @NonNull
    @Column(name = "PASSWORDUSERTEST", length = 200)
    private String passwordusertest;

    @NonNull
    @Column(name = "NUMCUNETAUSERTEST", length = 20)
    private String numcunetausertest;
}
