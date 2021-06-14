package ws.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ws.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> { 
	Optional<Usuario> findByUsername(String username);	
}
