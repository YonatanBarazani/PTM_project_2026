package test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Node {
    private String name;
    private List<Node> edges;
    private Message msg;

    public Node(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
        this.msg = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getEdges() {
        return edges;
    }

    public void setEdges(List<Node> edges) {
        if (edges == null) {
            this.edges = new ArrayList<>();
        }
        
        else {
            this.edges = edges;
        }
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public void addEdge(Node n) {
        if (n == null) {
            return;
        }
        edges.add(n);
    }

    public boolean hasCycles() {
        return search(this, new HashSet<Node>());
    }

    private boolean search(Node start, HashSet<Node> route) {
        if (route.contains(this)) {
            return this == start;
        }

        route.add(this);

        for (int i = 0; i < edges.size(); i++) {
            Node n = edges.get(i);
            if (n.search(start, route)) {
                return true;
            }
        }

        route.remove(this);
        return false;
    }
}
