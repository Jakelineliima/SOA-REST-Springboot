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

import ws.model.Produto;
import ws.repository.ProdutoRepository;

@RestController
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@RequestMapping(value = "/produto", method = RequestMethod.GET, 
			produces = "application/json")
	public List<Produto> Get() {
		return produtoRepository.findAll();
	}
	
	@RequestMapping(value = "/produto", method = RequestMethod.POST,
		      consumes = "application/json", produces = "application/json")
		  public Produto post(@Valid @RequestBody Produto produto) {
		    return produtoRepository.save(produto);
		  }
	
	@RequestMapping(value = "produto/{id}", method = RequestMethod.GET,
			produces = "application/json")
	public ResponseEntity<Produto> getPorCodigo(@PathVariable(value = "id") long id){
		Optional<Produto> produto = produtoRepository.findById(id);
		if (produto.isPresent()) {
			return new ResponseEntity<Produto>(produto.get(),HttpStatus.OK );
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/produto{id}", method = RequestMethod.PUT,
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<Produto> put(@PathVariable(value = "id") long id,
			@Valid @RequestBody Produto novoProduto) {
		Optional<Produto> antigoProduto = produtoRepository.findById(id);
		if (antigoProduto.isPresent()) {
			Produto produto = antigoProduto.get();
			produto.setNome(novoProduto.getNome());
			produto.setValor(novoProduto.getValor());
			produtoRepository.save(produto);
			return new ResponseEntity<Produto>(produto, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
