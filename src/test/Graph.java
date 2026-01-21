package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import test.TopicManagerSingleton.TopicManager;

public class Graph extends ArrayList<Node> {
    public boolean hasCycles() {
        for (int i = 0; i < size(); i++) {
            if (get(i).hasCycles()) {
                return true;
            }
        }
        return false;
    }

    public void createFromTopics() {
        clear();

        TopicManager tm = TopicManagerSingleton.get();
        Map<String, Node> nodes = new HashMap<>();

        for (Topic t : tm.getTopics()) {
            String tn = "T" + t.name;
            nodes.put(tn, new Node(tn));
        }

        for (Topic t : tm.getTopics()) {
            Node tn = nodes.get("T" + t.name);
            List<Agent> subs = t.getSubs();

            for (int i = 0; i < subs.size(); i++) {
                Agent a = subs.get(i);
                String an = "A" + a.getName();
                
                Node anNode = nodes.get(an);
                if (anNode == null) {
                    anNode = new Node(an);
                    nodes.put(an, anNode);
                }

                tn.addEdge(anNode);
            }
        }

        for (Topic t : tm.getTopics()) {
            Node tn = nodes.get("T" + t.name);

            List<Agent> pubs = t.getPubs();
            for (int i = 0; i < pubs.size(); i++) {
                Agent a = pubs.get(i);
                String an = "A" + a.getName();

                Node anNode = nodes.get(an);
                if (anNode == null) {
                    anNode = new Node(an);
                    nodes.put(an, anNode);
                }

                anNode.addEdge(tn);
            }
        }

        for (Node n : nodes.values()) {
            add(n);
        }
    }
}
