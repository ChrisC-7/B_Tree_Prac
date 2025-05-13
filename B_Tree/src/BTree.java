import java.util.Arrays;

public class BTree {
    private final int order;
    private Node root;
    private HelperStruct extra;

    public BTree(int order) {
        this.order = order;
    }

    public Node getRoot() {
        return root;
    }


    public void setRoot(Node root) {
        this.root = root;
    }

    public int getOrder() {
        return order;
    }

    // search node contains the key
    public Node search(int key) {
        return root.search(key);
    }

    public void print(){

    }

    public void insert(int key){
        if(root == null){
            root = new Node(order);
            root.insertNotFullKey(key);
        } else{
            Node current = root;
            while (current.isLeaf());

        }






    }





}
