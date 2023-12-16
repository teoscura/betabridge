package me.teoscura;

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandListener implements CommandExecutor {
    public TeosBetaBridge plugin;
    public CommandListener(TeosBetaBridge teosBetaBridge){
        plugin = teosBetaBridge;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        switch(strings.length){
            case 0:
                player.sendMessage(ChatColor.RED+"The command requires a subcommand! Available subcommands: ");
                player.sendMessage(ChatColor.RED+"- setid: Change the value of the channel-id.");
                player.sendMessage(ChatColor.RED+"- info: Gather information on current session.");
                break;
            case 1:
                if(strings[0].equals("setid")){
                    player.sendMessage(ChatColor.RED+"A value to be set is needed!");
                }
                else if(strings[0].equals("info")){
                    plugin.dslistener.channel = (MessageChannelUnion) plugin.dslistener.api.getTextChannelById(plugin.dslistener.getChannelId());
                    player.sendMessage(ChatColor.DARK_GREEN+">"+ChatColor.GREEN+"Server Name: "+plugin.dslistener.channel.asGuildMessageChannel().getGuild().getName());
                    player.sendMessage(ChatColor.DARK_GREEN+">"+ChatColor.GREEN+"Channel Name: "+plugin.dslistener.channel.getName());
                    player.sendMessage(ChatColor.DARK_GREEN+">"+ChatColor.GREEN+"Channel ID: "+plugin.dslistener.channel.getId());
                }
                else {
                    player.sendMessage(ChatColor.RED+"Invalid subcommand.");
                }
                break;
            case 2:
                if(strings[0].equals("setid")){
                    if(strings[1].length()>18) {
                        try {
                            long l = Long.parseLong(strings[1]);
                            plugin.dslistener.setChannelId(strings[1]);
                        } catch (NumberFormatException e) {
                            player.sendMessage(ChatColor.RED + "Invalid Channel ID.");
                        }
                    }
                    else{
                        player.sendMessage(ChatColor.RED+"Invalid subcommand argument combination.");
                    }
                }
                else{
                    player.sendMessage(ChatColor.RED+"Invalid subcommand argument combination.");
                }
                break;
            default:
                player.sendMessage(ChatColor.RED+"Invalid subcommand argument combination.");
                break;
        }
        return true;
    }
}
