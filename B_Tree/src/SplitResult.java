public class SplitResult {
    private int extraKey;
    private Node extraNode;
    private boolean isValid;

    public SplitResult() {
        this(0,null,false);
    }

    public SplitResult(int extraKey, Node extraNode, boolean Valid) {
        this.extraKey = extraKey;
        this.extraNode = extraNode;
        this.isValid = Valid;
    }

    public void setExtraKey(int extraKey) {
        this.extraKey = extraKey;
    }

    public void setExtraNode(Node extraNode) {
        this.extraNode = extraNode;
    }

    public int getExtraKey() {
        return extraKey;
    }

    public Node getExtraNode() {
        return extraNode;
    }

    public boolean checkEmpty(){
        return this.extraNode == null;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isValid() {
        return isValid;
    }
}