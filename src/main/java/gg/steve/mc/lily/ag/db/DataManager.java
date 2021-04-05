package gg.steve.mc.lily.ag.db;

import gg.steve.mc.lily.ag.framework.yml.Files;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataManager {
    private static Map<UUID, GiveawayData> data;

    public static void onLoad() {
        data = new HashMap<>();
        for (String entry : Files.DATA.get().getKeys(false)) {
            UUID uuid = UUID.fromString(entry);
            int hosted = Files.DATA.get().getInt(entry + ".hosted");
            int won = Files.DATA.get().getInt(entry + ".won");
            data.putIfAbsent(uuid, new GiveawayData(hosted, won));
        }
    }

    public static void onShutdown() {
        if (data != null && data.isEmpty()) {
            for (String entry : Files.DATA.get().getKeys(false)) {
                Files.DATA.get().set(entry, null);
            }
            for (UUID uuid : data.keySet()) {
                Files.DATA.get().set(String.valueOf(uuid) + ".hosted", data.get(uuid).getHosted());
                Files.DATA.get().set(String.valueOf(uuid) + ".won", data.get(uuid).getWon());
            }
            Files.DATA.save();
            data.clear();
        }
    }

    public static void incrementHosted(UUID giver) {
        if (data == null) data = new HashMap<>();
        if (data.containsKey(giver)) {
            data.get(giver).incrementHosted();
        } else {
            data.put(giver, new GiveawayData(1, 0));
        }
    }

    public static void incrementWon(UUID receiver) {
        if (data == null) data = new HashMap<>();
        if (data.containsKey(receiver)) {
            data.get(receiver).incrementWon();
        } else {
            data.put(receiver, new GiveawayData(0, 1));
        }
    }

    public static int getHosted(UUID uuid) {
        if (data == null || data.isEmpty() || !data.containsKey(uuid)) return 0;
        return data.get(uuid).getHosted();
    }

    public static int getWon(UUID uuid) {
        if (data == null || data.isEmpty() || !data.containsKey(uuid)) return 0;
        return data.get(uuid).getWon();
    }
}
