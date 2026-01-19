package test;

public class TopicManagerSingleton {

    public static class TopicManager {
        private static final TopicManager instance = new TopicManager();

        private final java.util.Map<String, Topic> topics = new java.util.HashMap<>();

        private TopicManager() {}

        public Topic getTopic(String name) {
            Topic t = topics.get(name);
            if (t == null) {
                t = new Topic(name);
                topics.put(name, t);
            }
            return t;
        }

        public java.util.Collection<Topic> getTopics() {
            return topics.values();
        }

        public void clear() {
            topics.clear();
        }
    }

    public static TopicManager get() {
        return TopicManager.instance;
    }
}
