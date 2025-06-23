package com.learn.app.repository;

import com.learn.app.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    List<Recipe> findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCase(String name, String cuisine);
}
