package com.theendercore.grinding_your_gears.datagen.data.tags

import com.theendercore.grinding_your_gears.data.tags.GYGItemTags
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags
import net.minecraft.core.HolderLookup
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Items.*
import java.util.concurrent.CompletableFuture

class ItemTagsProvider(o: FabricDataOutput, p: CompletableFuture<HolderLookup.Provider>) :
    ItemTagProvider(o, p) {

    override fun addTags(lookup: HolderLookup.Provider) {

        valueLookupBuilder(GYGItemTags.CRUSHABLE)
            .forceAddTag(ConventionalItemTags.ENCHANTABLES)
            .forceAddTag(ConventionalItemTags.TOOLS)
            .forceAddTag(ConventionalItemTags.ARMORS)
            .forceAddTag(ItemTags.TRIMMABLE_ARMOR)

        valueLookupBuilder(GYGItemTags.NOT_CRUSHABLE)
            .forceAddTag(ConventionalItemTags.NAUTILUS_ARMORS)
            .forceAddTag(ItemTags.SKULLS)
            .add(
                CROSSBOW,
                TRIDENT,
                BRUSH,

                COMPASS,

                CARVED_PUMPKIN,

                COPPER_HORSE_ARMOR,
                GOLDEN_HORSE_ARMOR,
                IRON_HORSE_ARMOR,
                DIAMOND_HORSE_ARMOR,
                NETHERITE_HORSE_ARMOR,


                NETHERITE_SWORD,
                NETHERITE_SHOVEL,
                NETHERITE_PICKAXE,
                NETHERITE_AXE,
                NETHERITE_HOE,
                NETHERITE_HELMET,
                NETHERITE_CHESTPLATE,
                NETHERITE_LEGGINGS,
                NETHERITE_BOOTS,
                NETHERITE_SPEAR,
            )
    }

}