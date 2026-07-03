package com.theendercore.grinding_your_gears

import com.mojang.brigadier.context.CommandContext
import com.theendercore.grinding_your_gears.data.tags.GYGItemTags
import com.theendercore.grinding_your_gears.util.CUSTOM_RECIPE_CACHE
import com.theendercore.grinding_your_gears.util.isDev
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.resource.v1.ResourceLoader
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands.literal
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManagerReloadListener
import net.minecraft.world.item.ItemStack
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object GrindingYourGears {

    const val MODID = "grinding_your_gears"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(GrindingYourGears::class.simpleName)

//    @JvmField
//    var config = ConfigApi.registerAndLoadConfig(::GYGConfig)

    fun init() {
        if (isDev()) CommandRegistrationCallback.EVENT.register { dispatcher, context, selection ->
            val dumpItems = literal("dump_items").executes(::dumpItems).build()
            dispatcher.root.addChild(dumpItems)
        }

        ResourceLoader.get(PackType.SERVER_DATA)
            .registerReloader(id("cache_invalidator"), ResourceManagerReloadListener { CUSTOM_RECIPE_CACHE.clear() })
    }

    fun dumpItems(context: CommandContext<CommandSourceStack>): Int {
        val src = context.source ?: return -1
        for (item in BuiltInRegistries.ITEM) {
            val stack = item.defaultInstance
            if (canCrush(stack)) {
                src.sendSystemMessage(Component.literal(item.name.string + ":" + item.defaultMaxStackSize))
            }
        }

        return 0
    }

    @JvmStatic
    fun canCrush(stack: ItemStack): Boolean = stack.`is`(GYGItemTags.CRUSHABLE) && !stack.`is`(GYGItemTags.NOT_CRUSHABLE)

    fun id(namespace: String, path: String): Identifier = Identifier.fromNamespaceAndPath(namespace, path)
    fun mc(path: String): Identifier = Identifier.withDefaultNamespace(path)
    fun id(path: String) = id(MODID, path)

}