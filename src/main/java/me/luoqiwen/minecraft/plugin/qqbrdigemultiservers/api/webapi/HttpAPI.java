package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.api.webapi;

import com.alibaba.fastjson.JSONObject;
import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.BungeeCordMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpAPI
{
    public final BungeeCordMain plugin;
    private final String url;
    private static final String encoding = "UTF-8";
    public HttpAPI(BungeeCordMain plugin)
    {
        this.plugin = plugin;
        url = "http://" + plugin.config.getString("URI");
    }

    public String httpPost(String param, UrlArg arg) throws IOException
    {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder builder = new StringBuilder();
        try
        {
            URL realURL = new URL(url + arg);
            HttpURLConnection conn = (HttpURLConnection) realURL.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(5000);

            conn.connect();

            out = new OutputStreamWriter(conn.getOutputStream(), encoding);
            out.write(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
            String line;
            while ((line = in.readLine()) != null)
            {
                builder.append(line);
            }

            conn.disconnect();
        }
        finally
        {
            try
            {
                if (in != null)
                    in.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            try
            {
                if (out != null)
                    out.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

    public JSONObject jsonPost(JSONObject param, UrlArg arg) throws IOException
    {
        return JSONObject.parseObject(httpPost(param.toJSONString(), arg));
    }
}
