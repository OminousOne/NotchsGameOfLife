package me.ominous.gameoflife.listeners

import com.destroystokyo.paper.event.server.ServerTickStartEvent
import me.ominous.gameoflife.managers.LifeManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class ServerTickListener : Listener {

    private var tickCounter: Int = 0

    @EventHandler
    fun serverTickStartEvent(event: ServerTickStartEvent) {
        if(tickCounter >= 10) {
            LifeManager.tickLife()
            tickCounter = 0
        }

        tickCounter++
    }
}