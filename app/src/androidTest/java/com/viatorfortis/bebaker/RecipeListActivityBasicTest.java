package com.viatorfortis.bebaker;


import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.viatorfortis.bebaker.rv.RecipeDetailAdapter;
import com.viatorfortis.bebaker.ui.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityBasicTest {

    @Rule public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<> (MainActivity.class);

    @Test
    public void ClickRecipeListItem_opensActivityWithIngredientsItem() {
        onView(withId(R.id.rv_recipe_list) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click() ) );

        onView(withId(R.id.rv_step_list) )
                .perform(RecyclerViewActions.scrollToHolder(withIngredientListViewHolder("Ingredients") ) );
    }

    public static Matcher<RecyclerView.ViewHolder> withIngredientListViewHolder(final String caption) {
        return new BoundedMatcher<RecyclerView.ViewHolder, RecipeDetailAdapter.IngredientListViewHolder>(RecipeDetailAdapter.IngredientListViewHolder.class) {
            @Override
            protected boolean matchesSafely(RecipeDetailAdapter.IngredientListViewHolder item) {
                TextView ingredientListCaptionTextView = item.itemView.findViewById(R.id.tv_ingredient_list_caption);

                if (ingredientListCaptionTextView == null) {
                    return false;
                }

                return ingredientListCaptionTextView.getText().toString().contains(caption);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Checking the matcher on the caption of ingredients item view");
            }
        };
    }
}
