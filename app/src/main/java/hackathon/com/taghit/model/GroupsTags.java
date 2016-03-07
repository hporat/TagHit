package hackathon.com.taghit.model;

import android.content.Context;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seffy on 3/7/2016.
 */
public class GroupsTags {
    private static Map<String, List<String>> groupsToTagsMap = new HashMap<>();

    public static void addGroup(String groupName) {
        if(!groupsToTagsMap.containsKey(groupName.toLowerCase())) groupsToTagsMap.put(groupName.toLowerCase(), new ArrayList<String>());
        GroupsTags.save();
    }

    public static void addTag(String groupName, String tagName) {
        groupName = groupName.toLowerCase();
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

    public static void save() {
        //// TODO: 07/03/2016
        /*String filename = "myfile";
        String string = "Hello world!";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static void load() {
        addTag("IATI", "hi");
        addTag("iati", "@Hadas");
        //// TODO: 07/03/2016
    }
}
