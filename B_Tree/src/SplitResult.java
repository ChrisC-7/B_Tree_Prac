public class SplitResult {
    private int middleKey;
    private Node rightNode;

    /***
     *  Constructs an empty SplitResult with no key and null node, marked invalid.
     */
    public SplitResult() {
        this(0,null);
    }

    /***
     * Constructs a SplitResult with the given middle key, right node, and validity.
     *
     * @param middleKey - the key to move up to the parent
     * @param rightNode - the new right node created after splitting
     */
    public SplitResult(int middleKey, Node rightNode) {
        this.middleKey = middleKey;
        this.rightNode = rightNode;
    }

    /***
     * Sets the middle key of the split result.
     * @param middleKey - the key to move up to the parent
     */
    public void setMiddleKey(int middleKey) {
        this.middleKey = middleKey;
    }

    /***
     * Sets the right node of the split result.
     *
     * @param rightNode - the new right Node
     */
    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    /***
     * Returns the middle key from the split.
     *
     * @return the middle key
     */
    public int getMiddleKey() {
        return middleKey;
    }

    /***
     * Returns the right node from the split
     *
     * @return the right child Node
     */
    public Node getRightNode() {
        return rightNode;
    }

    /***
     * Returns whether the split result is empty.
     * A result is considered empty if the right node is null.
     *
     * @return {@code true} if empty <br>
     *         {@code false} otherwise
     */
    public boolean checkEmpty(){
        return this.rightNode == null;
    }

    /***
     * Returns a human-readable string representing the split result,
     * useful for debugging purposes.
     *
     * @return a string representation of the split result
     */
    @Override
    public String toString() {
        return !checkEmpty() ? "SplitResult{key=" + middleKey + ", rightNode.size=" + (rightNode != null ? rightNode.getSize() : "null") + "}" : "EmptySplit";
    }


}