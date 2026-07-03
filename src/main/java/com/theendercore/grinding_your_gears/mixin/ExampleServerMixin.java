package com.theendercore.grinding_your_gears.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MinecraftServer.class)
public class ExampleServerMixin {

//    @Inject(method = "<init>", at = @At("TAIL"))
//    private static void run(Thread thread, LevelStorageSource.LevelStorageAccess levelStorageAccess, PackRepository packRepository, WorldStem worldStem, Proxy proxy, DataFixer dataFixer, Services services, ChunkProgressListenerFactory chunkProgressListenerFactory, CallbackInfo ci) {
//        log.info("Hello from server Mixin");
//    }

}
