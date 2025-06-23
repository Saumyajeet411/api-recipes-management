package com.learn.app.service;

import com.learn.app.exception.RecipeNotFoundException;
import com.learn.app.exception.RecipeServiceException;
import com.learn.app.model.Recipe;
import com.learn.app.repository.RecipeRepository;
import com.learn.app.service.impl.RecipeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {
    private AutoCloseable closeable;
    @Mock private RecipeRepository recipeRepository;
    @Mock private RestTemplate restTemplate;
    @InjectMocks private RecipeServiceImpl recipieServiceImpl;

    @BeforeEach
    void setUp(){
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void cleanUp() throws Exception {
        closeable.close();
    }

    @Test
    void testImportRecipes() {
        // Implement the test logic for importing recipes
        // This method should call recipeServiceImpl.importRecipes() and verify its behavior
        ReflectionTestUtils.setField(recipieServiceImpl, "recipeApiUrl", "http://example.com/api/recipes");
        String recipeData = "{\"recipes\": [{\"name\":\"Pasta\",\"cuisine\":\"Italian\"}]}"; // Example JSON data
        ResponseEntity<String> response = new ResponseEntity<>(recipeData, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(response);
        when(recipeRepository.saveAll(Mockito.anyList())).thenReturn(new ArrayList<>());
        recipieServiceImpl.importRecipes();
        verify(recipeRepository, atLeastOnce()).saveAll(anyList());
    }

    @Test
    void testImportRecipes_callException() {
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenThrow(new RuntimeException());
        when(recipeRepository.saveAll(Mockito.anyList())).thenReturn(new ArrayList<>());
        assertThrows(RecipeServiceException.class, () -> {
            recipieServiceImpl.importRecipes();
        });
    }

    @Test
    void testImportRecipes_parseException() {
        ReflectionTestUtils.setField(recipieServiceImpl, "recipeApiUrl", "http://example.com/api/recipes");
        // Incorrect JSON format to trigger parsing exception
        String recipeData = "{\"recipes\": [{\"namey\":\"Pasta\",\"cuisine\":\"Italian\"}]}"; // Example JSON data
        ResponseEntity<String> response = new ResponseEntity<>(recipeData, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(response);
        when(recipeRepository.saveAll(Mockito.anyList())).thenReturn(new ArrayList<>());
        assertThrows(RecipeServiceException.class, () -> {
            recipieServiceImpl.importRecipes();
        });
    }

    @Test
    void testImportRecipes_saveException() {
        ReflectionTestUtils.setField(recipieServiceImpl, "recipeApiUrl", "http://example.com/api/recipes");
        // Incorrect JSON format to trigger parsing exception
        String recipeData = "{\"recipe\": [{\"name\":\"Pasta\",\"cuisine\":\"Italian\"}]}"; // Example JSON data
        ResponseEntity<String> response = new ResponseEntity<>(recipeData, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(response);
        when(recipeRepository.saveAll(Mockito.anyList())).thenThrow(new RuntimeException());
        assertThrows(RecipeServiceException.class, () -> {
            recipieServiceImpl.importRecipes();
        });
    }

    @Test
    void testSearchRecipes() {
        // Implement the test logic for searching recipes
        when(recipeRepository.findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCase(anyString(), anyString()))
                .thenReturn(new ArrayList<>());
        recipieServiceImpl.searchRecipes("Pasta", "Italian");
        verify(recipeRepository, atLeastOnce()).findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCase(anyString(), anyString());
    }

    @Test
    void testSearchRecipes_serviceException() {
        when(recipeRepository.findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCase(anyString(), anyString()))
                .thenThrow(new RuntimeException("Service error occurred"));
        assertThrows(RecipeServiceException.class, () -> {
            recipieServiceImpl.searchRecipes("Pasta", "Italian");
        });
    }

    @Test
    void testGetRecipesById() {
        // Implement the test logic for getting a recipe by ID
        Integer id = 1;
        when(recipeRepository.findById(id)).thenReturn(java.util.Optional.of(new Recipe()));
        recipieServiceImpl.getRecipesById(id);
        verify(recipeRepository, atLeastOnce()).findById(id);
    }

    @Test
    void testGetRecipesById_notFoundException() {
        Integer id = 1;
        when(recipeRepository.findById(id)).thenReturn(java.util.Optional.empty());
        assertThrows(RecipeNotFoundException.class, () -> {
            recipieServiceImpl.getRecipesById(id);
        });
    }

    @Test
    void testGetRecipesById_serviceException() {
        Integer id = 1;
        when(recipeRepository.findById(id)).thenThrow(new RuntimeException("Service error occurred"));
        assertThrows(RecipeServiceException.class, () -> {
            recipieServiceImpl.getRecipesById(id);
        });
    }
}
