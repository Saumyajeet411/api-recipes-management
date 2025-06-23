package com.learn.app.service;

import com.learn.app.exception.RecipeNotFoundException;
import com.learn.app.exception.RecipeServiceException;
import com.learn.app.model.Recipe;

import java.util.List;

public interface RecipeService {
    void importRecipes() throws RecipeServiceException;
    List<Recipe> searchRecipes(String name, String cusine) throws RecipeServiceException;
    Recipe getRecipesById(Integer id) throws RecipeServiceException, RecipeNotFoundException;
}
