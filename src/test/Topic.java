package test;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    public final String name;

    private final List<Agent> subs = new ArrayList<>();
    private final List<Agent> pubs = new ArrayList<>();

    Topic(String name){
        this.name = name;
    }

    public void subscribe(Agent agent){
        if (agent == null || subs.contains(agent)) {
            return;
        }
        subs.add(agent);
    }

    public void unsubscribe(Agent agent){
        subs.remove(agent);
    }

    public void publish(Message msg){
        for (int i = 0; i < subs.size(); i++) {
            subs.get(i).callback(this.name, msg);
        }
    }

    public void addPublisher(Agent agent){
        if (agent == null || pubs.contains(agent)) {
            return;
        }
        pubs.add(agent);
    }

    public void removePublisher(Agent agent){
        pubs.remove(agent);
    }

    public List<Agent> getSubs(){
        return subs;
    }

    public List<Agent> getPubs(){
        return pubs;
    }
}
