package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.botapi;

public enum BotRequestType
{
    GroupMessage, FriendMessage, TempMessage, GetSessionKey;

    public static BotRequestType toType(String type)
    {
        switch (type)
        {
            case "GroupMessage" : return GroupMessage;
            case "FriendMessage" : return FriendMessage;
            case "TempMessage" : return TempMessage;
            case "GetSessionKey" : return GetSessionKey;
            default: return null;
        }
    }

    @Override
    public String toString()
    {
        return name();
    }
}
