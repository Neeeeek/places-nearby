package eu.przysucha.placesnearby.service;

import eu.przysucha.placesnearby.aspect.PlaceAddToFavListAspectAnnotation;
import eu.przysucha.placesnearby.aspect.PlaceListGetFromApiAspectAnnotation;
import eu.przysucha.placesnearby.model.*;
import eu.przysucha.placesnearby.repo.CategoryRepository;
import eu.przysucha.placesnearby.repo.PlaceDtoRepository;
import eu.przysucha.placesnearby.repo.UserRepository;
import eu.przysucha.placesnearby.security.JwtUtilities;
import org.apache.http.client.utils.URIBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
public class PlaceService {

    @Value("${places.nearby.api-key}")
    private String apiKey;

    private String lat;
    private String lon;
    private String type;

    private static final String GOOGLE_MAPS_BASE_API_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?";

    private final PlaceDtoRepository placeDtoRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final JwtUtilities jwtUtilities;

    @Autowired
    public PlaceService(PlaceDtoRepository placeDtoRepository, CategoryRepository categoryRepository, UserRepository userRepository, JwtUtilities jwtUtilities) {
        this.placeDtoRepository = placeDtoRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.jwtUtilities = jwtUtilities;
    }


    @PlaceAddToFavListAspectAnnotation
    public void addFavouritePlace(PlaceDto placeDto, Map<String, String> headers) {

        String username = jwtUtilities.getUserNameFromJwtToken(headers.get("authorization").substring(7));
        User user = userRepository.findByUsername(username).get();
        user.getFavouritesPlaces().add(placeDto);
        if (!this.ifExistsPlaceDto(placeDto.getName())) {
            placeDtoRepository.save(placeDto);
            userRepository.save(user);
        }

    }


    @PlaceListGetFromApiAspectAnnotation
    public List<PlaceDto> getPlaceListFromApi(String lat, String lon, String type) {

        this.lat = lat;
        this.lon = lon;
        this.type = type;

        Category category = categoryRepository.findByName(type);
        List<PlaceDto> list = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        Data data = restTemplate.getForObject(getApiUrl(), Data.class);
        data.getPlaces().forEach(v -> {
            list.add(modelMapper(category).map(v, PlaceDto.class));
        });
        return list;

    }


    public PlaceDto loadPlaceByName(String name) {
        Optional<PlaceDto> placeDTO = placeDtoRepository.findPlaceDTOByName(name);
        if (placeDTO.isPresent()) {
            return placeDTO.get();
        }
        return null;
    }

    public boolean ifExistsPlaceDto(String name) {
        Optional<PlaceDto> placeDTO = placeDtoRepository.findPlaceDTOByName(name);
        return placeDTO.isPresent();
    }

    public void savePlace(PlaceDto placeDTO) {
        if (!ifExistsPlaceDto(placeDTO.getName())) placeDtoRepository.save(placeDTO);

    }

    public ModelMapper modelMapper(Category category) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addMappings(new PropertyMap<Place, PlaceDto>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                map().setName(source.getName());
                map().setFormattedAddress(source.getFormattedAddress());
                map().setRating(source.getRating());
                map().setLat(source.getGeometry().getLocation().getLat());
                map().setLon(source.getGeometry().getLocation().getLng());
                map().setCategory(category);
            }
        });

        return modelMapper;
    }


    private URI getApiUrl() {

        URI uri = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(GOOGLE_MAPS_BASE_API_URL);
            uriBuilder.addParameter("query", type);
            uriBuilder.addParameter("location", lat + "," + lon);
            uriBuilder.addParameter("key", apiKey);
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;


    }

    public Set<PlaceDto> getFavouritePlaceList(Map<String, String> headers) {

        String username = jwtUtilities.getUserNameFromJwtToken(headers.get("authorization").substring(7));
        User user = userRepository.findByUsername(username).get();
        return user.getFavouritesPlaces();

    }

    public void deletePlaceFromFavouriteList(PlaceDto placeDto, Map<String, String> headers) {

        String username = jwtUtilities.getUserNameFromJwtToken(headers.get("authorization").substring(7));
        User user = userRepository.findByUsername(username).get();
        Set<PlaceDto> lista = user.getFavouritesPlaces();
        lista.remove(placeDto);
        userRepository.save(user);
        placeDtoRepository.delete(placeDto);


    }


}
