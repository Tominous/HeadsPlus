package io.github.thatsmusic99.headsplus.events;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.api.EntityHeadDropEvent;
import io.github.thatsmusic99.headsplus.api.HeadCraftEvent;
import io.github.thatsmusic99.headsplus.api.PlayerHeadDropEvent;
import io.github.thatsmusic99.headsplus.api.SellHeadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LBEvents implements Listener {

    @EventHandler
    public void onHeadDrop(EntityHeadDropEvent e) {
        if (!e.isCancelled()) {
            if (HeadsPlus.getInstance().lb) {
                HeadsPlus.getInstance().mySQLAPI.addOntoValue(e.getPlayer(), e.getEntityType().name(), "headspluslb", 0);
            }
        }
    }

    @EventHandler
    public void onPHeadDrop(PlayerHeadDropEvent e) {
        if (!e.isCancelled()) {
            if (HeadsPlus.getInstance().lb) {
                HeadsPlus.getInstance().mySQLAPI.addOntoValue(e.getKiller(), "player", "headspluslb", 0);
            }
        }
    }

    @EventHandler
    public void onHeadSold(SellHeadEvent e) {
        if (!e.isCancelled()) {
            if (HeadsPlus.getInstance().chal) {
                for (String s : e.getEntityAmounts().keySet()) {
                    for (int i : e.getEntityAmounts().values()) {
                        if (e.getEntityAmounts().get(s) == i) {
                            HeadsPlus.getInstance().mySQLAPI.addOntoValue(e.getPlayer(), s, "headsplussh", i);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onHeadCraft(HeadCraftEvent e) {
        if (!e.isCancelled()) {
            if (HeadsPlus.getInstance().chal) {
                if (e.getEntityType() != null) {
                    if (!e.getEntityType().equalsIgnoreCase("invalid")) {
                        HeadsPlus.getInstance().mySQLAPI.addOntoValue(e.getPlayer(), e.getEntityType(), "headspluscraft", e.getHeadsCrafted());
                    }
                }
            }
        }
    }
}
