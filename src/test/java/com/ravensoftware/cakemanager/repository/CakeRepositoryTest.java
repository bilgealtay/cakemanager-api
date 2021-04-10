package com.ravensoftware.cakemanager.repository;

import com.ravensoftware.cakemanager.model.entity.Cake;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by bilga
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class CakeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    CakeRepository cakeRepository;

    @Test
    public void should_find_no_cakes_if_repository_is_empty() {
        Iterable<Cake> cakes = cakeRepository.findAll();
        assertThat(cakes).isEmpty();
    }

    @Test
    public void should_store_a_cake() {
        Cake cake = cakeRepository.save(new Cake("title", "desc", "image"));

        assertThat(cake).hasFieldOrPropertyWithValue("title", "title");
        assertThat(cake).hasFieldOrPropertyWithValue("description", "desc");
        assertThat(cake).hasFieldOrPropertyWithValue("image", "image");
    }

    @Test
    public void should_find_all_cakes() {
        Cake cake1 = new Cake("Title1", "Description1", "image1");
        entityManager.persist(cake1);

        Cake cake2 = new Cake("Title2", "Description2", "image2");
        entityManager.persist(cake2);

        Cake cake3 = new Cake("Title3", "Description3", "image3");
        entityManager.persist(cake3);

        Iterable<Cake> cakes = cakeRepository.findAll();

        assertThat(cakes).hasSize(3).contains(cake1, cake2, cake3);
    }

    @Test
    public void should_find_cake_by_id() {
        Cake cake1 = new Cake("Title1", "Description1", "image1");
        entityManager.persist(cake1);

        Cake cake2 = new Cake("Title2", "Description2", "image2");
        entityManager.persist(cake2);

        Cake foundTutorial = cakeRepository.findById(cake2.getId()).get();

        assertThat(foundTutorial).isEqualTo(cake2);
    }

    @Test
    public void should_find_cakes_by_title_containing_string() {
        Cake cake1 = new Cake("Title1", "Description1", "image1");
        entityManager.persist(cake1);

        Cake cake2 = new Cake("Title2", "Description2", "image2");
        entityManager.persist(cake2);

        Cake cake3 = new Cake("Cake3", "Description3", "image3");
        entityManager.persist(cake3);

        Iterable<Cake> cakes = cakeRepository.findByFilter("title");

        assertThat(cakes).hasSize(2).contains(cake1, cake2);
    }

    @Test
    public void should_find_cake_by_title() {
        String searchTitle = "title1";

        Cake cake1 = new Cake("Title1", "Description1", "image1");
        entityManager.persist(cake1);
        Cake cake2 = new Cake("Title2", "Description2", "image2");
        entityManager.persist(cake2);

        Cake cake = cakeRepository.findByTitle(searchTitle);

        assertThat(searchTitle.equalsIgnoreCase(cake.getTitle()));
    }

}