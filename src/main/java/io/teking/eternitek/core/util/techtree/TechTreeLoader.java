package io.teking.eternitek.core.util.techtree;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

public class TechTreeLoader {

    private static final Gson GSON = new Gson();

    public static Map<String, TechNodeData> loadTechTree(ResourceManager resourceManager, String techTreeName) {

        Identifier resourceId = Identifier.of("eternitek", "tech_trees/" + techTreeName + ".json");
        try (InputStream inputStream = resourceManager.getResource(resourceId).get().getInputStream()) {
            InputStreamReader reader = new InputStreamReader(inputStream);
            Type type = new TypeToken<Map<String, TechNodeData>>(){}.getType();
            return GSON.fromJson(reader, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
