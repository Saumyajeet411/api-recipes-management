package com.learn.app.controller;

import com.learn.app.model.Recipe;
import com.learn.app.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@CrossOrigin(origins = "*")
@Tag(name = "Recipes API", description = "Operations related to recipes")
public class RecipeController {
    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);
    @Autowired private RecipeService recipeService;

    @Operation(summary = "Import Recipes", description = "Endpoint to import recipes from an external source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recipes imported successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error while importing recipes")
    })
    @PostMapping(value = "/import", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> importRecipes() {
        //import recipes
        logger.info("Importing recipes from external source");
        recipeService.importRecipes();
        logger.info("Recipes imported successfully");
        return new ResponseEntity<>("Recipes imported successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Search Recipes", description = "Endpoint to search for recipes based on a query string")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipes found"),
            @ApiResponse(responseCode = "404", description = "Recipes not found for the given query",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Recipe>> searchRecipes(
            @Parameter(description = "Query string to search for recipes",
                    example = "Pasta", required = false)
            @RequestParam(value = "query", required = false) String query) {
        //search recipes
        logger.info("Searching for recipes with query: {}", query);
        List<Recipe> recipes = recipeService.searchRecipes(query, query);
        logger.info("Found {} recipes for query: {}", recipes.size(), query);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @Operation(summary = "Get Recipe by ID", description = "Endpoint to retrieve a recipe by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe found"),
            @ApiResponse(responseCode = "404", description = "Recipe not found for the given Id",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @GetMapping(value = "/{recipeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Recipe> getRecipesById(@PathVariable("recipeId") Integer recipeId) {
        //Get recipes By Id
        logger.info("Retrieving recipe with ID: {}", recipeId);
        Recipe recipe = recipeService.getRecipesById(recipeId);
        logger.info("Recipe found: {}", recipe);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }
}
