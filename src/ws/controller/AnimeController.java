 package ws.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ws.model.Anime;
import ws.repository.AnimeRepository;

@RestController
public class AnimeController {

	@Autowired
	private AnimeRepository animeRepository;
	
	@RequestMapping(value = "/animecad", method = RequestMethod.GET, 
			produces = "application/json")
	public List<Anime> Get() {
		return animeRepository.findAll();
	}
	
	@RequestMapping(value = "/animecad/{id}", method = RequestMethod.GET,
			produces = "application/json")
	public ResponseEntity<Anime> getPorId(@PathVariable(value = "id") long id) {
		Optional<Anime> anime = animeRepository.findById(id);
		if (anime.isPresent()) {
			return new ResponseEntity<Anime>(anime.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/animecad", method = RequestMethod.POST,
			consumes = "application/json", produces = "application/json")
	public Anime post(@Valid @RequestBody Anime anime) {
		return animeRepository.save(anime);
	}
	
	@RequestMapping(value = "/anime/{id}", method = RequestMethod.PUT,
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<Anime> put(@PathVariable(value = "id") long id,
			@Valid @RequestBody Anime novoAnime) {
		Optional<Anime> antigoAnime = animeRepository.findById(id);
		if (antigoAnime.isPresent()) {
			Anime anime = antigoAnime.get();
			anime.setNome(novoAnime.getNome());
			anime.setEpisodio(novoAnime.getEpisodio());
			anime.setTemporada(novoAnime.getTemporada());
	
			
			animeRepository.save(anime);
			return new ResponseEntity<Anime>(anime, HttpStatus.OK); 
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/anime/{id}", method = RequestMethod.DELETE,
			produces = "application/json")
	public ResponseEntity<Object> delete(@PathVariable(value = "id") long id) {
		Optional<Anime> anime = animeRepository.findById(id);
		if (anime.isPresent()) {
			animeRepository.delete(anime.get());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
