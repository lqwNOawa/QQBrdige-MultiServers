package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.events.bungee;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.md_5.bungee.api.plugin.Event;

public class MessageReceivedEvent extends Event
{
    private final JSONObject received;
    private final MessageType type;
    private final sender sender;
    private final messageChain message;

    public MessageReceivedEvent(String msg)
    {
        this.received = JSONObject.parseObject(msg);
        type = MessageType.toType(received.getString("type"));
        sender = new sender(received);
        message = new messageChain(received);
    }

    public JSONObject getReceivedJson()
    {
        return received;
    }

    public messageChain getMessageChain()
    {
        return message;
    }

    public MessageType getType()
    {
        return type;
    }

    public sender getSender()
    {
        return sender;
    }

    public static class messageChain
    {
        public final JSONArray array;
        public String text = "";
        public messageChain(JSONObject received)
        {
            array = received.getJSONArray("messageChain");
            array.forEach(o ->
            {
                JSONObject jso = (JSONObject) o;
                String type = jso.getString("type");
                if (type.equals("Plain"))
                {
                    text += jso.getString("text");
                }
                else if (type.equals("Source"))
                    text += "";
                else
                    text += "[" + type.toLowerCase() + "]";
            });
            if (text.equals(""))
                text = "[emoji]";
        }
    }
    public static class sender
    {
        public final JSONObject senderJson;
        public final JSONObject groupJson;
        public final long senderId;
        public final String senderName;
        public final PermissionType senderPermission;
        public final long groupId;
        public final String groupName;
        public final PermissionType botPermission;

        public sender(JSONObject received)
        {
            senderJson = received.getJSONObject("sender");
            groupJson = senderJson.getJSONObject("group");
            senderId = senderJson.getLongValue("id");
            senderName = senderJson.getString("memberName");
            senderPermission = PermissionType.toPermission(senderJson.getString("permission"));
            groupId = groupJson.getLongValue("id");
            groupName = groupJson.getString("name");
            botPermission = PermissionType.toPermission(groupJson.getString("permission"));
        }
    }

    public enum MessageType
    {
        GroupMessage, FriendMessage, TempMessage;

        @Override
        public String toString()
        {
            return name();
        }

        public static MessageType toType(String type)
        {
            switch (type)
            {
                case "GroupMessage" : return GroupMessage;
                case "FriendMessage": return FriendMessage;
                case "TempMessage" : return TempMessage;
                default: return null;
            }
        }
    }
    public enum PermissionType
    {
        OWNER, ADMINISTRATOR, MEMBER;

        @Override
        public String toString()
        {
            return name();
        }

        public static PermissionType toPermission(String type)
        {
            switch (type)
            {
                case "OWNER" : return OWNER;
                case "ADMINISTRATOR" : return ADMINISTRATOR;
                case "MEMBER" : return MEMBER;
                default: return null;
            }
        }
    }
}
