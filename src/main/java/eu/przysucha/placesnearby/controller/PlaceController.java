package eu.przysucha.placesnearby.controller;

import eu.przysucha.placesnearby.model.Category;
import eu.przysucha.placesnearby.model.PlaceDto;
import eu.przysucha.placesnearby.model.payload.FindRequest;
import eu.przysucha.placesnearby.service.CategoryService;
import eu.przysucha.placesnearby.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/places")
public class PlaceController {

    PlaceService placeService;
    CategoryService categoryService;

    @Autowired
    public PlaceController(PlaceService placeService, CategoryService categoryService) {
        this.placeService = placeService;
        this.categoryService = categoryService;
    }


    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);

    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<PlaceDto> getPlacesList(@RequestBody FindRequest findRequest, @RequestHeader Map<String, String> headers) {

        return placeService.getPlaceListFromApi(findRequest.getLat(), findRequest.getLon(), findRequest.getType());

    }


}
