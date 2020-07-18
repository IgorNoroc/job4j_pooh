package pooh;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Storage Topics.
 * task : Техническое задание - проект "Pooh JMS" [#318329].
 */
public class Topics {
    private final ConcurrentHashMap<String, Queue<String>> topics = new ConcurrentHashMap<>();

    /**
     * Add text or topic to storage queue.
     *
     * @param topic request.
     * @param text    text.
     */
    public void postTopic(String topic, String text) {
        addToStore(topic, text);
    }

    /**
     * Get topic from storage queue.
     *
     * @param topic request.
     * @return text or nothing.
     */
    public String getFromTopic(String topic) {
        if (topics.containsKey(topic)) {
            return getFromStore(topic);
        } else {
            return "";
        }
    }

    /**
     * Checking if queue contains request.
     *
     * @param request request.
     * @return text from queue or system text.
     */
    private String getFromStore(String request) {
        String rsl = topics.get(request).poll();
        return rsl != null ? rsl : ",: sorry, the information is absent";
    }

    /**
     * Checking if queue contains request and add it to queue.
     *
     * @param request request.
     * @param text    text.
     */
    private void addToStore(String request, String text) {
        if (!topics.containsKey(request)) {
            Queue<String> temp = new LinkedList<>();
            temp.offer(text);
            topics.put(request, temp);
        } else {
            topics.get(request).offer(text);
        }
    }

    public ConcurrentHashMap<String, Queue<String>> getTopics() {
        return topics;
    }
}
