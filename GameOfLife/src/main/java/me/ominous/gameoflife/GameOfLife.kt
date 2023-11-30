package me.ominous.gameoflife

import me.ominous.gameoflife.commands.ResetLifeCommand
import me.ominous.gameoflife.commands.StartLifeCommand
import me.ominous.gameoflife.listeners.ServerTickListener
import me.ominous.gameoflife.managers.LifeManager
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin

class GameOfLife : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic

        server.pluginManager.registerEvents(ServerTickListener(), this)

        getCommand("start-life")!!.setExecutor(StartLifeCommand())
        getCommand("reset-life")!!.setExecutor(ResetLifeCommand())

        for(x in -100..100) {
            for(z in -100..100) {
                LifeManager.cells.add(Bukkit.getWorld("world")!!.getBlockAt(x, 64, z))
            }
        }

        for(cell in LifeManager.cells) {
            cell.type = Material.BLACK_CONCRETE
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
        LifeManager.clearLife()
    }
}
