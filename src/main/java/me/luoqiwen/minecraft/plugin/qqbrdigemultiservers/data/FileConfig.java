package me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.data;

import me.luoqiwen.minecraft.plugin.qqbrdigemultiservers.utils.Replacement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface FileConfig
{
    String getString(String path);
    int getInt(String path);
    long getLong(String path);
    boolean getBoolean(String path);
    List<String> getStringList(String path);
    List<Integer> getIntList(String path);
    List<Long> getLongList(String path);
    List<Boolean> getBooleanList(String path);
    default String getString(String path, Replacement... replacements)
    {
        String get = getString(path);
        for (Replacement r : replacements)
        {
            get = get.replaceAll(r.getOriginal(), r.getChangeTo());
        }
        return get;
    }
    default List<String> getStringList(String path, Replacement... replacements)
    {
        List<String> get = getStringList(path);
        List<String> result = new ArrayList<>();
        get.forEach(s ->
        {
            for (Replacement r : replacements)
            {
                s = s.replaceAll(r.getOriginal(), r.getChangeTo());
                result.add(s);
            }
        });
        return result;
    }
    void load() throws IOException;
    void loadAsync();
    void save() throws IOException;
    File getConfigFile();
}
