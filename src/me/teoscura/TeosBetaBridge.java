package me.teoscura;


import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;


public class TeosBetaBridge extends JavaPlugin {

    public final DiscordListener dslistener = new DiscordListener(this);
    public final MinecraftListener mclistener = new MinecraftListener(this);
    @Override
    public void onDisable() {
        saveConfig("config/betabridge.txt");
        System.out.println("BetaBridge is Disabled!");
    }
    @Override
    public void onEnable() {
        loadConfig("config/betabridge.txt");
        PluginManager pm = getServer().getPluginManager();
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){public void run(){dslistener.updateActivity();}}, 0L, 1200L);
        pm.registerEvent(Event.Type.PLAYER_CHAT, mclistener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, mclistener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_JOIN, mclistener, Event.Priority.Normal, this);
        getCommand("betabridge").setExecutor(new CommandListener(this));
        System.out.println("BetaBridge is Enabled!");
    }

    public void saveConfig(String path){
        try{
            FileWriter writer = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(dslistener.getChannelId());
            bw.close();
        } catch (IOException e){
            System.out.println("Configuration file couldn't be written to!");
        }
    }

    public void loadConfig(String path){
        try{
            FileReader reader = new FileReader(path);
            BufferedReader br = new BufferedReader(reader);
            dslistener.setChannelId(br.readLine());
        } catch (IOException e){
            System.out.println("Configuration file couldn't be found, please do /betabridge setid [id] to configure a value for it!");
        }
    }
}
