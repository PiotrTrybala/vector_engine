package pl.piotrtrybala.database.security.permissions;

import java.util.List;

public class Node {

    private final boolean isGroup;
    private final String segment;

    private List<Node> children;

    public Node(String segment, boolean isGroup) {
        this.segment = segment;
        this.isGroup = isGroup;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public String getSegment() {
        return segment;
    }

    public List<Node> getGroup(String prefix) {
        return null;
    }

    public Node getNode(String permission) {
        return null;
    }
}
