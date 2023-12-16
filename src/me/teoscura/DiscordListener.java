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
import javax.annotation.Nonnull;

public class DiscordListener extends ListenerAdapter {
    private final String BOT_TOKEN = "no";
    private String channelId;
    public TeosBetaBridge plugin;
    public MessageChannelUnion channel;
    public JDA api = JDABuilder.createLight(BOT_TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
            .addEventListeners(this)
            .build();
    public DiscordListener(TeosBetaBridge teosBetaBridge) {
        plugin = teosBetaBridge;
        if(channelId!=null){
            channel = (MessageChannelUnion) api.getTextChannelById(channelId);
        }
        else{
            System.out.println("No channel id was setup, waiting on config load.");
        }
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event){
        User sender = event.getAuthor();
        Message message = event.getMessage();
        MessageChannelUnion channel = event.getChannel();

        if(event.isFromGuild()&&message.getChannelId().equals(channelId)&&!sender.isBot()){
            if(message.getAttachments().isEmpty()){
                plugin.mclistener.sendTextMsg(sender.getEffectiveName(), message.getContentDisplay());
            }
            else{
                plugin.mclistener.sendAttachmentMsg(sender.getEffectiveName()); //might be wrong method call
            }
        }
    }

    public void updateActivity(){
        api.getPresence().setPresence(OnlineStatus.ONLINE, Activity.customStatus("["+plugin.getServer().getOnlinePlayers().length+" Buddies Online]"));
    }

    public void sendMessageToDiscord(String playername, String messagecontent){
        channel = (MessageChannelUnion) api.getTextChannelById(channelId);
        channel.sendMessage("`<"+playername+"> "+messagecontent+"`").queue();
    }

    public String getChannelId(){
        return channelId;
    }
    public void setChannelId(String str){
        channelId = str;
    }
}
