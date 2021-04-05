package gg.steve.mc.lily.ag.db;

public class GiveawayData {
    private int hosted, won;

    public GiveawayData(int hosted, int won) {
        this.hosted = hosted;
        this.won = won;
    }

    public void incrementHosted() {
        this.hosted++;
    }

    public void incrementWon() {
        this.won++;
    }

    public int getHosted() {
        return hosted;
    }

    public int getWon() {
        return won;
    }
}
