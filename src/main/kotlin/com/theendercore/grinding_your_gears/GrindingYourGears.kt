package com.theendercore.grinding_your_gears

import com.mojang.brigadier.context.CommandContext
import com.theendercore.grinding_your_gears.config.GYGConfig
import com.theendercore.grinding_your_gears.mixin.IngredientAccessor
import com.theendercore.grinding_your_gears.mixin.ShapedRecipeAccessor
import dev.amymialee.grindering.recipes.GrindingRecipe
import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands.literal
import net.minecraft.core.HolderSet
import net.minecraft.core.NonNullList
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.jvm.optionals.getOrNull

object GrindingYourGears {

    const val MODID = "grinding_your_gears"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(GrindingYourGears::class.simpleName)

    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::GYGConfig)

    fun init() {
        log.info("Hello from Common ${config.commonEntry.get()}")

//        GrindingRecipe

        CommandRegistrationCallback.EVENT.register { dispatcher, context, selection ->
            val dumpItems = literal("dump_items").executes(::dumpItems).build()
            dispatcher.root.addChild(dumpItems)
        }
    }

    fun dumpItems(context: CommandContext<CommandSourceStack>): Int {

        val src = context.source ?: return -1
        val player = src.player ?: return -1

        val level = src.level

        for (item in BuiltInRegistries.ITEM) {
            val stack = item.defaultInstance
            if (canUnCraft(stack)) {
                src.sendSystemMessage(Component.literal(item.name.string + ":" + item.defaultMaxStackSize))
            }
        }

        return 0
    }

    @JvmStatic
    fun canUnCraft(stack: ItemStack): Boolean {
        return stack.has(DataComponents.ENCHANTABLE) || stack.has(DataComponents.TOOL) || stack.has(DataComponents.BLOCKS_ATTACKS)
    }


    @JvmStatic
    fun crateGrindingRecipe(id: Identifier, recipe: ShapedRecipeAccessor): GrindingRecipe? {
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

        println(id)

        return GrindingRecipe(
            NonNullList.of(input, input),
            recipe.gyg_result().count,
            ing2[0], ing2.getOrElse(1) { ItemStack.EMPTY },
            28, false, ""
        )
    }

    fun Ingredient.getItem(): Item? {
        val this2 = (this as IngredientAccessor).values
        if (this2 is HolderSet.Named<*>) {
            println(this2)
        }

        return items().findFirst().getOrNull()?.value()
    }

    fun id(namespace: String, path: String): Identifier = Identifier.fromNamespaceAndPath(namespace, path)
    fun mc(path: String): Identifier = Identifier.withDefaultNamespace(path)
    fun id(path: String) = id(MODID, path)

}