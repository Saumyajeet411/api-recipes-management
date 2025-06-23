package com.learn.app.controller;

import com.learn.app.model.Recipe;
import com.learn.app.service.RecipeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RecipeControllerTest {

    private MockMvc mockMvc;
    private AutoCloseable closeable;
    @Mock private RecipeService recipeService;
    @InjectMocks private RecipeController recipeController;
    private Recipe recipe;

    @BeforeEach
    void setUp(){
        closeable = MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(recipeController).dispatchOptions(true).build();
    }

    @AfterEach
    void cleanUp() throws Exception {
        closeable.close();
    }

    @Test
    void testImportRecipes() throws Exception {
        // Implement the test logic for importing recipes
        Mockito.doNothing().when(recipeService).importRecipes();
        mockMvc.perform(post("/recipes/import"))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void testSearchRecipes() throws Exception {
        // Implement the test logic for searching recipes
        when(recipeService.searchRecipes(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(new ArrayList<>());
        String name = "Pasta";
        mockMvc.perform(get("/recipes")
                .param("query", name))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testGetRecipesById() throws Exception {
        // Implement the test logic for getting a recipe by ID
        Integer recipeId = 1;
        recipe = new Recipe();
        recipe.setId(recipeId);
        when(recipeService.getRecipesById(recipeId)).thenReturn(recipe);

        mockMvc.perform(get("/recipes/{recipeId}", recipeId))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
