package gg.steve.mc.lily.ag.papi;

import gg.steve.mc.lily.ag.AtomGiveaway;
import gg.steve.mc.lily.ag.db.DataManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class AtomGiveawayExpansion extends PlaceholderExpansion {

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "ag";
    }

    @Override
    public String getAuthor() {
        return AtomGiveaway.getInstance().getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return AtomGiveaway.getInstance().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (identifier.contains("hosted")) {
            return String.valueOf(DataManager.getHosted(player.getUniqueId()));
        } else if (identifier.contains("won")) {
            return String.valueOf(DataManager.getWon(player.getUniqueId()));
        }
        return "Invalid Placeholder.";
    }
}
