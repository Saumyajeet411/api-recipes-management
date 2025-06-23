package com.learn.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.app.model.Recipe;
import com.learn.app.model.RecipiesDummy;

import java.util.List;

public class RecipeUtil {

    public static List<Recipe> parseRecipes(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        RecipiesDummy recipes = mapper.readValue(json, new TypeReference<RecipiesDummy>() {});
        return recipes.getRecipes();
    }
}
