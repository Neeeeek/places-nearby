package eu.przysucha.placesnearby.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean visibility;

    @Transient
    @OneToMany(mappedBy = "category")
    private Set<PlaceDto> placeDTO = new HashSet<>();

    public Category(String name) {
        this.name = name;


    }

    public Category() {
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

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", visibility=" + visibility +
                ", placeDTO=" + placeDTO +
                '}';
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public Set<PlaceDto> getPlaceDTO() {
        return placeDTO;
    }

    public void setPlaceDTO(Set<PlaceDto> placeDTO) {
        this.placeDTO = placeDTO;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }
}
