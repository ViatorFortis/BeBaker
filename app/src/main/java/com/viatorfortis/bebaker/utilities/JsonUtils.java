package com.viatorfortis.bebaker.utilities;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.viatorfortis.bebaker.model.Recipe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class JsonUtils {
    public static List<Recipe> parseRecipeListJson(String jsonArray)
            throws JsonSyntaxException {
        Type listType = new TypeToken<ArrayList<Recipe>>(){}.getType();
        List<Recipe> recipeList = new Gson().fromJson(jsonArray, listType);

        return recipeList;
    }
}
