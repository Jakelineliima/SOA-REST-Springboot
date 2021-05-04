package ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ws.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
