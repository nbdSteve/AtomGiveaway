package gg.steve.mc.lily.ag.framework.utils;

import gg.steve.mc.lily.ag.AtomGiveaway;

public class LogUtil {

    public static void info(String message) {
        AtomGiveaway.getInstance().getLogger().info(message);
    }

    public static void warning(String message) {
        AtomGiveaway.getInstance().getLogger().warning(message);
    }
}
