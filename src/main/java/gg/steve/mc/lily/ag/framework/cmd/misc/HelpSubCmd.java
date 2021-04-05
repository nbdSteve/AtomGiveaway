package gg.steve.mc.lily.ag.framework.cmd.misc;

import gg.steve.mc.lily.ag.framework.cmd.SubCommand;
import gg.steve.mc.lily.ag.framework.message.GeneralMessage;
import gg.steve.mc.lily.ag.framework.permission.PermissionNode;
import org.bukkit.command.CommandSender;

public class HelpSubCmd extends SubCommand {

    public HelpSubCmd() {
        super("help", 1, 1, false, PermissionNode.HELP);
        addAlias("h");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        GeneralMessage.HELP.message(sender);
    }
}
