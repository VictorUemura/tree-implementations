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

    private No menorSub(No subArvore) {
        while (subArvore.getVLig(0) != null)
            subArvore = subArvore.getVLig(0);
        return subArvore;
    }

    private No maiorSub(No subArvore) {
        while (subArvore.getVLig(0) != null)
            subArvore = subArvore.getVLig(subArvore.getTl());
        return subArvore;
    }

    private void redistribuiOuConcatena(No folha) {
        int pos;
        No pai, irmaE, irmaD;

        while (folha.getTl() < No.m && folha != root) {
            pai = buscaPai(folha);

            // encontra as irmas da folha
            irmaE = irmaD = null;
            pos = pai.procurarPosicao(folha.getVInfo(0));

            if (pos - 1 >= 0)
                irmaE = pai.getVLig(pos - 1);
            if (pos + 1 <= pai.getTl())
                irmaD = pai.getVLig(pos + 1);

            // redistribuicao -> a redistribuicao acontece sempre que o numero
            // de elementos de u no-irma + elementos da folha >= m * 2
            if (irmaE != null && irmaE.getTl() > No.m) {
                folha.remanejar(0);
                folha.setVPos(0, pai.getVPos(pos - 1));
                folha.setVInfo(0, pai.getVInfo(pos - 1));
                folha.setTl(folha.getTl() + 1);

                pai.setVPos(pos - 1, irmaE.getVPos(irmaE.getTl() - 1));
                pai.setVInfo(pos - 1, irmaE.getVInfo(irmaE.getTl() - 1));
                folha.setVLig(0, irmaE.getVLig(irmaE.getTl()));
                irmaE.setTl(irmaE.getTl() - 1);

                // redistribuicao -> com a irma da direita
            } else if (irmaD != null && irmaD.getTl() > No.m) {
                folha.setVInfo(folha.getTl(), pai.getVInfo(pos));
                folha.setVPos(folha.getTl(), pai.getVPos(pos));
                folha.setTl(folha.getTl() + 1);
                folha.setVLig(folha.getTl(), irmaD.getVLig(0));

                pai.setVInfo(pos, irmaD.getVInfo(0));
                pai.setVPos(pos, irmaD.getVPos(0));
                irmaD.remanejarExclusao(0);
                irmaD.setTl(irmaD.getTl() - 1);

            } else {
                if (irmaE == null) {
                    irmaE = folha;
                    folha = irmaD;
                    pos++;
                }

                irmaE.setVInfo(irmaE.getTl(), pai.getVInfo(pos - 1));
                irmaE.setVPos(irmaE.getTl(), pai.getVPos(pos - 1));
                irmaE.setTl(irmaE.getTl() + 1);
                pai.remanejarExclusao(pos - 1);
                pai.setTl(pai.getTl() - 1);
                pai.setVLig(pos - 1, irmaE);

                for (int i = 0; i < folha.getTl(); i++) {
                    irmaE.setVInfo(irmaE.getTl(), folha.getVInfo(i));
                    irmaE.setVPos(irmaE.getTl(), folha.getVPos(i));
                    irmaE.setVLig(irmaE.getTl(), folha.getVLig(i));
                    irmaE.setTl(irmaE.getTl() + 1);
                }
                irmaE.setVLig(irmaE.getTl(), folha.getVLig(folha.getTl()));

            }
            if (pai == root && pai.getTl() == 0)
                root = irmaE;

            folha = pai;
        }
    }

    private No buscaNo(int value) {
        int pos;
        No aux = root;

        pos = aux.procurarPosicao(value);
        while (aux != null && aux.getVInfo(pos) != value) {
            aux = aux.getVLig(pos);
            if (aux != null)
                pos = aux.procurarPosicao(value);
        }

        return aux;
    }

    public void remove(int value) {
        No subE, subD, folha;
        int pos, aux;

        folha = buscaNo(value);
        if (folha != null) {
            pos = folha.procurarPosicao(value);

            // tem que fazer a substituicao porque o elemento que esta sendo apagado
            // nao e uma folha e so pode ser removido folhas da arvore
            if (folha.getVLig(0) != null) {
                subE = maiorSub(folha.getVLig(pos));
                subD = menorSub(folha.getVLig(pos + 1));

                // priorizando o lado esquerdo para a substituicao
                if (subE.getTl() > No.m || subD.getTl() == No.m) {
                    folha.setVInfo(pos, subE.getVInfo(subE.getTl() - 1));
                    folha.setVPos(pos, subE.getVPos(subE.getTl() - 1));

                    // faz com que a folha agora seja a folha onde o elemento
                    // foi utilizado para substituicao
                    folha = subE;
                    // posicao logica de onde o elemento se encontra
                    pos = folha.getTl() - 1;

                    // caso para a substituicao utilizando o menor elemento
                    // da direita
                } else {
                    folha.setVInfo(pos, subD.getVInfo(0));
                    folha.setVPos(pos, subD.getVPos(0));

                    folha = subD;
                    pos = 0;
                }
            }
            folha.remanejarExclusao(pos);
            folha.setTl(folha.getTl() - 1);

            if (folha == root && folha.getTl() == 0)
                root = null;

                // aqui folha e diferente de root porque isso nao pode acontecer com a raiz
                // pois nao tem irmas
            else if (folha != root && folha.getTl() < No.m)
                redistribuiOuConcatena(folha);

        }

    }

}
