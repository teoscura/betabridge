package me.teoscura;

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupHelper implements CommandExecutor {
    public TeosBetaBridge plugin;
    public SetupHelper(TeosBetaBridge teosBetaBridge) {
        plugin = teosBetaBridge;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        switch(strings.length){
            case 0:
                player.sendMessage(ChatColor.RED+"The command requires a subcommand! Available subcommands: ");
                player.sendMessage(ChatColor.RED+"- setid: Change the value of the channel-id.");
                player.sendMessage(ChatColor.RED+"- settoken: Gather information on current session.");
                break;
            case 1:
                if(strings[0].equals("setid")){
                    player.sendMessage(ChatColor.RED+"A value to be set is needed!");
                }
                else if(strings[0].equals("settoken")){
                    plugin.dslistener.channel = (MessageChannelUnion) plugin.dslistener.api.getTextChannelById(plugin.dslistener.getChannelId());
                    player.sendMessage(ChatColor.RED+"A value to be set is needed!");
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
                else if(strings[0].equals("settoken")){
                    if(strings[1].length()>71) {
                        plugin.dslistener.setBOT_TOKEN(strings[1]);
                    }
                    else{
                        player.sendMessage(ChatColor.RED+"Invalid subcommand argument combination.");
                    }
                }
                break;
            default:
                player.sendMessage(ChatColor.RED+"Invalid subcommand argument combination.");
                break;
        }
        return true;
    }
}
