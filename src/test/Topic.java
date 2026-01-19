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

    public void subscribe(Agent a){
        if (a == null || subs.contains(a)) {
            return;
        }
        subs.add(a);
    }

    public void unsubscribe(Agent a){
        subs.remove(a);
    }

    public void publish(Message msg){
        for (int i = 0; i < subs.size(); i++) {
            subs.get(i).callback(this.name, msg);
        }
    }

    public void addPublisher(Agent a){
        if (a == null || pubs.contains(a)) {
            return;
        }
        pubs.add(a);
    }

    public void removePublisher(Agent a){
        pubs.remove(a);
    }
}
