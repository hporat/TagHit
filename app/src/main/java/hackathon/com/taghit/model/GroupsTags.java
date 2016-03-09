package hackathon.com.taghit.model;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by seffy on 3/7/2016.
 */
public class GroupsTags {
    private static Map<String, List<String>> groupsToTagsMap = new LinkedHashMap<>();

    public static void addGroup(String groupName) {
        if(!groupsToTagsMap.containsKey(groupName)) groupsToTagsMap.put(groupName, new ArrayList<String>());
        GroupsTags.save();
    }

    public static void addTag(String groupName, String tagName) {
        tagName = tagName.toLowerCase();
        if (!groupsToTagsMap.containsKey(groupName)) {
            groupsToTagsMap.put(groupName, new ArrayList<String>());
        }
        groupsToTagsMap.get(groupName).add(tagName);
        GroupsTags.save();
    }

    public static List<String> getTags(String groupName) {
        // if load wasnt read
        if(groupsToTagsMap.isEmpty()) GroupsTags.load();
        if(!groupsToTagsMap.containsKey(groupName)) {
            addGroup(groupName);
        }
        return groupsToTagsMap.get(groupName);
    }

    public static List<String> getGroups()
    {
        Set<String> groups = groupsToTagsMap.keySet();
        List<String> grpList = new ArrayList<String>(groups.size());
        grpList.addAll(groups);
        return grpList;
    }

    public static void save() {
        //// TODO: 07/03/2016
        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.MEDIA_SHARED);
            FileOutputStream fout = new FileOutputStream(path.getAbsolutePath()+"//groups.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(groupsToTagsMap);
            oos.close();
            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        addTag("Test", "@seffy");
        addTag("IATI", "@Hadas");

        //// TODO: 07/03/2016
        try
        {
            File path = Environment.getExternalStoragePublicDirectory(Environment.MEDIA_SHARED);
            FileInputStream fileIn = new FileInputStream(path.getAbsolutePath()+"//groups.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            groupsToTagsMap = (Map<String, List<String>>) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
    }
}
