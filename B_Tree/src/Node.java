import java.util.Arrays;

public class Node {
    private final int order;
    private int[] keys;
    private Node[] children;
    private int size;
    private boolean isLeaf;


    /***
     * Constructs a default B-Tree Node with a default order of 3.
     */
    public Node() {
        this(3);
    }

    /***
     * Constructs a B-Tree Node
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
    public int getKey(int index) {return this.keys[index];}


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
        return children[index];
    }


    /**
     *  Inserts a key into the current node assuming it is not overflowed.
     *  Keys are inserted in sorted order by shifting larger keys to the right.
     *
     * @param key - the key to insert into the array
     */
    public void insertNotOverflowKey(int key) {
        int index = 0;
        while (index < size && keys[index] < key ) {
            index++;
        }
        for (int i = size; i > index; i--) {
            keys[i] = keys[i - 1];
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
        Node right = new Node(order, this.isLeaf);

        // move the right key and childNode to the new Node
        for(int i = mid + 1; i < this.size; i++) {
            right.keys[i-mid-1] = this.keys[i];
            this.keys[i] = 0;
            right.size++;
        }
        if(!isLeaf()){
            for(int i = mid + 1; i <= this.size; i++) {
                right.children[i - mid-1] = this.children[i];
                this.children[i] = null;
            }
        }
        int middleKey = keys[mid];
        this.keys[mid] = 0;
        size = mid;
        return new SplitResult(middleKey, right);
    }
}
