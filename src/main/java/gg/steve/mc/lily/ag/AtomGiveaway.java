package gg.steve.mc.lily.ag;

import gg.steve.mc.lily.ag.framework.SetupManager;
import gg.steve.mc.lily.ag.framework.yml.utils.FileManagerUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class AtomGiveaway extends JavaPlugin {
    private static AtomGiveaway instance;
    private static Random random;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        SetupManager.setupFiles(new FileManagerUtil(instance));
        SetupManager.registerCommands(instance);
        SetupManager.registerEvents(instance);
        SetupManager.loadPluginCache();
        SetupManager.registerPlaceholderExpansions();
        random = new Random();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static AtomGiveaway getInstance() {
        return instance;
    }

    public static Random getRandom() {
        return random;
    }
}
