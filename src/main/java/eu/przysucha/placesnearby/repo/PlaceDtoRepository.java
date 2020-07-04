package eu.przysucha.placesnearby.repo;

import eu.przysucha.placesnearby.model.PlaceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceDtoRepository extends JpaRepository <PlaceDTO, Long>{
    Optional<PlaceDTO> findPlaceDTOByName(String name);
}
