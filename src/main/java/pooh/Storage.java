package pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Storage {
    private ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> storage = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    public void postTopic(String topic, String text) {
        addToStore(storage, topic, text);
    }

    public String getFromTopic(String topic) {
        if (storage.containsKey(topic)) {
            return getFromStore(storage, topic);
        } else {
            return "";
        }
    }

    public void addToQueue(String request, String text) {
        addToStore(queue, request, text);
    }

    public String getFromQueue(String request) {
        return getFromStore(queue, request);
    }

    private String getFromStore(ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map,
                                String request) {
        String rsl = map.get(request).poll();
        return rsl != null ? rsl : ",: sorry, the information is absent";
    }

    private void addToStore(ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map,
                            String request, String text) {
        if (!map.containsKey(request)) {
            ConcurrentLinkedQueue<String> temp = new ConcurrentLinkedQueue<>();
            temp.add(text);
            map.put(request, temp);
        } else {
            map.get(request).offer(text);
        }
    }

    public ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> getStorage() {
        return storage;
    }

    public ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> getQueue() {
        return queue;
    }
}
