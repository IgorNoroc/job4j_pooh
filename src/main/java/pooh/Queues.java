package pooh;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Storage Queue.
 * task : Техническое задание - проект "Pooh JMS" [#318329].
 */
public class Queues {
    private final ConcurrentHashMap<String, Queue<String>> queue = new ConcurrentHashMap<>();

    /**
     * Add text or queue to storage queue.
     *
     * @param request request.
     * @param text    text.
     */
    public void addToQueue(String request, String text) {
        addToStore(request, text);
    }

    /**
     * Get text from storage queue.
     *
     * @param request request.
     * @return text or nothing.
     */
    public String getFromQueue(String request) {
        if (queue.containsKey(request)) {
            return getFromStore(request);
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
        String rsl = queue.get(request).poll();
        return rsl != null ? rsl : ",: sorry, the information is absent";
    }

    /**
     * Checking if queue contains request and add it to queue.
     *
     * @param request request.
     * @param text    text.
     */
    private void addToStore(String request, String text) {
        if (!queue.containsKey(request)) {
            Queue<String> temp = new LinkedList<>();
            temp.add(text);
            queue.put(request, temp);
        } else {
            queue.get(request).offer(text);
        }
    }

    public ConcurrentHashMap<String, Queue<String>> getQueue() {
        return queue;
    }
}
