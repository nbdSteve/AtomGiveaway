package gg.steve.mc.lily.ag.core;

import com.google.common.collect.Lists;
import gg.steve.mc.lily.ag.AtomGiveaway;
import gg.steve.mc.lily.ag.cmd.subs.GiveawaySubCmd;
import gg.steve.mc.lily.ag.db.DataManager;
import gg.steve.mc.lily.ag.framework.message.BroadcastMessage;
import gg.steve.mc.lily.ag.framework.utils.ColorUtil;
import gg.steve.mc.lily.ag.framework.yml.Files;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.UUID;

public class Giveaway {
    private Player giver, receiver;
    private UUID id;
    private ItemStack item;
    private boolean cancel, waiting;
    private BukkitTask task;

    public Giveaway(Player giver, ItemStack item) {
        this.giver = giver;
        this.item = item.clone();
        this.giver.getInventory().remove(item);
        this.id = UUID.randomUUID();
        this.cancel = false;
        this.waiting = true;
        this.doWaitTask();
    }

    private Player getRandomReceiver(int i) {
        if (i > 100) return this.giver;
        List<Player> players = Lists.newArrayList(Bukkit.getOnlinePlayers());
        int index = AtomGiveaway.getRandom().nextInt(players.size());
        Player player = players.get(index);
        if (Files.CONFIG.get().getStringList("exempt-players").contains(player.getName())) return getRandomReceiver(i+1);
        return player;
    }

    public void doWaitTask() {
        this.task = Bukkit.getScheduler().runTaskLater(AtomGiveaway.getInstance(), () -> {
            if (this.cancel) return;
            this.waiting = false;
            this.receiver = getRandomReceiver(0);
            if (this.receiver.getInventory().firstEmpty() == -1) {
                this.receiver.getWorld().dropItem(this.receiver.getLocation(), this.item);
            } else {
                this.receiver.getInventory().addItem(this.item);
            }
            DataManager.incrementHosted(this.giver.getUniqueId());
            DataManager.incrementWon(this.receiver.getUniqueId());

            for (Player player : Bukkit.getOnlinePlayers()) {
                for (String line : Files.MESSAGES.get().getStringList("giveaway-bc.text")) {
                    TextComponent comp;
                    if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                        comp = new TextComponent(ColorUtil.colorize(line).replace("{item}", item.getItemMeta().getDisplayName())
                        .replace("{giver}", this.giver.getName())
                        .replace("{receiver}", this.receiver.getName()));
                    } else {
                        comp = new TextComponent(ColorUtil.colorize(line).replace("{item}", item.getType().name())
                                .replace("{giver}", this.giver.getName())
                                .replace("{receiver}", this.receiver.getName()));
                    }
                    comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(GiveawaySubCmd.getShit(item)).create()));
                    player.spigot().sendMessage(comp);
                }
            }

//            BroadcastMessage.GIVEAWAY.broadcast(this.giver.getName(), this.receiver.getName());
            GiveawayManager.removeGiveaway(this);
        }, Files.CONFIG.get().getLong("giveaway.countdown"));
    }

    public void cancel() {
        if (!this.waiting) return;
        this.cancel = true;
        this.task.cancel();
        if (this.giver.getInventory().firstEmpty() == -1) {
            this.giver.getWorld().dropItem(this.giver.getLocation(), this.item);
        } else {
            this.giver.getInventory().addItem(this.item);
        }
    }

    public void restore() {
        if (!this.cancel && this.waiting) {
            if (this.giver.getInventory().firstEmpty() == -1) {
                this.giver.getWorld().dropItem(this.giver.getLocation(), this.item);
            } else {
                this.giver.getInventory().addItem(this.item);
            }
        }
    }

    public UUID getId() {
        return id;
    }

    public Player getGiver() {
        return giver;
    }

    public ItemStack getItem() {
        return item;
    }

    public boolean isCancel() {
        return cancel;
    }

    public boolean isWaiting() {
        return waiting;
    }
}
