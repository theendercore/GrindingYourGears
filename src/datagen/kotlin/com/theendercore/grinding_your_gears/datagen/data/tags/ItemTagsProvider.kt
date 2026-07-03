package com.theendercore.grinding_your_gears.datagen.data.tags

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

class ItemTagsProvider(o: FabricDataOutput, p: CompletableFuture<HolderLookup.Provider>) :
    ItemTagProvider(o, p) {

    override fun addTags(lookup: HolderLookup.Provider) {

    }

}