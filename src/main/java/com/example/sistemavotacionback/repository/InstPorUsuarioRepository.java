package com.example.sistemavotacionback.repository;

import com.example.sistemavotacionback.model.entity.Tblusuinst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstPorUsuarioRepository extends JpaRepository<Tblusuinst, Integer> {

    List<Tblusuinst> findByTbluser_Cveuser(long cveuser);

    boolean existsByTbluser_Cveuser(long cveuser);

    @Modifying
    @Query(value = "DELETE FROM tblusuinst WHERE cveuser = :cveuser", nativeQuery = true)
    void deleteByCveusuinst(@Param("cveuser") long cveuser);
}
