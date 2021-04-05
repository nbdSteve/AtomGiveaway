package gg.steve.mc.lily.ag.framework.cmd;

import gg.steve.mc.lily.ag.cmd.subs.GiveawaySubCmd;
import gg.steve.mc.lily.ag.framework.cmd.misc.HelpSubCmd;
import gg.steve.mc.lily.ag.framework.cmd.misc.ReloadSubCmd;

public enum SubCommandType {
    HELP_CMD(new HelpSubCmd()),
    RELOAD_CMD(new ReloadSubCmd()),
    GIVEAWAY_CMD(new GiveawaySubCmd());

    private SubCommand sub;

    SubCommandType(SubCommand sub) {
        this.sub = sub;
    }

    public SubCommand getSub() {
        return sub;
    }
}
