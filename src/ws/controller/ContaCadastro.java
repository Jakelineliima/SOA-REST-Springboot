package ws.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import ws.model.Usuario;
import ws.repository.UsuarioRepository;

@RestController
public class ContaCadastro {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@RequestMapping(value = "criarconta", method = RequestMethod.GET,
			produces = "application/json")
	public List<Usuario> Get() {
		return usuarioRepository.findAll();
	}
	@RequestMapping(value = "/criarconta", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public Usuario post(@Valid @RequestBody Usuario conta) {       
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String senha = bcrypt.encode(conta.getPassword());
        conta.setPassword(senha);
        return usuarioRepository.save(conta);
    }
	
	
	@RequestMapping(value = "userdados/{id}", method = RequestMethod.GET,
			produces = "application/json")
	public ResponseEntity<Usuario> getPorCodigo(@PathVariable(value = "id") long id){
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if(usuario.isPresent()) {
			return new ResponseEntity<Usuario>(usuario.get(),HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	
	}
	
	
	@RequestMapping(value = "userdados/{id}", method = RequestMethod.PUT,
			consumes ="application/json", produces ="application/json")
	public ResponseEntity<Usuario> put(@PathVariable(value= "id") long id,
			@Valid @RequestBody Usuario editUsuario){
		Optional<Usuario> antigoDados = usuarioRepository.findById(id);
		if(antigoDados.isPresent()) {
			Usuario usuario = antigoDados.get();
			usuario.setUsername(editUsuario.getUsername());
			usuario.setPassword(editUsuario.getPassword());
			usuarioRepository.save(usuario);
			return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "userdados/{id}", method = RequestMethod.DELETE,
			produces = "application/json")
	public ResponseEntity<Object> delete(@PathVariable(value = "id") long id){
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if(usuario.isPresent()) {
			usuarioRepository.delete(usuario.get());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	
	
	
}
