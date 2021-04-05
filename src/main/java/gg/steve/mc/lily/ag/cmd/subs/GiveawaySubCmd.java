package gg.steve.mc.lily.ag.cmd.subs;

import com.github.bingorufus.chatitemdisplay.DisplayParser;
import gg.steve.mc.lily.ag.core.Giveaway;
import gg.steve.mc.lily.ag.core.GiveawayManager;
import gg.steve.mc.lily.ag.framework.cmd.SubCommand;
import gg.steve.mc.lily.ag.framework.message.DebugMessage;
import gg.steve.mc.lily.ag.framework.message.GeneralMessage;
import gg.steve.mc.lily.ag.framework.message.JsonMessage;
import gg.steve.mc.lily.ag.framework.permission.PermissionNode;
import gg.steve.mc.lily.ag.framework.utils.ColorUtil;
import gg.steve.mc.lily.ag.framework.yml.Files;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

public class GiveawaySubCmd extends SubCommand {

    public GiveawaySubCmd() {
        super("cancel", 0, 2, true, PermissionNode.GIVEAWAY);
        addAlias("c");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                DebugMessage.AIR_ITEM.message(sender);
                return;
            }
            Giveaway giveaway = new Giveaway(player, player.getInventory().getItemInMainHand());
            GiveawayManager.addGiveaway(giveaway);
            for (String line : Files.MESSAGES.get().getStringList("giveaway-added.text")) {
                TextComponent comp;
                if (giveaway.getItem().hasItemMeta() && giveaway.getItem().getItemMeta().hasDisplayName()) {
                    comp = new TextComponent(ColorUtil.colorize(line).replace("{item}", giveaway.getItem().getItemMeta().getDisplayName()));
                } else {
                    comp = new TextComponent(ColorUtil.colorize(line).replace("{item}", giveaway.getItem().getType().name()));
                }
                comp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/giveaway cancel " + giveaway.getId()));
                comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(getShit(giveaway.getItem())).create()));
                player.spigot().sendMessage(comp);
            }
            return;
        } else if (args.length == 1) {
            List<Giveaway> giveaways = GiveawayManager.getHostingGiveawaysForPlayer(player);
            if (giveaways.size() == 1) {
                GiveawayManager.cancelGiveaway(giveaways.get(0).getId());
                GeneralMessage.GIVEAWAY_CANCEL.message(player);
                return;
            } else if (GiveawayManager.getHostingGiveawaysForPlayer(player).size() == 0) {
                DebugMessage.NO_GIVEAWAYS.message(player);
                return;
            } else {
                DebugMessage.MORE_THAN_1_GIVEAWAY.message(player);
                return;
            }
        } else {
            UUID giveawayId;
            try {
                giveawayId = UUID.fromString(args[1]);
                if (GiveawayManager.getGiveawayById(giveawayId) == null) throw new Exception();
            } catch (Exception e) {
                DebugMessage.INVALID_GIVEAWAY.message(player);
                return;
            }
            if (player.getUniqueId() != GiveawayManager.getGiveawayById(giveawayId).getGiver().getUniqueId()) return;
            GiveawayManager.cancelGiveaway(giveawayId);
            GeneralMessage.GIVEAWAY_CANCEL.message(player);
            return;
        }
    }

    public static String getShit(ItemStack item) {
        StringBuilder builder = new StringBuilder();
        if (!item.hasItemMeta()) return item.getType().name();
        ItemMeta m = item.getItemMeta();
        if (m.hasDisplayName()) {
            builder.append(m.getDisplayName() + "\n");
        } else {
            builder.append(item.getType().name() + "\n");
        }
        if (m.hasEnchants()) {
            for (Enchantment enchantment : m.getEnchants().keySet()) {
                builder.append("&7" + Files.CONFIG.get().getString("enchant-map." + enchantment.getName().toLowerCase()) + " " + integerToRoman5(m.getEnchantLevel(enchantment)) + "\n");
            }
        }
        if (m.hasLore()) {
            for (String line : m.getLore()) {
                builder.append(line + "\n");
            }
        }
        builder.lastIndexOf("\n");
        return ColorUtil.colorize(builder.toString());
    }

    private static final TreeMap<Integer, String> treemap = new TreeMap<Integer, String>();
    static {
        treemap.put(1000, "M");
        treemap.put(900, "CM");
        treemap.put(500, "D");
        treemap.put(400, "CD");
        treemap.put(100, "C");
        treemap.put(90, "XC");
        treemap.put(50, "L");
        treemap.put(40, "XL");
        treemap.put(10, "X");
        treemap.put(9, "IX");
        treemap.put(5, "V");
        treemap.put(4, "IV");
        treemap.put(1, "I");

    }

    public static final String integerToRoman5(int number) {
        int l = treemap.floorKey(number);
        if (number == l) {
            return treemap.get(number);
        }
        return treemap.get(l) + integerToRoman5(number - l);
    }
}
