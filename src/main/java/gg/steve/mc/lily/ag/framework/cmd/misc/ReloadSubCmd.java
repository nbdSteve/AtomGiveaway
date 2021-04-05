package gg.steve.mc.lily.ag.framework.cmd.misc;

import gg.steve.mc.lily.ag.AtomGiveaway;
import gg.steve.mc.lily.ag.framework.cmd.SubCommand;
import gg.steve.mc.lily.ag.framework.message.GeneralMessage;
import gg.steve.mc.lily.ag.framework.permission.PermissionNode;
import gg.steve.mc.lily.ag.framework.yml.Files;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ReloadSubCmd extends SubCommand {

    public ReloadSubCmd() {
        super("reload", 1, 1, false, PermissionNode.RELOAD);
        addAlias("r");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Files.reload();
        Bukkit.getPluginManager().disablePlugin(AtomGiveaway.getInstance());
        AtomGiveaway.getInstance().onLoad();
        Bukkit.getPluginManager().enablePlugin(AtomGiveaway.getInstance());
        GeneralMessage.RELOAD.message(sender);
    }
}
