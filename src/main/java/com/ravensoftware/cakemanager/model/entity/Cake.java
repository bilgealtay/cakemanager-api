package com.ravensoftware.cakemanager.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by bilga
 */
@Entity
@Table(name = "CAKES", uniqueConstraints={
        @UniqueConstraint(name = "TITLE_UNIQUE", columnNames = {"title"})
})
public class Cake implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAKES_ID_SEQ")
    @SequenceGenerator(name = "CAKES_ID_SEQ", sequenceName = "CAKES_ID_SEQ", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "TITLE", nullable = false, length = 100)
    private String title;

    @NotNull
    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String description;

    @NotNull
    @Column(name = "IMAGE", nullable = false, length = 300)
    private String image;

    public Cake() {
    }

    public Cake(String title, String description, String image) {
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
        Cake that = (Cake) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}

