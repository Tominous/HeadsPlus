package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.HeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class WhitelistAdd {
    private static FileConfiguration config = HeadsPlus.getInstance().getConfig();
    private static File configF = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");

    public static void wlAdd(CommandSender sender, String name) {
        if (sender.hasPermission("headsplus.maincommand.whitelist.add")) {
            if (name.matches("^[A-Za-z0-9_]+$")) {
                try {

                    if  (!(configF.exists())) {
                        HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();
                        @SuppressWarnings("unused")
                        File cfile = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
                    }
                    List<String> wl = config.getStringList("whitelist");
                    String aHead = name.toLowerCase();
                    if (wl.contains(aHead)) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("head-a-add"))));
                    } else {
                        wl.add(aHead);
                        config.set("whitelist", wl);
                        config.options().copyDefaults(true);
                        HeadsPlus.getInstance().saveConfig();

                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("head-added").replaceAll("%p", name))));
                    }
                } catch (Exception e) {
                    HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to add head!");
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("wl-fail"))));


                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HeadsPlus.getInstance().translateMessages(HeadsPlusConfig.getMessages().getString("alpha-names"))));
            }
        } else {
            sender.sendMessage(HeadsPlusCommand.noPerms);
        }

    }
}
