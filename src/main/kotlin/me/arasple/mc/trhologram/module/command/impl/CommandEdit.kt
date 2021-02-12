package me.arasple.mc.trhologram.module.command.impl

import io.izzel.taboolib.module.command.base.Argument
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.locale.TLocale
import me.arasple.mc.trhologram.module.display.Hologram
import me.arasple.mc.trhologram.module.editor.Editor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @author Arasple
 * @date 2021/2/12 17:41
 */
class CommandEdit : BaseSubCommand() {

    override fun getArguments() = arrayOf(
        Argument("Id", true) {
            Hologram.holograms.map { it.id }
        }
    )

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>) {
        val hologram = Hologram.findHologram { it.id.equals(args[0], true) }
        val player = sender as Player

        if (hologram == null) {
            TLocale.sendTo(sender, "Command.Existed")
            return
        }

        Editor.contentEditor(player, hologram)
    }

}