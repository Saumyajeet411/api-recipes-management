package com.learn.app.exception.handler;

import com.learn.app.controller.RecipeController;
import com.learn.app.exception.RecipeNotFoundException;
import com.learn.app.exception.RecipeServiceException;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RecipeAppExceptionHandlerTest {
    private MockMvc mockMvc;
    private AutoCloseable closeable;
    @Mock
    private RecipeService recipeService;
    @InjectMocks
    private RecipeController recipeController;

    @BeforeEach
    void setUp(){
        closeable = MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new RecipeApplicationExceptionHandler())
                .build();
    }

    @AfterEach
    void cleanUp() throws Exception {
        closeable.close();
    }

    @Test
    void testSearchRecipes_serviceException() throws Exception {
        when(recipeService.searchRecipes(Mockito.anyString(), Mockito.anyString()))
                .thenThrow(new RecipeServiceException("Service error occurred"));
        String name = "Pasta";
        mockMvc.perform(get("/recipes")
                        .param("query", name))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testSearchRecipes_genericException() throws Exception {
        when(recipeService.searchRecipes(Mockito.anyString(), Mockito.anyString()))
                .thenThrow(new RuntimeException("Service error occurred"));
        String name = "Pasta";
        mockMvc.perform(get("/recipes")
                        .param("query", name))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetRecipesById_notFoundException() throws Exception {
        Integer recipeId = 1;
        when(recipeService.getRecipesById(recipeId))
                .thenThrow(new RecipeNotFoundException("Recipe not found with id: " + recipeId));

        mockMvc.perform(get("/recipes/{recipeId}", recipeId))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}
