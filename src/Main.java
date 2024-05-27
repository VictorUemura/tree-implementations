import n.area.NArea;

public class Main {
    public static void main(String[] args) {
        NArea nArea = new NArea();
        for(int i = 0; i < 100; i++) {
            nArea.insere(i);
        }

        nArea.inOrdem();
    }
}