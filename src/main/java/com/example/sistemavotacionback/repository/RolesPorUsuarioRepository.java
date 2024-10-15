package com.example.sistemavotacionback.repository;

import com.example.sistemavotacionback.model.entity.Tblusuinst;
import com.example.sistemavotacionback.model.entity.Tblusurol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolesPorUsuarioRepository extends JpaRepository<Tblusurol, Integer> {

    List<Tblusurol> findByTbluser_Cveuser(long cveuser);

    @Modifying
    @Query(value = "DELETE FROM tblusurol WHERE cveuser = :cveuser", nativeQuery = true)
    void deleteByCveusurol(@Param("cveuser") long cveuser);

    boolean existsByTbluser_Cveuser(long cveuser);
}
