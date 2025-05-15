import java.util.Arrays;

public class BTree {
    private final int order;
    private Node root;
    private SplitResult extra;


    public BTree(){
        this(3);
    }

    public BTree(int order) {
        this.order = order;
        this.root = null;
    }

    // search node contains the key
    public Node search(int key) {
        return root.search(key);
    }

    public void insert(int key){
        if(root == null){
            root = new Node(order);
            root.insertNotFullKey(key);
        } else{
            if(root.isLeaf()){
                root.insertKey(key, extra);
            } else{
                Node current = root.getChild(root.childIndexOf(key));

                current.insertKey(key,extra);
                checkSplit(root, key, i+1);
            }

            // check the root of the tree if the tree need a new root
            checkRoot(root);
        }
    }
}
