package com.theendercore.grinding_your_gears

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import net.minecraft.resources.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.theendercore.grinding_your_gears.config.GYGConfig

object GrindingYourGears {

    const val MODID = "grinding_your_gears"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(GrindingYourGears::class.simpleName)

    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::GYGConfig)

    fun init() {
        log.info("Hello from Common ${config.commonEntry.get()}")
    }

    fun id(namespace: String, path: String): Identifier = Identifier.fromNamespaceAndPath(namespace, path)
    fun mc(path: String): Identifier = Identifier.withDefaultNamespace(path)
    fun id(path: String) = id(MODID, path)

}