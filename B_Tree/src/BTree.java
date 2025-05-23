import java.util.Arrays;

public class BTree {
    private final int order;
    private Node root;


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
            root.insertNotOverflowKey(key);
            return;
        }

        SplitResult result = insertRecursive(root, key);

        if(result != null){
            Node newRoot = new Node(order, false);
            newRoot.setChildren(root, 0);
            newRoot.setChildren(result.getExtraNode(), 1);
            newRoot.insertNotOverflowKey(result.getExtraKey());
            root = newRoot;
        }
    }

    private SplitResult insertRecursive(Node node, int key){
        if (node.isLeaf()) {
            node.insertNotOverflowKey(key);
        } else {
            SplitResult result = insertRecursive(node.getChild(node.childIndexOf(key)), key);
            if (result != null) {
                node.insertNotOverflowKey(result.getExtraKey());
                node.setChildren(result.getExtraNode(), node.childIndexOf(key));
            }
        }

        if (node.isOverflow()) return node.split();
        return null;
    }
}
