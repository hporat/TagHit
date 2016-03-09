package hackathon.com.taghit.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
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
    public static Map<String, SerializableBitmap> groupsToIconMap = new LinkedHashMap<>();

    public static void addGroup(String groupName,Bitmap icon) {
        if(groupName==null || groupName.trim().isEmpty()) return;
        if(!groupsToTagsMap.containsKey(groupName)) {
            groupsToTagsMap.put(groupName, new ArrayList<String>());
            groupsToIconMap.put(groupName,new SerializableBitmap(icon));
        }
        //groupsToIconMap.put(groupName,new SerializableBitmap(icon));
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
        try {
            File path = Environment.getExternalStorageDirectory();
            FileOutputStream fout = new FileOutputStream(path.getAbsolutePath()+"//groups.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            //groupsToTagsMap = new LinkedHashMap<>();
            oos.writeObject(groupsToTagsMap);
            oos.close();

            FileOutputStream foutIcon = new FileOutputStream(path.getAbsolutePath()+"//icon.ser");
            ObjectOutputStream oosIcon = new ObjectOutputStream(foutIcon);
            oosIcon.writeObject(groupsToIconMap);
            oosIcon.close();
            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try
        {
            File path = Environment.getExternalStorageDirectory();
            FileInputStream fileIn = new FileInputStream(path.getAbsolutePath()+"//groups.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            groupsToTagsMap = (Map<String, List<String>>) in.readObject();
            in.close();
            fileIn.close();

            FileInputStream fileIn1 = new FileInputStream(path.getAbsolutePath()+"//icon.ser");
            ObjectInputStream in1 = new ObjectInputStream(fileIn1);
            groupsToIconMap = (Map<String, SerializableBitmap>) in1.readObject();
            in1.close();
            fileIn1.close();
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
