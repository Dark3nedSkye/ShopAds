package com.dxrkenedsky.playershopads;

import net.milkbowl.vault.economy.Economy;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.logging.Logger;


public class TimedAd {

    Logger log = Bukkit.getLogger();

    private PlayerShopAds plugin;

    public ArrayList<String> messages = new ArrayList<>();

    public Economy eco;
    public int price;


    public TimedAd(Economy eco, int price) {

        this.eco = eco;
        this.price = price;

    }

    public void TimerCount(String msg) {

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {

                Bukkit.broadcastMessage(msg);

            }
        }, 20L);





    }

    public void addTimer(CommandSender sender, String[] args) {

       Player player = (Player) sender;

        double bal = plugin.econ.getBalance(player);
        int count = 0;

        if(bal > price) {

            eco.withdrawPlayer(player, price);
            String[] arr = (String[]) ArrayUtils.remove(args, 0);

            String msg = String.join(" ", arr);
            messages.add(msg);


            while(count < 72) {
                TimerCount(msg);
                count++;
                log.info(player.getName() + " Sent the ad: " + msg + " (DEBUG, Message number " + count + ") ");
            }
        }

    }



}
