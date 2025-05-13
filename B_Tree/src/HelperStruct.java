public class HelperStruct {
    private int extraKey;
    private Node extraNode;


    public int getExtraKey() {
        return extraKey;
    }

    public void setExtraKey(int extraKey) {
        this.extraKey = extraKey;
    }

    public Node getExtraNode() {
        return extraNode;
    }

    public void setExtraNode(Node extraNode) {
        this.extraNode = extraNode;
    }

    public boolean checkEmpty(){
        return this.extraNode == null;
    }
}