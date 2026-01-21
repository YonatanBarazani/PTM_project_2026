package test;

import test.TopicManagerSingleton.TopicManager;

public class PlusAgent implements Agent {

    private final String[] subs;
    private final String[] pubs;

    private final Topic aTopic;
    private final Topic bTopic;
    private final Topic resTopic;

    private double aVal;
    private double bVal;

    private boolean aReady;
    private boolean bReady;

    public PlusAgent(String[] subs, String[] pubs) {
        this.subs = subs;
        this.pubs = pubs;

        TopicManager tm = TopicManagerSingleton.get();

        String aName = "";
        String bName = "";

        if (subs != null) {
            if (subs.length > 0) {
                aName = subs[0];
            }
            if (subs.length > 1) {
                bName = subs[1];
            }
        }

        String resName = "";
        if (pubs != null && pubs.length > 0) {
            resName = pubs[0];
        }

        this.aTopic = tm.getTopic(aName);
        this.bTopic = tm.getTopic(bName);
        this.resTopic = tm.getTopic(resName);

        this.aTopic.subscribe(this);
        this.bTopic.subscribe(this);
        this.resTopic.addPublisher(this);

        reset();
    }

    @Override
    public String getName() {
        return "PlusAgent";
    }

    @Override
    public void reset() {
        aVal = 0;
        bVal = 0;
        aReady = false;
        bReady = false;
    }

    @Override
    public void callback(String topic, Message msg) {
        if (msg == null || Double.isNaN(msg.asDouble)) {
            return;
        }

        if (topic.equals(aTopic.name)) {
            aVal = msg.asDouble;
            aReady = true;
        }
        else if (topic.equals(bTopic.name)) {
            bVal = msg.asDouble;
            bReady = true;
        }
        else {
            return;
        }

        if (aReady && bReady) {
            double sum = aVal + bVal;
            resTopic.publish(new Message(sum));
        }
    }

    @Override
    public void close() {
    }
}
