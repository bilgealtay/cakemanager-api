package com.ravensoftware.cakemanager.repository;

import com.ravensoftware.cakemanager.model.entity.Cake;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bilga
 */
@Repository
public interface CakeRepository extends JpaRepository<Cake, Long> {

    @Query("SELECT c FROM Cake c WHERE LOWER(TRIM(c.title))=LOWER(TRIM(:title))")
    Cake findByTitle(String title);

    @Query("SELECT c FROM Cake c WHERE LOWER(TRIM(c.title)) LIKE LOWER(CONCAT('%',:search,'%')) OR LOWER(TRIM(c.description)) LIKE LOWER(CONCAT('%',:search,'%'))")
    List<Cake> findByFilter(String search);
}
