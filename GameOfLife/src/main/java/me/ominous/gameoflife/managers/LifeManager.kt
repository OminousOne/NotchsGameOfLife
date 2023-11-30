package me.ominous.gameoflife.managers

import me.ominous.gameoflife.enums.CellAction
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block

class LifeManager {

    companion object {

        val cells: MutableList<Block> = mutableListOf()
        val tickUpdates: MutableMap<Location, CellAction> = mutableMapOf()

        var isLifeRunning: Boolean = false
        var generation = 0

        fun startLife() {
            isLifeRunning = true
        }

        fun tickLife() {
            if(!isLifeRunning) return

            for(cell in cells) tickCell(cell)

            applyChanges()

            for(player in Bukkit.getOnlinePlayers()) player.sendActionBar(Component.text("Generation: $generation"))

            generation++
        }

        fun resetLife() {
            generation = 0
            isLifeRunning = false
            tickUpdates.clear()

            for(x in -100..100) {
                for(z in -100..100) {
                    cells.add(Bukkit.getWorld("world")!!.getBlockAt(x, 64, z))
                }
            }

            for(cell in cells) {
                cell.type = Material.BLACK_CONCRETE
            }
        }

        fun clearLife() {
            for(block in cells) block.type = Material.AIR
        }

        private fun applyChanges() {
            for(cellLocation: Location in tickUpdates.keys) {
                if(tickUpdates[cellLocation] == CellAction.CELL_DEATH) cellLocation.block.type = Material.BLACK_CONCRETE
                if(tickUpdates[cellLocation] == CellAction.CELL_BIRTH) cellLocation.block.type = Material.WHITE_CONCRETE
            }
        }

        private fun tickCell(cell: Block) {
            var neighbourCount = 0

            if(cell.world.getBlockAt(cell.x + -1, cell.y, cell.z + 1).type == Material.WHITE_CONCRETE) neighbourCount++
            if(cell.world.getBlockAt(cell.x, cell.y, cell.z + 1).type == Material.WHITE_CONCRETE) neighbourCount++
            if(cell.world.getBlockAt(cell.x + 1, cell.y, cell.z + 1).type == Material.WHITE_CONCRETE) neighbourCount++

            if(cell.world.getBlockAt(cell.x + -1, cell.y, cell.z).type == Material.WHITE_CONCRETE) neighbourCount++
            if(cell.world.getBlockAt(cell.x + 1, cell.y, cell.z).type == Material.WHITE_CONCRETE) neighbourCount++

            if(cell.world.getBlockAt(cell.x + -1, cell.y, cell.z - 1).type == Material.WHITE_CONCRETE) neighbourCount++
            if(cell.world.getBlockAt(cell.x, cell.y, cell.z - 1).type == Material.WHITE_CONCRETE) neighbourCount++
            if(cell.world.getBlockAt(cell.x + 1, cell.y, cell.z - 1).type == Material.WHITE_CONCRETE) neighbourCount++

            if(neighbourCount < 2) { killCell(cell); return }
            if(neighbourCount > 3) { killCell(cell); return }
            if(neighbourCount == 3 && cell.type != Material.WHITE_CONCRETE) { birthCell(cell); return }
        }

        private fun killCell(cell: Block) {
            tickUpdates[cell.location] = CellAction.CELL_DEATH
        }

        private fun birthCell(cell: Block) {
            tickUpdates[cell.location] = CellAction.CELL_BIRTH
        }
    }
}