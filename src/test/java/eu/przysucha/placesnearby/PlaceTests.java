package eu.przysucha.placesnearby;

import eu.przysucha.placesnearby.controller.PlaceController;
import eu.przysucha.placesnearby.model.PlaceDto;
import eu.przysucha.placesnearby.service.PlaceService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaceTests {


    @Test
    public void should_get_places() {

        PlaceService placeService = mock(PlaceService.class);
        when(placeService.getPlaceListFromApi("any", "any", "any")).thenReturn(prepareMockData());
        List<PlaceDto> placeDtoList = placeService.getPlaceListFromApi("any", "any", "any");
        Assert.assertThat(placeDtoList, Matchers.hasSize(3));

    }

    private List<PlaceDto> prepareMockData() {

        List<PlaceDto> placeDtoList = new ArrayList<>();
        placeDtoList.add(new PlaceDto("Pizzeria Manko", "Wrocław, Wita Stwosza 13", 4.5, 53.11223123, 17.4354523));
        placeDtoList.add(new PlaceDto("Sushi Carbo", "Warszawa, Mira Kosza 13", 4.8, 57.234223, 18.34534634));
        placeDtoList.add(new PlaceDto("MangoMama", "Kraków, Krakowska 53", 4.0, 58.2454223, 19.124634));

        return placeDtoList;
    }
}
