package b.tree;

public class BTree {
    private No root;

    public BTree() {
        root = null;
    }

    // busca pai para caso seja necessario realizar um split do no da arvore
    private No buscaPai(No filho) {
        No aux = root;
        No pai = aux;

        int pos;
        int valueAux = filho.getVInfo(0);
        while (aux != filho) {
            pos = aux.procurarPosicao(valueAux);
            pai = aux;
            aux = pai.getVLig(pos);
        }

        return pai;
    }

    private void split(No noExcedente) {
        No pai;
        No cx1, cx2;
        int pos, valorInsercao, posicaoInsercao;

        while (noExcedente.getTl() == No.m * 2 + 1) {
            pai = buscaPai(noExcedente);

            cx1 = new No();
            cx2 = new No();

            for (int i = 0; i < No.m; i++) {
                cx1.setVInfo(i, noExcedente.getVInfo(i));
                cx1.setVPos(i, noExcedente.getVPos(i));
                cx1.setVLig(i, noExcedente.getVLig(i));
                cx1.setTl(cx1.getTl() + 1);
            }
            cx1.setVLig(No.m, noExcedente.getVLig(No.m));

            for (int i = No.m + 1; i < noExcedente.getTl(); i++) {
                cx2.setVInfo(i - No.m - 1, noExcedente.getVInfo(i));
                cx2.setVPos(i - No.m - 1, noExcedente.getVPos(i));
                cx2.setVLig(i - No.m - 1, noExcedente.getVLig(i));
                cx2.setTl(cx2.getTl() + 1);
            }
            cx2.setVLig(cx2.getTl(), noExcedente.getVLig(noExcedente.getTl()));

            valorInsercao = noExcedente.getVInfo(No.m);
            posicaoInsercao = noExcedente.getVPos(No.m);

            if (noExcedente == pai) {
                noExcedente.setVInfo(0, valorInsercao);
                noExcedente.setVPos(0, valorInsercao);
                noExcedente.setVLig(0, cx1);
                noExcedente.setVLig(1, cx2);
                noExcedente.setTl(1);
            } else {
                pos = pai.procurarPosicao(valorInsercao);
                pai.remanejar(pos);
                pai.setVInfo(pos, valorInsercao);
                pai.setVPos(pos, posicaoInsercao);

                pai.setVLig(pos, cx1);
                pai.setVLig(pos + 1, cx2);
                pai.setTl(pai.getTl() + 1);
            }

            noExcedente = pai;
        }
    }

    public void insert(int value, int index) {
        int pos;
        No aux;

        // caso se nao existir nenhum elemento dentro da btree
        // nesse caso o tl - tamanho logico - e incrementado automaticamente
        if (root == null)
            root = new No(value, index);
        else {
            aux = root;
            pos = aux.procurarPosicao(value);
            while (aux.getVLig(0) != null) {
                aux = aux.getVLig(pos);
                pos = aux.procurarPosicao(value);
            }

            aux.remanejar(pos);
            aux.setVInfo(pos, value);
            aux.setVPos(pos, index);
            aux.setTl(aux.getTl() + 1);

            if (aux.getTl() == No.m * 2 + 1)
                split(aux);
        }
    }

    public void inOrdem() {
        inOrdem(root);
    }

    public void inOrdem(No aux) {
        if (aux != null) {
            for (int i = 0; i < aux.getTl(); i++) {
                inOrdem(aux.getVLig(i));
                System.out.println("Valor: " + aux.getVInfo(i) + " - Index: " + aux.getVPos(i));
            }
            inOrdem(aux.getVLig(aux.getTl()));
        }
    }

}
