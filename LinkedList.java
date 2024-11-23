public class LinkedList<T> implements List<T> {
    private Node<T> head;
    private int size;


    public void insert(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> tmp = head;
            while (tmp.next != null) {
                tmp = tmp.next;
            }
            tmp.next = newNode;
        }
        size++;
    }

    public boolean contains(T data) {
        Node<T> tmp = head;
        while (tmp != null) {
            if (tmp.data.equals(data)) {
                return true;
            }
            tmp = tmp.next;
        }
        return false;
    }


    public T get(int index) {
        if (index >= size) {
            return null;
        }

        if (index < 0) {
            return null;
        }
        Node<T> tmp = head;
        for (int i = 0; i < index; i++) {
            tmp = tmp.next;
        }
        return tmp.data;
    }

    public List<T> retainAll(List<T> other) {
        LinkedList<T> result = new LinkedList<>();
        Node<T> tmp = head;
        while (tmp != null) {
            if (other.contains(tmp.data)) {
                result.insert(tmp.data);
            }
            tmp = tmp.next;
        }
        return result;
    }


    public int size() {
        return size;
    }

    public void set(int index, T data) {
        if (index >= size) {
            return;
        }

        if (index < 0) {
            return;
        }
        Node<T> tmp = head;
        for (int i = 0; i < index; i++) {
            tmp = tmp.next;
        }
        tmp.data = data;
    }


}