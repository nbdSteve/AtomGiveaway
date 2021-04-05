package gg.steve.mc.lily.ag.cmd;

import gg.steve.mc.lily.ag.core.Giveaway;
import gg.steve.mc.lily.ag.core.GiveawayManager;
import gg.steve.mc.lily.ag.framework.cmd.SubCommandType;
import gg.steve.mc.lily.ag.framework.message.DebugMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GiveawayCmd implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!SubCommandType.GIVEAWAY_CMD.getSub().isValidCommand(sender, args)) return true;
            SubCommandType.GIVEAWAY_CMD.getSub().onCommand(sender, args);
            return true;
        }
        for (SubCommandType subCommand : SubCommandType.values()) {
            if (!subCommand.getSub().isExecutor(args[0])) continue;
            if (!subCommand.getSub().isValidCommand(sender, args)) return true;
            subCommand.getSub().onCommand(sender, args);
            return true;
        }
        DebugMessage.INVALID_COMMAND.message(sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completion = new ArrayList<>();
        switch (args.length) {
            case 1:
                completion.add("cancel");
                break;
            case 2:
                switch (args[0]) {
                    case "cancel":
                    case "c":
                        if (!(sender instanceof Player)) return completion;
                        Player player = (Player) sender;
                        for (Giveaway giveaway : GiveawayManager.getHostingGiveawaysForPlayer(player)) {
                            completion.add(String.valueOf(giveaway.getId()));
                        }
                        break;
                }
        }
        return completion;
    }
}
