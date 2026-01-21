package test;

import java.util.function.BinaryOperator;
import test.TopicManagerSingleton.TopicManager;

public class BinOpAgent implements Agent {
    private final String name;

    private final Topic a;
    private final Topic b;
    private final Topic res;

    private final BinaryOperator<Double> op;

    private boolean aReady;
    private boolean bReady;

    private double aVal;
    private double bVal;

    public BinOpAgent(String name, String aTopic, String bTopic, String resTopic, BinaryOperator<Double> op) {
        this.name = name;
        this.op = op;
        
        TopicManager tm = TopicManagerSingleton.get();

        this.a = tm.getTopic(aTopic);
        this.b = tm.getTopic(bTopic);
        this.res = tm.getTopic(resTopic);

        this.a.subscribe(this);
        this.b.subscribe(this);
        this.res.addPublisher(this);

        reset();
    }

    @Override
    public String getName() {
        return name;
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
        if (msg == null) {
            return;
        }

        if (Double.isNaN(msg.asDouble)) {
            return;
        }

        if (topic.equals(a.name)) {
            aVal = msg.asDouble;
            aReady = true;
        }

        else if (topic.equals(b.name)) {
            bVal = msg.asDouble;
            bReady = true;
        }
        
        else {
            return;
        }

        if (aReady && bReady) {
            double val = op.apply(aVal, bVal);
            res.publish(new Message(val));
        }
    }

    @Override
    public void close() {
    }
}
