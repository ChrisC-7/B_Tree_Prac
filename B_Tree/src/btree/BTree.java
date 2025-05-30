package btree;

public class BTree {
    private final int order;
    private Node root;


    /***
     * Constructs a default B-Tree with a default order of 3.
     */
    public BTree(){
        this(3);
    }

    /***
     * Constructs a B-Tree with the specified order
     *
     * @param order - the maximum number of children per node(minimum is 3)
     */
    public BTree(int order) {
        this.order = order;
        this.root = null;
    }

    /***
     * Searches for the node containing the given key
     *
     * @param key - the key we are searching for
     * @return the node contains the key, or null if not found
     */
    public Node search(int key) {
        Node curr = root;
        while (curr != null) {
            if (curr.contains(key)) return curr;
            curr = curr.getChild(curr.childIndexOf(key));
        }
        return null;

    }

    /***
     * insert the given key to our tree
     *
     * @param key - the key we're going to insert to the tree
     */
    public void insert(int key){
        if(search(key) != null) return;
        if(root == null){
            root = new Node(order);
            root.insertNotOverflowNode(key);
            return;
        }

        SplitResult result = insertRecursive(root, key);

        if(result != null){
            Node newRoot = new Node(order, false);
            newRoot.setChild(root, 0);
            newRoot.setChild(result.getRightNode(), 1);
            newRoot.insertNotOverflowNode(result.getMiddleKey());
            root = newRoot;
        }
    }

    /***
     * Recursively inserts a key into the subtree rooted at the given node.
     *
     * @param node - the node going to be inserted
     * @param key - the key going to be inserted recursively
     * @return the result of the split if one occurred; {@code null} otherwise
     * @see SplitResult
     */
    private SplitResult insertRecursive(Node node, int key){
        if (node.isLeaf()) {
            node.insertNotOverflowNode(key);
        } else {
            int childIndex = node.childIndexOf(key);
            SplitResult result = insertRecursive(node.getChild(childIndex), key);
            if (result != null) {
                node.insertNotOverflowNode(result.getMiddleKey());
                node.setChild(result.getRightNode(), childIndex + 1 );
            }
        }

        if (node.isOverflow()) return node.split();
        return null;
    }

    public void delete(int key) {
        if (root == null) {
            throw  new IllegalArgumentException("Tree is empty, Can't delete");
        }

        root.delete(key);

        if (root.getSize() == 0 && !root.isLeaf()) {
            root = root.getChild(0);
        }

        if (root != null && root.getSize() == 0 && root.isLeaf()) {
            root = null;
        }

    }
}
