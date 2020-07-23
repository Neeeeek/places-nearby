package eu.przysucha.placesnearby.repo;

import eu.przysucha.placesnearby.model.PlaceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceDtoRepository extends JpaRepository <PlaceDto, Long>{
    Optional<PlaceDto> findPlaceDTOByName(String name);

}
