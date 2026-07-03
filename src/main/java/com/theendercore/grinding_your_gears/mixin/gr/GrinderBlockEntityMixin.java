package com.theendercore.grinding_your_gears.mixin.gr;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.amymialee.grindering.block.GrinderBlockEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.theendercore.grinding_your_gears.GrindingYourGears.canCrush;
import static com.theendercore.grinding_your_gears.util.GrinderUtilsKt.getCrushingRecipe;

@Mixin(GrinderBlockEntity.class)
public class GrinderBlockEntityMixin {

    @ModifyExpressionValue(method = "serverTick", at = @At(value = "INVOKE", target = "Ljava/util/Optional;orElse(Ljava/lang/Object;)Ljava/lang/Object;"))
    private static <T> T addFakeRecipes(T original, @Local(name = "input") ItemStack input, @Local ServerLevel level) {
        if (original == null && canCrush(input)) {
            //noinspection unchecked
            return (T) getCrushingRecipe(input, level);
        }
        return original;
    }
}
