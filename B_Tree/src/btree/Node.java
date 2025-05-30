package btree;

import java.util.Arrays;

public class Node {
    public static final int DEFAULT_ORDER = 3;

    private final int order;
    private int[] keys;
    private Node[] children;
    private int size;
    private boolean isLeaf;


    /***
     * Constructs a default B-Tree btree.Node with a default order of 3.
     */
    public Node() {
        this(DEFAULT_ORDER);
    }

    /***
     * Constructs a B-Tree btree.Node
     *
     * @param order - the maximum number of children per node(minimum is 3)
     */
    public Node(int order) {
        this(order, true, 0);
    }

    public Node(int order, boolean isLeaf) {
        this(order, isLeaf, 0);
    }

    public Node(int order, boolean isLeaf, int size) {
        this.order = order;
        this.keys = new int[order];
        this.children = new Node[order + 1];
        this.isLeaf = isLeaf;
        this.size = size;
    }

    public int getLeastSize() {
        return (this.order + 1 )/2 - 1;
    }

    /***
     * Set the child node at the specified index in the children node array
     *
     * @param childNode - the node going to be set
     * @param index - the index at which to set the child
     */
    public void setChild(Node childNode, int index) {
        if (index < 0 || index >= children.length) {
            throw new IndexOutOfBoundsException("Invalid child index for setting: " + index);
        }
        this.children[index] = childNode;
    }

    /***
     * Returns the array of the child nodes
     *
     * @return the children node array of this node
     */
    public Node[] getChildren() {
        return children;
    }

    /***
     * Returns the number of keys currently stored in the node
     *
     * @return the size of the node
     */
    public int getSize() {
        return size;
    }

    /***
     * Returns whether this node is a leaf node
     *
     * @return {@code true} if the node is a leaf<br>
     *         {@code false} otherwise
     */
    public boolean isLeaf() {
        return isLeaf;
    }

    /***
     * Checks whether this node has exceeded its capacity (i.e., overflowed).
     *
     * @return {@code true} if the node is overflowed<br>
     *         {@code false} otherwise
     */
    public boolean isOverflow() {
        return this.size >= this.order;
    }

    /***
     * Checks whether the node contains the key
     *
     * @param key - the key to search
     * @return {@code true} if this node contains the key
     */
    public boolean contains(int key) {
        return indexOf(key) >= 0;
    }

    /***
     * Performs a binary search for the specified key in the keys array
     *
     * @param key - the key to find
     * @return the index of the key if found, otherwise a negative number
     */
    public int indexOf(int key) {
        return Arrays.binarySearch(keys, 0, size, key);
    }

    /***
     * Returns the key at the specified index.
     *
     * @param index - the index of the keys array
     * @return the key value at the index
     */
    public int getKey(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid key index: " + index);
        }
        return this.keys[index];
    }


    /**
     * Determines the appropriate child index for the specified key.
     *
     * @param key - the key need to be found
     * @return the index of the child the key should be
     */
    public int childIndexOf(int key) {
        for (int i = 0; i < size; i++) {
            if (key < keys[i]) return i;
        }
        return size;
    }

    /**
     * Retrieves the child node at the specified index.
     *
     * @param index - the index of the child in children array
     * @return the child node
     */
    public Node getChild(int index) {
        if (index < 0 || index >= children.length) {
            throw new IndexOutOfBoundsException("Invalid child index: " + index);
        }
        return children[index];
    }


    /**
     *  Inserts a key into the current node assuming it is not overflowed.
     *  Keys are inserted in sorted order by shifting larger keys to the right.
     *
     * @param key - the key to insert into the array
     */
    public void insertNotOverflowNode(int key) {
        int index = 0;
        while (index < size && keys[index] < key ) {
            index++;
        }

        // Check for duplicate key
        if(index < size && keys[index] == key) {
            throw new IllegalArgumentException("Duplicate key insertion is not allowed: " + key);
        }

        // Shift keys and children to make room
        for (int i = size; i > index; i--) {
            keys[i] = keys[i - 1];
        }

        if (!isLeaf()) {
            for (int i = size; i >= index + 1; i--) {
                children[i + 1] = children[i];
            }
        }

        keys[index] = key;
        size++;
    }


    /***
     * Splits the current node and returns the middle key and right node in a {@link SplitResult}.
     * The node is split at the middle index, and the right half is moved to a new node.
     *
     * @return the result of the split, including the middle key and right node
     * @see SplitResult
     */
    public SplitResult split(){
        int mid = this.size / 2;
        int middleKey = keys[mid];


        Node right = new Node(order, isLeaf());

        // move the right key and childNode to the new btree.Node
        for(int i = mid + 1; i < this.size; i++) {
            right.keys[i - (mid + 1)] = this.keys[i];
            this.keys[i] = 0;
            right.size++;
        }
        if(!isLeaf()) {
            for (int i = mid + 1; i <= this.size; i++) {
                int rightChildIndex = i - (mid + 1);
                if (rightChildIndex < right.children.length) {
                    right.children[rightChildIndex] = this.children[i];
                    this.children[i] = null;
                }
            }
        }
        this.size = mid;
        this.keys[mid] = 0;
        return new SplitResult(middleKey, right);
    }

    /***
     * Gets the largest key from the left child tree
     * @return the largest key
     */
    public int getPredecessor(int index){
        Node cur = this.getChild(index);
        while (!cur.isLeaf()) cur = cur.getChild(cur.getChildren().length - 1);
        return cur.getKey(cur.size - 1);
    }

    /***
     * Gets the smallest key from the right child tree
     * @return the largest key
     */
    public int getSuccessor(int index){
        Node cur = this.getChild(index + 1);
        while (!cur.isLeaf()) cur = cur.getChild(0);
        return cur.getKey(0);
    }


    public void delete(int key) {
        if(this.contains(key)) {
            if(isLeaf()) deleteLeafNode(key);
            else deleteNonLeafNode(key);
        }
        else {
            int index = this.childIndexOf(key);
            boolean atLastChild = (index == this.size);

            if (children[index].getSize() == getLeastSize()) {
                fill(index);
            }

            if (atLastChild) {
                children[index - 1].delete(key);
            } else {
                children[index].delete(key);
            }
        }

    }

    public void deleteLeafNode(int key) {
        int index = this.indexOf(key);
        for (int i = index; i < this.getSize() - 1; i++) {
            this.keys[i] = this.getKey(i+1);
        }
        this.keys[this.size - 1] = 0;
        this.size--;
    }

    public void deleteNonLeafNode(int key) {
        int index = this.indexOf(key);
        if(this.getChild(index).getSize() >= this.order/2 + 1) {
            int predecessor = this.getPredecessor(index);
            this.keys[index] = predecessor;
            this.getChild(index).delete(predecessor);
        }
        else if(this.getChild(index + 1).getSize() >= this.order/2 + 1) {
            int successor = this.getSuccessor(index);
            this.keys[index] = successor;
            this.getChild(index + 1).delete(successor);
        }
        else{
            this.merge(index);
            this.getChild(index).delete(key);
        }
    }

    private void fill(int index) {
        if(index > 0 && children[index - 1].getSize() > getLeastSize()) {
            borrowFromPrev(index);
        }else if (index < size && children[index + 1].getSize() > getLeastSize()) {
            borrowFromNext(index);
        }else{
            if(index < size){
                merge(index);
            }else merge(index - 1);
        }
    }

    private void borrowFromPrev(int index) {
        Node child = this.getChild(index);
        Node sibling = child.getChild(index - 1);

        for (int i = child.size - 1; i >= 0; i--){
            child.keys[i + 1] = child.keys[i];
        }

        if(!child.isLeaf()) {
            for(int i = child.size; i >= 0; i--){
                child.children[i + 1] = child.children[i];
            }
        }

        child.keys[0] = keys[index - 1];
        if(!child.isLeaf()) {
            child.children[0] = sibling.children[sibling.size];
        }

        keys[index - 1] = sibling.keys[sibling.size - 1];

        child.size++;
        sibling.size--;
    }

    private void borrowFromNext(int index) {
        Node child = this.getChild(index);
        Node sibling = child.getChild(index + 1);

        child.keys[child.size] = keys[index];
        if (!child.isLeaf()) {
            child.children[child.size + 1] = sibling.children[0];
        }

        keys[index] = sibling.keys[0];

        for (int i = 1; i < sibling.size; i++) {
            sibling.keys[i - 1] = sibling.keys[i];
        }
        if (!sibling.isLeaf()) {
            for (int i = 1; i <= sibling.size; i++) {
                sibling.children[i - 1] = sibling.children[i];
            }
        }

        child.size++;
        sibling.size--;

    }


    public void merge(int index) {
        Node left = children[index];
        Node right = children[index + 1];

        left.keys[getLeastSize()] = keys[index];

        for (int i = 0; i < right.size; i++) {
            left.keys[i + getLeastSize() + 1] = right.keys[i];
        }

        if (!left.isLeaf()) {
            for (int i = 0; i <= right.size; i++) {
                left.children[i + getLeastSize() + 1] = right.children[i];
            }
        }

        for (int i = index + 1; i < size; i++) {
            keys[i - 1] = keys[i];
            children[i] = children[i + 1];
        }

        size--;
        left.size += right.size + 1;
        children[size + 1] = null;
    }
}
