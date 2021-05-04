package ws.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
}
