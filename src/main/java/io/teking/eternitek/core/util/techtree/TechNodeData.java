package io.teking.eternitek.core.util.techtree;

public class TechNodeData {
    public String parent_connection;
    public String item;
    public int size;
    public Position position;
    public String icon;

    public static class Position {
        public int x;
        public int y;
    }
}

