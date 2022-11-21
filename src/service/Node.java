package service;

import model.Task;

public class Node<Task> {

    public Task data;
    public Node<Task> next;
    public Node<Task> prev;

    public Node(Task data, Node next, Node prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}
