package com.github.shatteredsoftware.core.messenger

import com.github.shatteredsoftware.core.data.NamespaceKey
import com.github.shatteredsoftware.core.data.NamespaceTypeKey
import com.github.shatteredsoftware.core.manager.Manager
import com.github.shatteredsoftware.core.manager.ManagerBuilder
import com.github.shatteredsoftware.core.manager.read.NullManagerReads
import com.github.shatteredsoftware.core.manager.write.NullManagerWrites
import com.github.shatteredsoftware.core.module.CoreModule

class Messenger : CoreModule {
    val messageManager = Manager.builder(
        NamespaceTypeKey("ShatteredCore", "message", Message::class.java),
        NullManagerReads,
        NullManagerWrites
    )

    val pluralizationManager = Manager.builder(
        NamespaceTypeKey("ShatteredCore", "pluralization_rules", PluralizationRules::class.java),
        NullManagerReads,
        NullManagerWrites
    )

    override fun getKey(): NamespaceKey {
        return NamespaceKey("ShatteredCore", "messenger")
    }

    override fun getManagersToRegister(): List<Manager<*>> {
        return super.getManagersToRegister()
    }

    override fun onStart(): () -> Unit {
        TODO("Not yet implemented")
    }

    override fun onStop(): () -> Unit {
        TODO("Not yet implemented")
    }
}