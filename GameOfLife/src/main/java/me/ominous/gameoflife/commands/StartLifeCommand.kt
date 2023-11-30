package me.ominous.gameoflife.commands

import me.ominous.gameoflife.managers.LifeManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class StartLifeCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        LifeManager.startLife()

        return false
    }
}