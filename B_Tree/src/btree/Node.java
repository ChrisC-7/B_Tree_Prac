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
     * Performs a binary search for the spedified key in the keys array
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
}
