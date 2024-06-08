import b.tree.BTree;
import n.area.NArea;


public class Main {
    public static void testNAreaTree() {
        NArea nArea = new NArea();
        for (int i = 0; i < 100; i++) {
            nArea.insere(i);
        }

        nArea.inOrdem();
    }

    public static void testBTree() {
        BTree bTree = new BTree();
        for (int i = 0; i < 100; i++) {
            bTree.insert(i, i);
        }

        bTree.inOrdem();
    }

    public static void main(String[] args) {
        testBTree();
    }
}