package eu.przysucha.placesnearby.controller;

import eu.przysucha.placesnearby.model.Data;
import eu.przysucha.placesnearby.model.Place;
import eu.przysucha.placesnearby.model.PlaceDTO;
import eu.przysucha.placesnearby.service.PlaceService;
import org.apache.http.client.utils.URIBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    PlaceService placeService;

    @Autowired
    public SpotController(PlaceService placeService) {
        this.placeService = placeService;
    }

    private List<PlaceDTO> placeList;
    @Value("${places.nearby.api-key}")
    private String apiKey;

    private String lat;
    private String lon;
    private String type;


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
        this.placeList = placeService.getPlaceListFromApi();
        return placeList;
    }

}
