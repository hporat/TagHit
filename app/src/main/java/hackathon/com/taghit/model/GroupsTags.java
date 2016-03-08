package hackathon.com.taghit.model;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static List<String> getGroups()
    {
        Set<String> groups = groupsToTagsMap.keySet();
        List<String> grpList = new ArrayList<String>(groups.size());
        grpList.addAll(groups);
        return grpList;
    }

    public static void save() {
        //// TODO: 07/03/2016
    }

    public static void load() {
        addTag("IATI", "hi, ddd ddd,");
        addTag("iati", "@Hadas");
        addTag("dIATI", "hi");
        addTag("fiati", "@Hadas");
        addTag("gIATI", "hi");
        addTag("hiati", "@Hadas");
        addTag("jIATI", "hi");
        addTag("jiati", "@Hadas");
        addTag("jIATI", "hi");
        addTag("jiati", "@Hadas");
        addTag("fIATI", "hi");
        addTag("liati", "@Hadas");
        addTag("kIATI", "hi");
        addTag("uiati", "@Hadas");
        addTag("kuku", "@Hadas");
        addTag("muku", "@Hadas");
        addTag("kuku2", "@Hadas");
        addTag("kuku3", "@Hadas");
        addTag("kuku", "@Hadas");
        //// TODO: 07/03/2016
    }
}
