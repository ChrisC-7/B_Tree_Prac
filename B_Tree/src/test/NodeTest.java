package test;

import btree.Node;
import btree.SplitResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class NodeTest {

    @Test
    public void testSplitBalancedKeys(){
        Node node = new Node(5, true);
        node.insertNotOverflowNode(10);
        node.insertNotOverflowNode(20);
        node.insertNotOverflowNode(30);
        node.insertNotOverflowNode(40);
        node.insertNotOverflowNode(50);

        SplitResult result = node.split();

        assertEquals(2, node.getSize());
        assertEquals(2, result.getRightNode().getSize());
        assertEquals(30, result.getMiddleKey());
        assertEquals(10, node.getKey(0));
        assertEquals(20, node.getKey(1));
        assertEquals(40, result.getRightNode().getKey(0));
        assertEquals(50, result.getRightNode().getKey(1));
    }

    @Test
    public void testInsertDuplicateKeyThrows() {
        Node node = new Node(4, true);
        node.insertNotOverflowNode(10);
        node.insertNotOverflowNode(20);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            node.insertNotOverflowNode(20);
        });

        assertTrue(exception.getMessage().contains("Duplicate key"));
    }

}
