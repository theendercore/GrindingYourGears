package com.theendercore.grinding_your_gears.mixin.gr;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.amymialee.grindering.client.GrinderScreenHandler;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.theendercore.grinding_your_gears.GrindingYourGears.canCrush;

@Mixin(GrinderScreenHandler.class)
public class GrinderScreenHandlerMixin {

    @ModifyReturnValue(method = "isGrindable", at = @At("RETURN"))
    boolean allowEasyInsertion(boolean original, ItemStack itemStack) {
        return original || canCrush(itemStack);
    }
}
