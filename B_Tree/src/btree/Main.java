package btree;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6,7};
        BTree bt = new BTree();

        for(int key:a){
            bt.insert(key);
        }

        bt.delete(1);
        System.out.println("Hello");


    }
}