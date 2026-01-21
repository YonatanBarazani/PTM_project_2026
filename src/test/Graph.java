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
            String nodeName = "T" + t.name;
            nodes.put(nodeName, new Node(nodeName));
        }

        for (Topic t : tm.getTopics()) {
            Node topicNode = nodes.get("T" + t.name);
            List<Agent> subs = t.getSubs();

            for (int i = 0; i < subs.size(); i++) {
                Agent agent = subs.get(i);
                String nodeName = "A" + agent.getName();

                Node agentNode = nodes.get(nodeName);
                if (agentNode == null) {
                    agentNode = new Node(nodeName);
                    nodes.put(nodeName, agentNode);
                }

                topicNode.addEdge(agentNode);
            }
        }

        for (Topic t : tm.getTopics()) {
            Node topicNode = nodes.get("T" + t.name);
            List<Agent> pubs = t.getPubs();

            for (int i = 0; i < pubs.size(); i++) {
                Agent agent = pubs.get(i);
                String nodeName = "A" + agent.getName();

                Node agentNode = nodes.get(nodeName);
                if (agentNode == null) {
                    agentNode = new Node(nodeName);
                    nodes.put(nodeName, agentNode);
                }

                agentNode.addEdge(topicNode);
            }
        }

        for (Node n : nodes.values()) {
            add(n);
        }
    }
}
