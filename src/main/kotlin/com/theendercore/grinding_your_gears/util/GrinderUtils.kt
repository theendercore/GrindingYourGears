package com.theendercore.grinding_your_gears.util

import com.theendercore.grinding_your_gears.GrindingYourGears.id
import com.theendercore.grinding_your_gears.mixin.ShapedRecipeAccessor
import dev.amymialee.grindering.recipes.GrindingRecipe
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.core.NonNullList
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeHolder
import net.minecraft.world.item.crafting.RecipeType
import java.util.*
import kotlin.jvm.optionals.getOrNull

val CUSTOM_RECIPE_CACHE = mutableMapOf<Item, Optional<RecipeHolder<GrindingRecipe>>>()

fun getCrushingRecipe(input: ItemStack, level: ServerLevel): RecipeHolder<GrindingRecipe>? {
    var recipe = CUSTOM_RECIPE_CACHE[input.item]

    if (recipe == null) {
        recipe = recipeFromItem(input, level)
        CUSTOM_RECIPE_CACHE[input.item] = recipe
    }

    return recipe.getOrNull()
}

fun recipeFromItem(input: ItemStack, level: ServerLevel): Optional<RecipeHolder<GrindingRecipe>> {

    for (holder in level.recipeAccess().getAllOfType(RecipeType.CRAFTING)) {
        val shaped = holder.value
        if (shaped is ShapedRecipeAccessor && input.`is`(shaped.gyg_result().item)) {

            if (doNoveltyCheck(holder.id)) continue

            val opt = Optional.ofNullable(crateGrindingRecipe(shaped)).map {
                RecipeHolder(
                    Registries.RECIPE.key(
                        id(
                            input.item.builtInRegistryHolder().key().identifier().toString().replace(":", "/")
                        )
                    ),
                    it
                )
            }
            if (opt.isPresent) {
                return opt
            }
        }
    }

    return Optional.empty()
}

fun doNoveltyCheck(id: ResourceKey<Recipe<*>>): Boolean {
    return FabricLoader.getInstance().isModLoaded("hydrothermia") && id.identifier().path.contains("diamond")
}

fun crateGrindingRecipe(recipe: ShapedRecipeAccessor): GrindingRecipe? {
    val input = Ingredient.of(recipe.gyg_result().item)

    val ing = mutableMapOf<Item, Int>()
    val patternIng = recipe.gyg_pattern().ingredients().mapNotNull { it.getOrNull()?.getItem() }
    if (patternIng.isEmpty()) {
        return null
    }

    for (item in patternIng) {
        ing.compute(item) { _, count -> if (count == null) 1 else count + 1 }
    }

    if (ing.size > 2) return null


    val ing2 = ing.map { it.key.defaultInstance.copyWithCount(it.value) }

    return GrindingRecipe(
        NonNullList.of(input, input),
        recipe.gyg_result().count,
        ing2[0], ing2.getOrElse(1) { ItemStack.EMPTY },
        28, false, ""
    )
}


fun Ingredient.getItem(): Item? = items().findFirst().getOrNull()?.value()