package eu.przysucha.placesnearby.controller;

import eu.przysucha.placesnearby.model.Category;
import eu.przysucha.placesnearby.model.PlaceDto;
import eu.przysucha.placesnearby.payload.FindRequest;
import eu.przysucha.placesnearby.service.CategoryService;
import eu.przysucha.placesnearby.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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


//        System.out.println(headers.get("authorization"));
//        System.out.println(placeDto);
        //System.out.println(user);
        //Optional<Quiz>  quiz = quizService.getQuiz(id);
        System.out.println("Kategoria->");
        System.out.println(placeDto.getCategory());
        placeService.addFavouritePlace(placeDto, headers);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @GetMapping("/favourites")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Set<PlaceDto>> getFavouritePlaceList(@RequestHeader Map<String, String> headers) {

//        headers.forEach((key, value) -> {
//            System.out.println(String.format("Header '%s' = %s", key, value));
//        });


//        this.lat = lat;
//        this.lon = lon;
//        this.type = type;

        return new ResponseEntity<>(placeService.getFavouritePlaceList(headers), HttpStatus.OK);
       // return ;
        //return placeList;
    }

    @DeleteMapping("/favourites")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Set<PlaceDto>> deletePlaceFromFavouriteList(@RequestBody PlaceDto placeDto, @RequestHeader Map<String, String> headers) {

        headers.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });

        placeService.deletePlaceFromFavouriteList(placeDto, headers);


//        this.lat = lat;
//        this.lon = lon;
//        this.type = type;

      //  return new ResponseEntity<>(placeService.getFavouritePlaceList(headers), HttpStatus.OK);
        // return ;
        //return placeList;

        return null;
    }

}
