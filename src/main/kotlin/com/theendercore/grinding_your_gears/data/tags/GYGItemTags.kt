package com.theendercore.grinding_your_gears.data.tags

import net.minecraft.core.registries.Registries
import com.theendercore.grinding_your_gears.GrindingYourGears.id
import com.theendercore.grinding_your_gears.util.tag

object GYGItemTags {

    val APPLE_LIKE = create("apple_like")

    fun create(id: String) = Registries.ITEM.tag(id(id))

}