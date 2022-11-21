package service;

import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private Map<Integer, Node> nodes = new HashMap<>();
    private CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();

    public void add(Task task) {
        if (task != null) {
            if (nodes.containsKey(task.getId())) {
                customLinkedList.removeNode(nodes.get(task.getId()));
            }
            Node<Task> newNode = customLinkedList.linkLast(task);
            nodes.put(task.getId(), newNode);
        }
    }

    public List<Task> getHistory() {
        return customLinkedList.getTasks();

    }

    @Override
    public void remove(int id) {
        customLinkedList.removeNode(nodes.get(id));
    }

    class CustomLinkedList<Task> {
        private Node<Task> head;
        private Node<Task> tail;

        public Node<Task> linkLast(Task element) {
            Node<Task> oldTail = tail;
            Node<Task> newNode = new Node<>(element, null, tail);

            tail = newNode;
            if (oldTail == null) {
                head = newNode;
            } else {
                oldTail.next = newNode;
            }
            newNode.prev = oldTail;
            return newNode;
        }

        public List<Task> getTasks() {
            List<Task> history = new ArrayList<>();
            Node<Task> temp = head;
            while (temp != null) {
                history.add(temp.data);
                temp = temp.next;
            }
            return history;
        }

        public void removeFirst() {
            if (head.next == null) {
                head = null;
            } else {
                head.next.prev = null;
                head = head.next;
            }
        }

        public void removeLast() {
            if (tail.prev == null) {
                tail = null;
            } else {
                tail.prev.next = null;
                tail = tail.prev;
            }
        }

        public void removeNode(Node<Task> key) {
            if (key != null) {
                if (key == head) {
                    removeFirst();
                } else if (key == tail) {
                    removeLast();
                } else {
                    key.next.prev = key.prev;
                    key.prev.next = key.next;
                }
            }
        }
    }
}
