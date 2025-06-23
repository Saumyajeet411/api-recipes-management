package com.learn.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipiesDummy {
    List<Recipe> recipes;
    private Integer total;
    private Integer skip;
    private Integer limit;
}
