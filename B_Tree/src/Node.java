import java.util.Arrays;

public class Node {
    private int order;
    private int[] keys;
    private Node[] children;
    private int size;
    private boolean isLeaf;


    public Node() {
        this(3);
    }

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

    // The Basic Setters and Getters
    public void setChildren(Node childNode, int index) {
        this.children[index] = childNode;
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

    public boolean checkFull() {
        return this.size == this.order - 1;
    }

    public boolean isOverflow() {
        return this.size >= this.order;
    }

    public Node search(int key) {
        if (isLeaf()) {
            if (indexOf(key) >= 0) return this;
            else return null;
        } else {
            return getChild(childIndexOf(key)).search(key);
        }
    }

    // find the index of the key in our keys
    public int indexOf(int key) {
        return Arrays.binarySearch(keys, 0, size, key);
    }

    public int getKey(int index) {return this.keys[index];}


    /**
     * @param key - the key need to be found
     * @return the index of the child
     */
    public int childIndexOf(int key) {
        for (int i = 0; i < size; i++) {
            if (key < keys[i]) return i;
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
     * @param key - the key to insert into the array
     */
    public void insertNotOverflowKey(int key) {
        int index = 0;
        while (keys[index] < key && index < size) {
            index++;
        }
        for (int i = size; i > index; i--) {
            keys[i] = keys[i - 1];
        }
        keys[index] = key;
        size++;
    }

    // insert the key to the node

    /**
     *  to do : put the middle data to the extra.
     * @param key
     * @param extra
     */
    public void insertKey(int key, SplitResult extra){
        if(checkFull()){
            // move the rightest data in the keys to the extra
            int index = size/2;
            if(keys[size/2] < key) {
                extra.setExtraKey(keys[size/2]);
                while(index < size && keys[index] < key) {
                    keys[index] = keys[index + 1];
                    index++;
                }
            } else{
                if(keys[size/2 - 1] < key){
                    extra.setExtraKey(keys[size/2]);
                } else{
                    extra.setExtraKey(keys[size/2-1]);
                    index--;
                    while(index > 0 && keys[index] > key) {
                        keys[index] = keys[index - 1];
                        index--;
                    }
                }
            }
            keys[index] = key;
            extra.setValid(true);

        }else insertNotOverflowKey(key);
    }

    SplitResult split(){
        int mid = this.size / 2;
        Node right = new Node(order, this.isLeaf);
        for(int i = mid + 1; i < this.size; i++) {
            right.keys[i-mid-1] = this.keys[i];
            right.size++;
        }
        if(!isLeaf()){
            for(int i = mid + 1; i <= this.size; i++) {
                right.children[i - mid-1] = this.children[i];
            }
        }
        int middleKey = keys[mid];
        size = mid;
        return new SplitResult(middleKey, right, true);
    }
}
