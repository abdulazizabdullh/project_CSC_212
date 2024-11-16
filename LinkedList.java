
public class LinkedList<T> implements List<T> {
    private Node<T> head;
    private int size;

    
    public void insert(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public boolean contains(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

   
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public List<T> retainAll(List<T> other) {
        LinkedList<T> result = new LinkedList<>();
        Node<T> current = head;
        while (current != null) {
            if (other.contains(current.data)) {
                result.insert(current.data);
            }
            current = current.next;
        }
        return result;
    }

    
    public int size() {
        return size;
    }

    public void set(int index, T data) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.data = data;  // Update the data at the specified index
    }

    public void printList() {
        Node<T> current = head;
        System.out.print("{");
        while (current != null) {
            System.out.print(current.data);
            current = current.next;
            if (current != null) {
                System.out.print(", ");
            }
        }
        System.out.println("}");
    }
}