import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.*
import org.bukkit.plugin.java.JavaPlugin

fun validOre(b: Block): Boolean {
    return b.type.name.contains("ORE")
}

class NodeBreak: JavaPlugin(), Listener {
    init {
        plugin = this
    }

    companion object {
        lateinit var plugin: JavaPlugin
    }

    private val maxDepth: UInt = this.config.getInt("depth").toUInt()
    private val toolsAllowed: List<String> = this.config.getStringList("allowed_tools")
    private val gamemodesAllowed: List<String> = this.config.getStringList("allowed_gamemodes")

    override fun onEnable() {
        this.saveDefaultConfig()

        val manager = this.server.pluginManager

        manager.registerEvents(this, this)
    }

    @EventHandler
    fun onBlockBroke(event: BlockBreakEvent) {
        val ply = event.player
        val tool = ply.inventory.itemInMainHand
        if( !gamemodesAllowed.contains( ply.gameMode.name.lowercase() ) ) return
        if( !toolsAllowed.contains( tool.type.name.lowercase() ) ) return
        if(ply.isSneaking) return

        val block = event.block
        if (validOre(block)) {
            val broken = breakNode(block, 0u, null)
            // this.logger.info("Broke $broken nodes!")
            ply.playSound( block.location, Sound.BLOCK_TUFF_BREAK, 1f + broken.toFloat() * 0.3f, 1f )
        }
    }

    private fun breakNode(b: Block, fromOrigin: UInt, ignoreDir: BlockFace?): UInt {
        val dirs = BlockFace.values()
        var nodesBroken = if (b.breakNaturally()) 1u else 0u

        if (fromOrigin >= this.maxDepth) {
            return nodesBroken
        }

        for ( dir in dirs ) {
            if (ignoreDir != null && dir == ignoreDir) {
                // Ignore the direction toward the block that we just came from
                continue
            }
            val block = b.getRelative(dir)
            if (validOre(block)) {
                nodesBroken += breakNode(block, fromOrigin + 1u, dir.oppositeFace )
            }
        }
        return nodesBroken
    }
}