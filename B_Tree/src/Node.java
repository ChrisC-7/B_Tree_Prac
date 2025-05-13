import java.util.Arrays;

public class Node{
    private int order;
    private int[] keys;
    private Node[] children;
    private int size;
    private boolean isLeaf;

    public Node(){
        this(3);
    }

    public Node(int order){
        this(order, true, 0);
    }

    public Node(int order, boolean isLeaf, int size) {
        this.order = order;
        this.keys = new int[order-1];
        this.children = new Node[order];
        this.isLeaf = isLeaf;
        this.size = size;
    }

    // The Basic Setters and Getters
    public void setOrder(int order) {
        this.order = order;
    }

    public void setKeys(int[] keys) {
        this.keys = keys;
    }

    public void setChildren(Node childNode, int index) {
        this.children[index] = childNode;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public int getOrder() {
        return order;
    }

    public int[] getKeys() {
        return keys;
    }

    public Node[] getChildren() {
        return children;
    }

    public int getSize() {
        return size;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public Node search (int key){
        if(isLeaf()){
            if(indexOf(key) >= 0) return this;
            else return null;
        }
        else {
            return getChild(childIndexOf(key)).search(key);
        }
    }

    // find the index of the key in our keys
    public int indexOf(int key) {
        return Arrays.binarySearch(keys, 0, size, key);
    }

    /**
     *
     * @param key - the key need to be found
     * @return the index of the child
     */
    public int childIndexOf(int key) {
        for(int i = 0; i < size; i++){
            if(key < keys[i]) return i;
        }
        return size;
    }

    /**
     * @param index - the index of the child in children array
     * @return the child node
     */
    public Node getChild(int index) {
        return children[index];
    }


    /**
     *
     * @param key - the key to insert into the array
     */
    public void insertNotFullKey(int key){
        int index = 0;
        while(keys[index] < key && index < size){ index++;}
        for(int i = size; i > index; i--){
            keys[i] = keys[i-1];
        }
        keys[index] = key;
        size++;
    }


    public void splitChild(int key){


    }
}