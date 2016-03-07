package hackathon.com.taghit.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seffy on 3/7/2016.
 */
public class GroupsTags {
    private static Map<String, List<String>> groupsToTagsMap = new HashMap<>();

    public static void addGroup(String groupName) {
        groupsToTagsMap.put(groupName, new ArrayList<String>());
    }

    public static void addTag(String groupName, String tagName) {
        groupName = groupName.toLowerCase();
        tagName = tagName.toLowerCase();
        if (!groupsToTagsMap.containsKey(groupName)) {
            groupsToTagsMap.put(groupName, new ArrayList<String>());
        }
        groupsToTagsMap.get(groupName).add(tagName);
    }

    public static List<String> getTags(String groupName) {
        return groupsToTagsMap.get(groupName);
    }

    public static void save() {
        //// TODO: 07/03/2016
    }

    public static void load() {
        addTag("IATI", "hi");
        addTag("iati", "@Hadas");
        //// TODO: 07/03/2016
    }
}
