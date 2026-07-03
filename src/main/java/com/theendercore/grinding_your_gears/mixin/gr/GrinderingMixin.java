package com.theendercore.grinding_your_gears.mixin.gr;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.amymialee.grindering.Grindering;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Grindering.class)
public class GrinderingMixin {

    @WrapOperation(
            method = "lambda$onInitialize$1",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z",
                    ordinal = 2
            )
    )
    private static boolean preventNetherWater(ServerLevel level, BlockPos pos, BlockState state, Operation<Boolean> original) {
        return (!level.environmentAttributes().getValue(EnvironmentAttributes.WATER_EVAPORATES, pos)) ? original.call(level, pos, state) : false;
    }
}
