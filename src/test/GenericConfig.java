package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GenericConfig implements Config {

    private String confFile;
    private final List<Agent> agents = new ArrayList<>();

    public void setConfFile(String fileName) {
        this.confFile = fileName;
    }

    @Override
    public void create() {
        agents.clear();

        if (confFile == null) {
            return;
        }

        List<String> lines = readLines(confFile);
        if (lines.size() % 3 != 0) {
            return;
        }

        for (int i = 0; i < lines.size(); i += 3) {
            String className = lines.get(i).trim();
            String subsLine = lines.get(i + 1).trim();
            String pubsLine = lines.get(i + 2).trim();

            String[] subs = split(subsLine);
            String[] pubs = split(pubsLine);

            Agent agent = createAgent(className, subs, pubs);
            if (agent == null) {
                continue;
            }

            Agent wrapped = new ParallelAgent(agent);
            agents.add(wrapped);
        }
    }

    @Override
    public String getName() {
        return "Generic Config";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void close() {
        for (int i = 0; i < agents.size(); i++) {
            agents.get(i).close();
        }
        agents.clear();
    }

    private List<String> readLines(String fileName) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {

        }

        return lines;
    }

    private String[] split(String line) {
        if (line.length() == 0) {
            return new String[0];
        }
        String[] items = line.split(",");
        for (int i = 0; i < items.length; i++) {
            items[i] = items[i].trim();
        }
        return items;
    }

    private Agent createAgent(String className, String[] subs, String[] pubs) {
        try {
            Class<?> c = Class.forName(className);
            java.lang.reflect.Constructor<?> ctor = c.getConstructor(String[].class, String[].class);
            Object obj = ctor.newInstance(subs, pubs);

            return (Agent) obj;
        } catch (Exception e) {
            return null;
        }
    }
}
