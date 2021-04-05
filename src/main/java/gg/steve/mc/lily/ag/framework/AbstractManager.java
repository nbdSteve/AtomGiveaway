package gg.steve.mc.lily.ag.framework;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractManager {
    private static List<AbstractManager> managers;

    private static void initialiseManagerList() {
        if (managers == null) managers = new ArrayList<>();
    }

    private static void addManager(AbstractManager manager) {
        initialiseManagerList();
        if (managers.contains(manager)) return;
        managers.add(manager);
    }

    public static void loadManagers() {
        if (managers == null || managers.isEmpty()) return;
        for (AbstractManager manager : managers) {
            manager.onLoad();
        }
    }

    public static void shutdownManagers() {
        if (managers == null || managers.isEmpty()) return;
        for (AbstractManager manager : managers) {
            manager.onShutdown();
        }
    }

    public static List<AbstractManager> getActiveManagers() {
        initialiseManagerList();
        return managers;
    }

    public AbstractManager() {
        addManager(this);
    }

    public abstract void onLoad();

    public abstract void onShutdown();
}
