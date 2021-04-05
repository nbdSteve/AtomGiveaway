package gg.steve.mc.lily.ag.framework.utils;

import gg.steve.mc.lily.ag.AtomGiveaway;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkUtil {

    public static void spawn(Location location) {
        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta meta = firework.getFireworkMeta();
        meta.setPower(2);
        meta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BURST).withColor(Color.PURPLE).withColor(Color.WHITE).build());
        firework.setFireworkMeta(meta);
        Bukkit.getScheduler().runTaskLater(AtomGiveaway.getInstance(), firework::detonate, 10l);
    }
}
