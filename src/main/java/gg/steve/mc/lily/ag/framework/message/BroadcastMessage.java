package gg.steve.mc.lily.ag.framework.message;

import gg.steve.mc.lily.ag.framework.actionbarapi.ActionBarAPI;
import gg.steve.mc.lily.ag.framework.utils.ColorUtil;
import gg.steve.mc.lily.ag.framework.yml.Files;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public enum BroadcastMessage {
    GIVEAWAY("giveaway-bc", "{giver}", "{receiver}");

    private final String path;
    private boolean actionBar;
    private List<String> placeholders;

    BroadcastMessage(String path, String... placeholders) {
        this.path = path;
        this.placeholders = Arrays.asList(placeholders);
        this.actionBar = Files.MESSAGES.get().getBoolean(this.path + ".action-bar");
    }

    public void broadcast(String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar) {
            for (String line : Files.MESSAGES.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                if (!ActionBarAPI.sendActionBarToAllPlayers(ColorUtil.colorize(line))) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ColorUtil.colorize(line)));
                    }
                }
            }
        } else {
            for (String line : Files.MESSAGES.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                Bukkit.broadcastMessage(ColorUtil.colorize(line));
            }
        }
    }
}
