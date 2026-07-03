package com.theendercore.grinding_your_gears.config

import com.theendercore.grinding_your_gears.GrindingYourGears.MODID
import com.theendercore.grinding_your_gears.GrindingYourGears.id
import me.fzzyhmstrs.fzzy_config.annotations.NonSync
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber.WidgetType.TEXTBOX_WITH_BUTTONS

class GYGConfig : Config(id(MODID)) {

    var groupName = ConfigGroup("group_id", false)

    var commonEntry = ValidatedInt(0, 10, -10, TEXTBOX_WITH_BUTTONS)

    @NonSync
    @ConfigGroup.Pop
    var clientEntry = true

}