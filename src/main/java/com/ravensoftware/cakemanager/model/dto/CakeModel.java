package com.ravensoftware.cakemanager.model.dto;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by bilga
 */
public class CakeModel implements Serializable {

    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String image;

    public CakeModel() {
        // empty
    }

    public CakeModel(String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public CakeModel(Long id, String title, String description, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CakeModel cakeModel = (CakeModel) o;
        return Objects.equals(id, cakeModel.id) &&
                Objects.equals(title, cakeModel.title) &&
                Objects.equals(description, cakeModel.description) &&
                Objects.equals(image, cakeModel.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, image);
    }
}
