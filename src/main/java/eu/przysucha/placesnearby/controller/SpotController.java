package eu.przysucha.placesnearby.controller;

import eu.przysucha.placesnearby.model.Data;
import eu.przysucha.placesnearby.model.Place;
import eu.przysucha.placesnearby.model.PlaceDTO;
import org.apache.http.client.utils.URIBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/places")
public class SpotController {

    private List<PlaceDTO> placeList;
    @Value("${places.nearby.api-key}")
    private String apiKey;

    private String lat;
    private String lon;
    private String type;
    private Logger logger = LoggerFactory.getLogger(SpotController.class);

    private static final String GOOGLE_MAPS_BASE_API_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?";


    public List<PlaceDTO> getPlaceListFromApi() {

        List<PlaceDTO> list = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        Data data = restTemplate.getForObject(getApiUrl(), Data.class);
        data.getPlaces().forEach(v -> list.add(modelMapper().map(v, PlaceDTO.class)));
        logger.info("Got list of " + type + "s");
        return list;

    }

    public ModelMapper modelMapper () {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Place, PlaceDTO>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setFormattedAddress(source.getFormattedAddress());
                map().setRating(source.getRating());
                map().setOpenNow(source.getOpeningHours().getOpenNow());
                map().setLat(source.getGeometry().getLocation().getLat());
                map().setLon(source.getGeometry().getLocation().getLng());
            }
        });

        return modelMapper;
    }


    private URI getApiUrl() {

        URI uri = null;
        try {
           URIBuilder uriBuilder = new URIBuilder(GOOGLE_MAPS_BASE_API_URL);

        uriBuilder.addParameter("query", type);
        uriBuilder.addParameter("location", lat +"," + lon);
        uriBuilder.addParameter("key", apiKey);
        uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;


    }
    @GetMapping
    public String get() {
        return "map";
    }

    @ResponseBody
    @PostMapping
    public List<PlaceDTO> getby(@RequestParam(value = "lat") String lat, @RequestParam(value = "lon") String lon, @RequestParam(value = "type") String type) {
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.placeList = getPlaceListFromApi();
        return placeList;
    }

}
