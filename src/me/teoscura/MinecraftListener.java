package me.teoscura;

import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.entity.Player;

public class MinecraftListener extends PlayerListener {
    private final TeosBetaBridge plugin;
    public MinecraftListener(TeosBetaBridge teosBetaBridge) {
        plugin =  teosBetaBridge;
    }

    @Override
    public void onPlayerChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();
        plugin.dslistener.sendMessageToDiscord(player.getDisplayName(), message);
    }

    public void sendTextMsg(String msgsender, String strmsg){
        for(Player p: plugin.getServer().getOnlinePlayers()){
            p.sendMessage("["+ ChatColor.DARK_AQUA+"Discord"+ChatColor.WHITE+"] "+msgsender+ ": "+strmsg);
        }
    }
    public void sendAttachmentMsg(String msgsender){
        for(Player p: plugin.getServer().getOnlinePlayers()){
            p.sendMessage("["+ChatColor.DARK_AQUA+"Discord"+ChatColor.WHITE+"] "+msgsender+": "+ChatColor.AQUA+"-Discord Attachment-");

        }
    }
}
