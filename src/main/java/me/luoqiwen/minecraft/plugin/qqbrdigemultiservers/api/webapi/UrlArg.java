package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.webapi;

public enum UrlArg
{
    AUTH("/auth"),
    VERIFY("/verify"),
    RELEASE("/release"),
    SendFriendMsg("/sendFriendMessage"),
    SendTempMsg("/sendTempMessage"),
    SendGroupMsg("/sendGroupMessage"),
    RECALL("/recall"),
    MUTE("/mute"),
    UnMUTE("/unmute"),
    KICK("/kick"),
    QUIT("/quit"),
    MUTEAll("/muteAll"),
    UnMUTEAll("/unmuteAll"),
    ConfGroupConfig("/groupConfig"),
    ConfMemberINFO("/memberInfo");
    final String arg;
    UrlArg(String arg)
    {
        this.arg = arg;
    }

    public String toString()
    {
        return arg;
    }

    public static String WsMsgChannel(String sessionKey)
    {
        return "/message?sessionKey=" + sessionKey;
    }
    public static String  WsEventChannel(String sessionKey)
    {
        return "/event?sessionKey=" + sessionKey;
    }
    public static String  WsAllChannel(String sessionKey)
    {
        return "/all?sessionKey=" + sessionKey;
    }
    public static String FriendList(String sessionKey)
    {
        return "/friendList?sessionKey=" + sessionKey;
    }
    public static String GroupList(String sessionKey)
    {
        return "/groupList?sessionKey=" + sessionKey;
    }
    public static String MemberList(String sessionKey, long target)
    {
        return "/memberList?sessionKey=" + sessionKey + "&target=" + target;
    }

    public static String GetConfig(String sessionKey, long target)
    {
        return "/groupConfig?sessionKey=" + sessionKey + "&target=" + target;
    }
    public static String GetMemberINFO(String sessionKey, long target)
    {
        return "/memberInfo?sessionKey=" + sessionKey + "&target=" + target;
    }
}
