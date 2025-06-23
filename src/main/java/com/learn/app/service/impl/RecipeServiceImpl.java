package com.learn.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learn.app.exception.RecipeNotFoundException;
import com.learn.app.exception.RecipeServiceException;
import com.learn.app.model.Recipe;
import com.learn.app.repository.RecipeRepository;
import com.learn.app.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.learn.app.util.RecipeUtil.parseRecipes;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {
    private static final Logger logger = LoggerFactory.getLogger(RecipeServiceImpl.class);


    @Value("${app.recipes.base-url}")
    private String recipeApiUrl;

    @Autowired private RestTemplate restTemplate;
    @Autowired private RecipeRepository recipeRepository;

    @Override
    public void importRecipes() {
        logger.info("Starting import of recipes from external source: {}", recipeApiUrl);
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(recipeApiUrl, String.class);
            String recipeData = null;
            if (response.getStatusCode().is2xxSuccessful()) {
                recipeData = response.getBody();
            } else {
                throw new RecipeServiceException("Failed to import recipes: " + response.getStatusCode());
            }
                // recipeData is in JSON format and can be converted to a List<Recipe>
                List<Recipe> recipies = parseRecipes(recipeData);

                recipeRepository.saveAll(recipies);
                logger.info("Successfully imported {} recipes", recipies.size());

        } catch (JsonProcessingException exception){
            logger.error("Error parsing recipes data: {}", exception.getMessage());
            throw new RecipeServiceException("Error parsing recipes data", exception);
        } catch (RecipeServiceException exception) {
            logger.error("Error importing recipes: {}", exception.getMessage());
            throw exception;
        } catch (Exception exception) {
            logger.error("Error importing recipes: {}", exception.getMessage());
            throw new RecipeServiceException("Error importing recipes", exception);
        }
    }

    @Override
    public List<Recipe> searchRecipes(String name, String cuisine) {
        logger.info("Searching for recipies with name: {} and cuisine: {}", name, cuisine);
        try {
            // Search for recipies by name or cuisine
            List<Recipe> recipe = new ArrayList<>();
            if( name == null && cuisine == null) {
                logger.warn("Both name and cuisine are null, returning All");
                recipe = recipeRepository.findAll();
            }
            else {
                recipe = recipeRepository.findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCase(name, cuisine);
            }

            logger.info("Found {} recipes for name: {} and cuisine: {}", recipe.size(), name, cuisine);
            return recipe;
        } catch (Exception exception) {
            logger.error("Error searching recipes: {}", exception.getMessage());
            throw new RecipeServiceException("Error searching recipes", exception);
        }

    }

    @Override
    public Recipe getRecipesById(Integer id) {
    logger.info("Retrieving recipe by ID: {}", id);
        try {
            Recipe recipe = recipeRepository.findById(id)
                    .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with ID: " + id));
            logger.info("Recipe found: {}", recipe);
            return recipe;
        } catch (RecipeNotFoundException exception) {
            logger.error("Recipe not found: {}", exception.getMessage());
            throw new RecipeNotFoundException(exception.getMessage(), exception);
        } catch (Exception exception) {
            logger.error("Error retrieving recipe by ID: {}", exception.getMessage());
            throw new RecipeServiceException("Error retrieving recipe by ID", exception);
        }
    }
}
