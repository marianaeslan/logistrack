package br.com.logistrack.repository;


import br.com.logistrack.dto.usuario.RegisterDTO;
import br.com.logistrack.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String email);
    Optional<Usuario> findById(Long id);
}
