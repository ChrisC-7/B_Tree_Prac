public class BTree {
    private Node root;






    private class Node{
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

        public void setChildren(Node[] children) {
            this.children = children;
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
    }
}
