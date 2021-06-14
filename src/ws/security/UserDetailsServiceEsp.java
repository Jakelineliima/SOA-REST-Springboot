package ws.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ws.model.Usuario;
import ws.repository.UsuarioRepository;

@Service
public class UserDetailsServiceEsp implements UserDetailsService {
 
    @Autowired
    private UsuarioRepository usuarioRepository;
     
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        usuario.orElseThrow(() -> new UsernameNotFoundException("Não Encontrado.: " + username));
        return usuario.map(UserDetailsEsp::new).get();    	
    }
 
}