package me.teoscura;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.ChatColor;

import javax.annotation.Nonnull;

public class DiscordListener extends ListenerAdapter {
    private String BOT_TOKEN;
    private String channelId;
    public TeosBetaBridge plugin;
    public MessageChannelUnion channel;
    public JDA api;

    public DiscordListener(TeosBetaBridge teosBetaBridge) throws InterruptedException {
        plugin = teosBetaBridge;
        if(plugin.configured){
            setBOT_TOKEN(plugin.loadedToken);
            setChannelId(plugin.loadedID);
            api = JDABuilder.createLight(BOT_TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(this)
                    .build().awaitReady();
            channel = (MessageChannelUnion) api.getTextChannelById(channelId);
        }
        else{
            System.out.println("No config was setup, please configure it using /betabridge");
        }
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event){
        User sender = event.getAuthor();
        Message message = event.getMessage();

        if(event.isFromGuild()&&message.getChannelId().equals(channelId)&&!sender.isBot()){
            handleMessage(sender, message);
        }
    }
    public void updateActivity(){
        api.getPresence().setPresence(OnlineStatus.ONLINE, Activity.customStatus("["+plugin.getServer().getOnlinePlayers().length+" Buddies Online]"));
    }
    public void sendPlayerMessageToDiscord(String playername, String messagecontent){
        channel = (MessageChannelUnion) api.getTextChannelById(channelId);
        channel.sendMessage("`<"+playername+"> "+messagecontent+"`").queue();
    }
    public void sendPlayerLeaveToDiscord(String playername){
        channel = (MessageChannelUnion) api.getTextChannelById(channelId);
        channel.sendMessage("**`"+playername+" has left Beta Buddies`**").queue();
    }
    public void sendPlayerJoinToDiscord(String playername){
        channel = (MessageChannelUnion) api.getTextChannelById(channelId);
        channel.sendMessage("**`"+playername+" has joined Beta Buddies`**").queue();
    }
    public String getChannelId(){
        return channelId;
    }
    public void setChannelId(String str){
        channelId = str;
    }
    public String getBOT_TOKEN() {
        return BOT_TOKEN;
    }
    public void setBOT_TOKEN(String s) {
        BOT_TOKEN = s;
    }
    public void handleMessage(User sender, Message message){
        String basemsg = "["+ ChatColor.DARK_AQUA+"Discord"+ChatColor.WHITE+"] ";
        int remaining = 128;
        if(message.getReferencedMessage()!=null){
            basemsg = basemsg +ChatColor.AQUA+"-Reply to @" +message.getReferencedMessage().getAuthor().getEffectiveName()+"- "+ChatColor.WHITE;
        }
        basemsg = basemsg + sender.getEffectiveName() + ": ";
        if(message.getContentDisplay().length()>(remaining-basemsg.length())){
            basemsg = basemsg + message.getContentDisplay().substring(0, Math.min(message.getContentDisplay().length(),(remaining-basemsg.length())));
            basemsg = basemsg + ChatColor.YELLOW+"*message was trimmed*";
        }
        else{
            basemsg = basemsg + message.getContentDisplay();
        }
        if(message.getAttachments().size()==1){
            basemsg = basemsg + ChatColor.AQUA+" -Discord Attachment-";
        }
        if(message.getAttachments().size()>1){
            basemsg = basemsg + ChatColor.AQUA+" -"+ChatColor.YELLOW+message.getAttachments().size()+ChatColor.AQUA+" Discord Attachments-";
        }
        plugin.mclistener.sendMsgToPlayers(basemsg);
    }

}

