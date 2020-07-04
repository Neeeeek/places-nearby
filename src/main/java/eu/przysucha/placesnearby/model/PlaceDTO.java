package eu.przysucha.placesnearby.model;


import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name="places")
public class PlaceDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String formattedAddress;
    private Boolean openNow;
    private Double rating;
    private Double lat;
    private Double lon;

    public PlaceDTO() {
    }

    @Autowired
    public PlaceDTO(String name, String formattedAddress, Boolean openNow, Double rating, Double lat, Double lon) {
        this.name = name;
        this.formattedAddress = formattedAddress;
        this.openNow = openNow;
        this.rating = rating;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "PlaceDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", formattedAddress='" + formattedAddress + '\'' +
                ", openNow=" + openNow +
                ", rating=" + rating +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
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
}
