package com.learn.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Recipies model representing a recipe with various attributes")
public class Recipe {

    @Id
    @Schema(description = "Unique identifier for the recipe", example = "1")
    private Integer id;
    @Schema(description = "Name of the recipe", example = "Spaghetti Carbonara")
    private String name;
    @ElementCollection
    @Schema(description = "List of ingredients required for the recipe")
    private List<String> ingredients;
    @ElementCollection
    @Schema(description = "List of instructions to prepare the recipe")
    private List<String> instructions;
    @Schema(description = "Preparation time in minutes", example = "15")
    private Integer prepTimeMinutes;
    @Schema(description = "Cooking time in minutes", example = "30")
    private Integer cookTimeMinutes;
    @Schema(description = "Total time in minutes to prepare the recipe", example = "45")
    private Integer servings;
    @Schema(description = "Difficulty level of the recipe", example = "Easy")
    private String difficulty;
    @Schema(description = "Cuisine type of the recipe", example = "Italian")
    private String cuisine;
    @Schema(description = "Calories per serving of the recipe", example = "500")
    private Integer caloriesPerServing;
    @ElementCollection
    @Schema(description = "List of tags associated with the recipe")
    private List<String> tags;
    @Schema(description = "User ID of the person who created the recipe", example = "user123")
    private String userId;
    @Schema(description = "Image URL of the recipe", example = "http://example.com/image.jpg")
    private String image;
    @Schema(description = "Rating of the recipe", example = "4.5")
    private Double rating;
    @Schema(description = "Number of reviews for the recipe", example = "100")
    private Integer reviewCount;
    @ElementCollection
    @Schema(description = "List of meal types for the recipe, e.g., breakfast, lunch, dinner")
    private List<String> mealType;
}
