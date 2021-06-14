package ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ws.model.Anime;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

}
