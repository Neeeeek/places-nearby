package eu.przysucha.placesnearby.controller;

import eu.przysucha.placesnearby.model.PlaceDto;
import eu.przysucha.placesnearby.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    PlaceService placeService;
    @Autowired
    public UserController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/favourites")
    public ResponseEntity<?> addFavouritePlace(@RequestBody PlaceDto placeDto, @RequestHeader Map<String, String> headers) {

        placeService.addFavouritePlace(placeDto, headers);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @GetMapping("/favourites")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Set<PlaceDto>> getFavouritePlaceList(@RequestHeader Map<String, String> headers) {

        return new ResponseEntity<>(placeService.getFavouritePlaceList(headers), HttpStatus.OK);

    }

    @DeleteMapping("/favourites")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Set<PlaceDto>> deletePlaceFromFavouriteList(@RequestBody PlaceDto placeDto, @RequestHeader Map<String, String> headers) {

        placeService.deletePlaceFromFavouriteList(placeDto, headers);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
