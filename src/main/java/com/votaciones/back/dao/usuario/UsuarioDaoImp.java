package com.votaciones.back.dao.usuario;
import com.votaciones.back.model.entity.Tbluser;
import com.votaciones.back.model.exception.ResourceNotFoundException;
import com.votaciones.back.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class UsuarioDaoImp implements UsuarioDao {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDaoImp(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Tbluser createOrUpdateUsuario(Tbluser tbluser) {
        return usuarioRepository.save(tbluser);
    }

    @Override
    public Tbluser findTblUserByCveuser(long cveuser) {
        return usuarioRepository.findTblUserByCveuser(cveuser);
    }

    @Override
    public Page<Object> findUserByKeyPage(String key, Pageable pageable) {
        return usuarioRepository.findUserByKeyPage(key, pageable);
    }

    @Override
    public boolean existsTbluserByEmailuser(String emailuser) {
        return usuarioRepository.existsTbluserByEmailuser(emailuser);
    }

    @Override
    public boolean existsTbluserByNumcunetauser(String numcunetauser) {
        return usuarioRepository.existsTbluserByNumcunetauser(numcunetauser);
    }

    @Override
    public void deleteTbluserByCveuser(long cveuser) {
        usuarioRepository.deleteTbluserByCveuser(cveuser);
    }

    @Override
    public boolean existsTbluserByCveuser(long cveuser) {
        return usuarioRepository.existsTbluserByCveuser(cveuser);
    }

    @Override
    public Tbluser findTbluserByEmailuser(String emailuser) {
        return usuarioRepository.findTbluserByEmailuser(emailuser);
    }

    @Override
    public Tbluser findTbluserByNumcunetauser(String numcunetauser) {
        return usuarioRepository.findTbluserByNumcunetauser(numcunetauser);
    }

    @Override
    public void saveall(Set<Tbluser> users) {
        usuarioRepository.saveAll(users);
    }

    @Override
    public List<Tbluser> findAllUsers(){
       return usuarioRepository.findAll();
    }

    @Override
    public List<Long>findAllUsersCve(){
        return usuarioRepository.findAllCveuser();
    }

    @Override
    public boolean existTbluserByNameUser(String name){
        return usuarioRepository.existsTbluserByNameusr(name);
    }

    @Override
    public Tbluser findTbluserByNameUser(String name){
        return usuarioRepository.findTbluserByNameusr(name);
    }

    @Override
    public boolean existTbluserByApeUser(String lastName){
        return usuarioRepository.existsTbluserByApeuser(lastName);
    }

    @Override
    public Tbluser findTbluserByApeUser(String LastName){
        return usuarioRepository.findTbluserByApeuser(LastName);
    }

    @Override
    public boolean existTbluserByNameAndApeUser(String name, String lastName){
        return usuarioRepository.existsTbluserByNameusrAndApeuser(name, lastName);
    }

    @Override
    public Tbluser findTbluserByNameAndApeUser(String name, String lastName){
        return usuarioRepository.findTbluserByNameusrAndApeuser(name, lastName);
    }

    @Override
    public Tbluser findTbluserByEmailOrNumcuentaUser(String param) {
        Tbluser usuario = null;
        if (usuarioRepository.existsTbluserByEmailuser(param)) {
             usuario = usuarioRepository.findTbluserByEmailuser(param);
        } else if (usuarioRepository.existsTbluserByNumcunetauser(param)) {
            usuario = usuarioRepository.findTbluserByNumcunetauser(param);
        }
        if (usuario == null) {
            throw new ResourceNotFoundException("No existe un usuario con el parámetro: " + param);
        }

        return usuario;
    }
}
