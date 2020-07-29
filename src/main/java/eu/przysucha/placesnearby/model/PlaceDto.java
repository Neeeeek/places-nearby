package eu.przysucha.placesnearby.model;


import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="places")
public class PlaceDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String formattedAddress;
    private Double rating;
    private Double lat;
    private Double lon;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceDto placeDto = (PlaceDto) o;
        return Objects.equals(id, placeDto.id) &&
                name.equals(placeDto.name) &&
                formattedAddress.equals(placeDto.formattedAddress) &&
                Objects.equals(rating, placeDto.rating) &&
                lat.equals(placeDto.lat) &&
                lon.equals(placeDto.lon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, formattedAddress, rating, lat, lon);
    }

    @ManyToOne
    private Category category;

    public PlaceDto() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Autowired
    public PlaceDto(String name, String formattedAddress, Double rating, Double lat, Double lon) {
        this.name = name;
        this.formattedAddress = formattedAddress;
        this.rating = rating;
        this.lat = lat;
        this.lon = lon;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }


    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }


    @Override
    public String toString() {
        return "PlaceDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", formattedAddress='" + formattedAddress + '\'' +
                ", rating=" + rating +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
