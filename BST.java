public class BST<K extends Comparable<K>, T> {
    BSTNode<K, T> root;
    BSTNode<K, T> curr;
    int count;

    public BST() {
        root = curr = null;
        count = 0;
    }


    public boolean empty() {
        return root == null;
    }

    public T retrieve() {
        T data = null;
        if (curr != null)
            data = curr.data;
        return data;
    }

    public void update(T e) {
        if (curr != null)
            curr.data = e;
    }


    public boolean find(K key) {
        BSTNode<K, T> p = root;

        if (empty())
            return false;

        while (p != null) {
            if (p.key.compareTo(key) == 0) {
                curr = p;
                return true;
            } else if (key.compareTo(p.key) < 0)
                p = p.left;
            else
                p = p.right;
        }
        return false;
    }


    public boolean insert(K key, T data) {

        if (empty()) {
            curr = root = new BSTNode<K, T>(key, data);
            count++;
            return true;
        }
        BSTNode<K, T> par = null;
        BSTNode<K, T> child = root;

        while (child != null) {
            if (child.key.compareTo(key) == 0) {
                return false;
            } else if (key.compareTo(child.key) < 0) {
                par = child;
                child = child.left;
            } else {
                par = child;
                child = child.right;
            }
        }

        if (key.compareTo(par.key) < 0) {
            par.left = new BSTNode<K, T>(key, data);
            curr = par.left;
        } else {
            par.right = new BSTNode<K, T>(key, data);
            curr = par.right;
        }
        count++;
        return true;
    }
}
