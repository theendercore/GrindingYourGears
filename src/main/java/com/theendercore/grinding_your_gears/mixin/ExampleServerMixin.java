package com.theendercore.grinding_your_gears.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.SortedMap;
import java.util.TreeMap;

import static com.theendercore.grinding_your_gears.GrindingYourGears.*;


@Debug(export = true)
@Mixin(RecipeManager.class)
public class ExampleServerMixin {

    @Shadow
    @Final
    private HolderLookup.Provider registries;

    @Inject(
            method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Lnet/minecraft/world/item/crafting/RecipeMap;",
            at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;<init>(I)V")
    )
    void doDarkMagic(ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfoReturnable<RecipeMap> cir,
                     @Local SortedMap<Identifier, Recipe<?>> sortedMap) {

        var toBeAdded = new TreeMap<Identifier, Recipe<?>>();
        sortedMap.forEach((id, recipe) -> {
            if (recipe instanceof ShapedRecipe shapedRecipe) {
                var accessor = ((ShapedRecipeAccessor) shapedRecipe);
                if (canUnCraft(accessor.gyg_result())) {
                    var recipe2 = crateGrindingRecipe(id, accessor);
                    if (recipe2 != null) {
                        toBeAdded.put(id.withPrefix(MODID + "/"), recipe2);
                    }
                }
            }
        });

        sortedMap.putAll(toBeAdded);
    }

}
