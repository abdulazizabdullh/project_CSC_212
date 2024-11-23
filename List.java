public interface List<T> {
    public void insert(T data);
    public boolean contains(T data);
    public T get(int index);
    public List<T> retainAll(List<T> other);
    public int size();
    public void set(int index, T data);
    public void printList();
}