package backend.newssseuk.domain.scrap.repository;

import backend.newssseuk.domain.scrap.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long>, ScrapRepositoryCustom {
}
