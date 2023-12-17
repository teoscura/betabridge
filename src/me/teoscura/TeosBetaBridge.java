package me.teoscura;


import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;


public class TeosBetaBridge extends JavaPlugin {

    public DiscordListener dslistener;
    public final MinecraftListener mclistener = new MinecraftListener(this);
    public boolean configured = false;
    public String loadedID;
    public String loadedToken;

    @Override
    public void onDisable() {
        if(dslistener.getBOT_TOKEN()!=null) {
            saveConfig("config/betabridge.txt");
        }
        System.out.println("BetaBridge is Disabled!");
    }
    @Override
    public void onEnable() {
        loadConfig("config/betabridge.txt");
        if(configured){
            PluginManager pm = getServer().getPluginManager();
            try {
                dslistener = new DiscordListener(this);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            pm.registerEvent(Event.Type.PLAYER_CHAT, mclistener, Event.Priority.Normal, this);
            pm.registerEvent(Event.Type.PLAYER_QUIT, mclistener, Event.Priority.Normal, this);
            pm.registerEvent(Event.Type.PLAYER_JOIN, mclistener, Event.Priority.Normal, this);
            getCommand("betabridge").setExecutor(new CommandListener(this));
            System.out.println("BetaBridge is Enabled!");
        }
        else {
            System.out.println("BetaBridge needs to be configured, look for the guide on github!");
            try {
                dslistener = new DiscordListener(this);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            getCommand("betabridge").setExecutor(new SetupHelper(this));
        }
    }

    public void saveConfig(String path){
        try{
            FileWriter writer = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(dslistener.getChannelId());
            bw.newLine();
            bw.write(dslistener.getBOT_TOKEN());
            bw.close();
        } catch (IOException e){
            System.out.println("Configuration file couldn't be written to!");
        }
    }

    public void loadConfig(String path){
        try{
            FileReader reader = new FileReader(path);
            BufferedReader br = new BufferedReader(reader);
            loadedID = br.readLine();
            loadedToken = br.readLine();
            configured = true;
        } catch (IOException e){
            System.out.println("Configuration file couldn't be found, please do /betabridge setid [id] and settoken [token] to configure a value for it!");
            configured = false;
        }
    }
}
