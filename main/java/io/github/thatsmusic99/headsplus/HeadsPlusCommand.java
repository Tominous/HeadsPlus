package io.github.thatsmusic99.headsplus;

import java.io.File;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class HeadsPlusCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		FileConfiguration config = HeadsPlus.getInstance().getConfig();
		File configF = HeadsPlus.getInstance().configF;
		if (cmd.getName().equalsIgnoreCase("headsplus")) {
			if (sender.hasPermission("headsplus.maincommand")) {
			    if (args.length == 0) {
				    sender.sendMessage(ChatColor.DARK_BLUE + "===============" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "===============");
				    if (sender.hasPermission("headsplus.maincommand.reload")) {
					    sender.sendMessage(ChatColor.GRAY + "/headsplus reload - " + ChatColor.DARK_AQUA + "Reloads the config.");
				    }
					if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
						sender.sendMessage(ChatColor.GRAY + "/headsplus blacklistadd <IGN> - " + ChatColor.DARK_AQUA + "Adds a head to the blacklist.");
					}
					if (sender.hasPermission("headsplus.maincommand.blacklist.delete")) {
						sender.sendMessage(ChatColor.GRAY + "/headsplus blacklistdel <IGN> - " + ChatColor.DARK_AQUA + "Removes head from the blacklist.");
						
					}
					if (sender.hasPermission("headsplus.maincommand.blacklist.toggle")) {
						sender.sendMessage(ChatColor.GRAY + "/headsplus blacklist <On|Off> - " + ChatColor.DARK_AQUA + "Turns the blacklist on/off.");
					}
					if (sender.hasPermission("headsplus.maincommand.info")) {
						sender.sendMessage(ChatColor.GRAY + "/headsplus info - " + ChatColor.DARK_AQUA + "Gets plugin info.");
					}
					sender.sendMessage(ChatColor.DARK_BLUE + "========================================");
			    
			   } 
			   if ((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {
				   if (sender.hasPermission("headsplus.maincommand.reload")) {
				       sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Reloading config...");
				       try {

					       if  (!(configF.exists())) {
						       HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
						       HeadsPlus.getInstance().saveConfig();				   
					       } else {
						       HeadsPlus.getInstance().log.info("[HeadsPlus] Found config, loading!");
						       HeadsPlus.getInstance().reloadConfig();
						       HeadsPlus.getInstance().log.info("[HeadsPlus] Config reloaded!");
						       sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Reloaded config!");
					      }  
				       } catch (Exception e) {
					       HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to reload config - if this problem consists, contact Thatsmusic99!");
					       e.printStackTrace();
					       sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "HeadsPlus" + ChatColor.DARK_RED + "] " + "Failed to reload config.");
				       }
				   }
			   }
			   if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistadd"))) {
				   if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
				       try {

					       if  (!(configF.exists())) {
					           HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
					           config.options().copyDefaults(true);
                               HeadsPlus.getInstance().saveConfig();
                               File cfile = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
					           sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Config wasn't found, now created." );
					       }
					       List<String> blacklist = config.getStringList("blacklist");
					       String aHead = args[1].toLowerCase();
					       if (blacklist.contains(aHead)) {
						       sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "This head is already added!");
					       } else {
					    	   blacklist.add(aHead);
						       mConfig().set("blacklist", blacklist);
						       mConfig().options().copyDefaults(true);
						       saveMainConfig();
						       reloadMainConfig();
						       sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + args[1] + " has been added to the blacklist!");
					       }
				       } catch (Exception e) {
					       HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to add head!");
					       e.printStackTrace();
				       }
			  } else if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklistadd"))) {
				  if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
					  sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklistadd [IGN]");
				  }
			  } else if ((args.length > 2) && (args[0].equalsIgnoreCase("blacklistadd"))) {
				  if (sender.hasPermission("headsplus.maincommand.blacklist.add")) {
					  sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklistadd [IGN]");
					  sender.sendMessage(ChatColor.RED + "Too many arguments!");
				  }
			  }
		}
			  if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklistdel"))) {
				  if (sender.hasPermission("headsplus.maincommand.blacklist.delete")) {
					  try {
						  File mConfigF = new File(HeadsPlus.getInstance().getDataFolder(), "config.yml");
					       mConfig = YamlConfiguration.loadConfiguration(mConfigF);
					       if  (!(mConfigF.exists())) {
					           HeadsPlus.getInstance().log.info("[HeadsPlus] Config not found, creating!");
					           reloadMainConfig();
					           saveMainConfig();
					           sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Config wasn't found, now created." );
					       }
					       try {
					           List<String> blacklist = (List<String>)mConfig().getStringList("blacklist");
					           String rHead = args[1].toLowerCase();
					           if (blacklist.contains(rHead)) {
						           blacklist.remove(rHead);
						           mConfig().set("blacklist", blacklist);
						           mConfig().options().copyDefaults(true);
						           saveMainConfig();
						           sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + args[1] + " has been removed from the blacklist!");
					           } else {
					    	       sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "This head is not on the blacklist!");
					       }} catch (Exception e) {
					    	   HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove head!");
					    	   e.printStackTrace();
					       }
					       
				       } catch (Exception e) {
					       HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to remove head!");
					       e.printStackTrace();
				       }
				  }
			  } else if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklistdel"))) {
				  if (sender.hasPermission("headsplus.maincommand.blacklist.delete")) {
					  sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/heads [IGN]");
				  }
			  }
			  if ((args.length == 1) && (args[0].equalsIgnoreCase("blacklist"))) {
				  if (sender.hasPermission("headsplus.maincommand.blacklist.toggle")) {
					  try {
						  Boolean blacklistToggle = mConfig().getBoolean("blacklistOn");
						  if (blacklistToggle) {
							  mConfig().set("blacklistOn", false);
							  mConfig().options().copyDefaults(true);
					          saveMainConfig();
							  sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Blacklist disabled, use /headsplus blacklist to re-enable!");
						  } else if (!(blacklistToggle)) {
							  mConfig().set("blacklistOn", true);
							  mConfig().options().copyDefaults(true);
							  saveMainConfig();
							  sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Blacklist enabled, use /headsplus blacklist to disable!");
						  }
					  } catch (Exception e) {
						  HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle blacklist!");
						  e.printStackTrace();
						  sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "HeadsPlus" + ChatColor.DARK_RED + "] " + ChatColor.RED + "Failed to toggle blacklist.");
					  }
				  }
			  }
			  if ((args.length == 2) && (args[0].equalsIgnoreCase("blacklist"))) {
				  if (sender.hasPermission("headsplus.maincommand.blacklist.toggle")) {
					  try {
						  if (args[1].equalsIgnoreCase("on")) {
							  if (mConfig().getBoolean("blacklistOn")) {
							      mConfig().set("blacklistOn", true);
							      mConfig().options().copyDefaults(true);
							      saveMainConfig();
							  } else {
								  sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Blacklist is already disabled!");
							  }
							      sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Blacklist enabled!");
						  } else if (args[1].equalsIgnoreCase("off")) {
							  if (!(mConfig().getBoolean("blacklistOn"))) {
								  sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Blacklist is already enabled.");
							  } else {
							      mConfig().set("blacklistOn", false);
							      mConfig().options().copyDefaults(true);
					              saveMainConfig();
							      sender.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "] " + ChatColor.DARK_AQUA + "Blacklist disabled!");
							  }
						  } else {
							  sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklist [On|Off]");
						  }
					  } catch (Exception e) {
						  HeadsPlus.getInstance().log.severe("[HeadsPlus] Failed to toggle blacklist!");
						  e.printStackTrace();
						  sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "HeadsPlus" + ChatColor.DARK_RED + "] " + ChatColor.RED + "Failed to toggle blacklist.");
					  }
				  }
			  }
			  if ((args.length > 2) && (args[0].equalsIgnoreCase("blacklist"))) {
				  sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/headsplus blacklist [On|Off]");
			  }
			  
			  if ((args.length == 1) && (args[0].equalsIgnoreCase("info"))) {
				  String version = HeadsPlus.getInstance().version;
				  String author = HeadsPlus.getInstance().author;
				  sender.sendMessage(ChatColor.DARK_BLUE + "===============" + ChatColor.GOLD + "HeadsPlus" + ChatColor.DARK_BLUE + "===============");
				  sender.sendMessage(ChatColor.DARK_AQUA + "Version: " + ChatColor.GRAY + version);
				  sender.sendMessage(ChatColor.DARK_AQUA + "Author: " + ChatColor.GRAY + author);
			  }
			   
			    
			    
			
		    
	
		}

			}
	
	 
		return false;
	

	}	

}