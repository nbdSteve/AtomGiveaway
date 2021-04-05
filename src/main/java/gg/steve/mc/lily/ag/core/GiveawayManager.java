package gg.steve.mc.lily.ag.core;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GiveawayManager {
    private static Map<UUID, Giveaway> giveaways;

    public static void onLoad() {
        giveaways = new HashMap<>();
    }

    public static void onShutdown() {
        if (giveaways == null || giveaways.isEmpty()) return;
        for (Giveaway giveaway : giveaways.values()) {
            giveaway.restore();
        }
    }

    public static void addGiveaway(Player giver, ItemStack item) {
        if (giveaways == null) giveaways = new HashMap<>();
        Giveaway giveaway = new Giveaway(giver, item);
        giveaways.put(giveaway.getId(), giveaway);
    }

    public static void addGiveaway(Giveaway giveaway) {
        if (giveaways == null) giveaways = new HashMap<>();
        if (giveaways.containsKey(giveaway.getId())) return;
        giveaways.put(giveaway.getId(), giveaway);
    }

    public static void cancelGiveaway(UUID giveawayId) {
        if (giveaways == null || giveaways.isEmpty()) return;
        if (!giveaways.containsKey(giveawayId)) return;
        giveaways.get(giveawayId).cancel();
        giveaways.remove(giveawayId);
    }

    public static List<Giveaway> getHostingGiveawaysForPlayer(Player player) {
        List<Giveaway> hosting = new ArrayList<>();
        if (giveaways == null || giveaways.isEmpty()) return hosting;
        for (Giveaway giveaway : giveaways.values()) {
            if (giveaway.getGiver().getUniqueId().equals(player.getUniqueId())) hosting.add(giveaway);
        }
        return hosting;
    }

    public static Giveaway getGiveawayById(UUID id) {
        if (giveaways == null || giveaways.isEmpty()) return null;
        return giveaways.get(id);
    }

    public static void removeGiveaway(Giveaway giveaway) {
        giveaways.remove(giveaway.getId());
    }
}
