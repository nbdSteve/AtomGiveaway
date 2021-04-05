package gg.steve.mc.lily.ag.framework;

import gg.steve.mc.lily.ag.cmd.GiveawayCmd;
import gg.steve.mc.lily.ag.core.GiveawayManager;
import gg.steve.mc.lily.ag.db.DataManager;
import gg.steve.mc.lily.ag.framework.utils.LogUtil;
import gg.steve.mc.lily.ag.framework.yml.Files;
import gg.steve.mc.lily.ag.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.lily.ag.papi.AtomGiveawayExpansion;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Class that handles setting up the plugin on start
 */
public class SetupManager {
    private static FileManagerUtil fileManagerUtil;

    private SetupManager() throws IllegalAccessException {
        throw new IllegalAccessException("Manager class cannot be instantiated.");
    }

    /**
     * Loads the files into the file manager
     */
    public static void setupFiles(FileManagerUtil fm) {
        fileManagerUtil = fm;
        Files.CONFIG.load(fm);
        Files.PERMISSIONS.load(fm);
        Files.DATA.load(fm);
        Files.DEBUG.load(fm);
        Files.MESSAGES.load(fm);
    }

    public static void registerCommands(JavaPlugin instance) {
        instance.getCommand("giveaway").setExecutor(new GiveawayCmd());
        instance.getCommand("giveaway").setTabCompleter(new GiveawayCmd());
    }

    /**
     * Register all of the events for the plugin
     *
     * @param instance Plugin, the main plugin instance
     */
    public static void registerEvents(JavaPlugin instance) {
        PluginManager pm = instance.getServer().getPluginManager();
    }

    public static void registerEvent(JavaPlugin instance, Listener listener) {
        instance.getServer().getPluginManager().registerEvents(listener, instance);
    }

    public static void loadPluginCache() {
        // gui
        DataManager.onLoad();
        GiveawayManager.onLoad();
    }

    public static void shutdownPluginCache() {
        // gui
        GiveawayManager.onShutdown();
        DataManager.onShutdown();
    }

    public static void registerPlaceholderExpansions() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            LogUtil.info("PlaceholderAPI found, registering expansions with it now...");
            new AtomGiveawayExpansion().register();
        }
    }

    public static FileManagerUtil getFileManagerUtil() {
        return fileManagerUtil;
    }
}
