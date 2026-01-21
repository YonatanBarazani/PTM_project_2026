package test;

import test.TopicManagerSingleton.TopicManager;

public class IncAgent implements Agent {

    private final String[] subs;
    private final String[] pubs;

    private final Topic topic;
    private final Topic resTopic;

    public IncAgent(String[] subs, String[] pubs) {
        this.subs = subs;
        this.pubs = pubs;

        TopicManager tm = TopicManagerSingleton.get();

        String name = "";
        if (subs !=null && subs.length > 0) {
            name = subs[0];
        }

        String resName = "";
        if (pubs != null && pubs.length > 0) {
            resName = pubs[0];
        }

        this.topic = tm.getTopic(name);
        this.resTopic = tm.getTopic(resName);

        this.topic.subscribe(this);
        this.resTopic.addPublisher(this);
    }

    @Override
    public String getName() {
        return "IncAgent";
    }

    @Override
    public void reset() {

    }

    @Override
    public void callback(String topicName, Message msg) {
        if (msg == null || Double.isNaN(msg.asDouble)) {
            return;
        }

        if (!topicName.equals(topic.name)) {
            return;
        }

        double result = msg.asDouble + 1;
        resTopic.publish(new Message(result));
    }

    @Override
    public void close() {
        
    }
}
