package com.example.eta;

public class PriorityQueue<T, S extends Comparable<S>> {
    public Pair<T, S>[] tree;
    public int count;

    @SuppressWarnings("unchecked")
    public PriorityQueue(int initialCapacity) {
        tree = new Pair[initialCapacity];
        count = 0;
    }

    public void enqueue(T item, S priority) {
        if (count == tree.length) {
            expandCapacity();
        }
        tree[count] = new Pair<>(item, priority);
        count++;
        if (count > 1) {
            shiftUp();
        }
    }

    private void shiftUp() {
        Pair<T, S> temp;
        int next = count - 1;
        temp = tree[next];
        while ((next != 0) && temp.compareTo(tree[(next - 1) / 2]) >= 0) {
            tree[next] = tree[(next - 1) / 2];
            next = (next - 1) / 2;
        }
        tree[next] = temp;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Empty Heap");
        }
        T maxItem = tree[0].item;
        tree[0] = tree[count - 1];
        shiftDown();
        count--;
        return maxItem;
    }

    private void shiftDown() {
        Pair<T, S> temp;
        int node = 0, left = 1, right = 2, next;
        if (tree[left] == null && tree[right] == null)
            next = count;
        else if (tree[right] == null) {
            next = left;
        }
        else if (tree[left].compareTo(tree[right]) >= 0) {
            next = left;
        } else {
            next = right;
        }
        temp = tree[node];
        while (next < count && tree[next].compareTo(temp) >= 0) {
            tree[node] = tree[next];
            node = next;
            left = 2 * node + 1;
            right = 2 * node + 2;
            if ((tree[left] == null ) && (tree[right] == null)) {
                next = count;
            } else if (tree[right] == null) {
                next = left;
            }
            else if (tree[left].compareTo(tree[right]) >= 0) {
                next = left;
            } else {
                next = right;
            }
        }
        tree[node] = temp;
    }

    @SuppressWarnings("unchecked")
    private void expandCapacity() {
        Pair<T, S>[] largerArray = (Pair<T, S>[]) new Pair[tree.length * 2];
        for (int i = 0; i < tree.length; i++) {
            largerArray[i] = tree[i];
        }
        tree = largerArray;
    }

    public boolean isEmpty() {
        return (count == 0);
    }

    class Pair<U, V extends Comparable<V>> implements Comparable<Pair<U, V>> {
        U item;
        V priority;

        public Pair(U item, V priority) {
            this.item = item;
            this.priority = priority;
        }

        @Override
        public int compareTo(Pair<U, V> other) {
            return this.priority.compareTo(other.priority);
        }
    }
}

class Pair<T, S extends Comparable<S>> implements Comparable<Pair<T, S>> {
    T item;
    S priority;

    public Pair(T item, S priority) {
        this.item = item;
        this.priority = priority;
    }

    @Override
    public int compareTo(Pair<T, S> other) {
        return this.priority.compareTo(other.priority);
    }
}
class Node<T, S extends Comparable<S>> {
    T item;
    S priority;
    Node<T, S> next;

    public Node(T item, S priority) {
        this.item = item;
        this.priority = priority;
        this.next = null;
    }
}
class Date implements Comparable<Date> {
    int year, month, day;

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) {
            return this.year - other.year;
        }
        if (this.month != other.month) {
            return this.month - other.month;
        }
        return this.day - other.day;
    }
}