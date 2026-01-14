package br.com.logistrack.repository;


import br.com.logistrack.entity.Usuario;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
    Optional<Usuario> findById(@NonNull Long id);
}
