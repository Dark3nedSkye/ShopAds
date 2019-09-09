package com.dxrkenedsky.playershopads;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;


public final class PlayerShopAds extends JavaPlugin {


    public FileConfiguration config;
    public static Economy econ;
    int price;



    Logger log = Bukkit.getLogger();

    @Override
    public void onEnable() {

        setupEconomy();

        if (!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        File configFile = new File(getDataFolder(), "AdConfig.yml");
        config = YamlConfiguration.loadConfiguration(configFile);

        price = config.getInt("price");





    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    TimedAd ads = new TimedAd(econ, price);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {

            if (command.getName().equalsIgnoreCase("shopad")) {

                if(args[0].equalsIgnoreCase("add")) {
                    ads.addTimer(sender, args);
                }

            }

        }


        return true;
    }




}
