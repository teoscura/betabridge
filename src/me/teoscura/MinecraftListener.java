package me.teoscura;

import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

public class MinecraftListener extends PlayerListener {
    private final TeosBetaBridge plugin;
    public MinecraftListener(TeosBetaBridge teosBetaBridge) {
        plugin =  teosBetaBridge;
    }
    @Override
    public void onPlayerChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();
        plugin.dslistener.sendPlayerMessageToDiscord(player.getDisplayName(), message);
    }
    @Override
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        plugin.dslistener.sendPlayerJoinToDiscord(player.getDisplayName());
        plugin.dslistener.updateActivity();
    }
    @Override
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        plugin.dslistener.sendPlayerLeaveToDiscord(player.getDisplayName());
        plugin.dslistener.updateActivity();
    }
    public void sendMsgToPlayers(String msg){
        for(Player p: plugin.getServer().getOnlinePlayers()){
            p.sendMessage(msg);
        }
    }
}
