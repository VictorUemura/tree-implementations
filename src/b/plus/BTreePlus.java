package b.plus;

public class BTreePlus {
    private int n;
    private No root;

    public BTreePlus(int n) {
        this.n = n;
    }

    private No buscaFolhaInsercao(int dado) {
        No atual = root;
        int pos;
        // busca enquanto nao encontrar a folha em que vai
        // ser inserido ou o pai que deve ser inserido
        while (atual.getVFilho(0) != null) {
            pos = atual.buscaPosicao(dado);
            atual = atual.getVFilho(pos);
        }
        return atual;
    }

    private No buscaPai(No folha, int dado) {
        No pai = root;
        No atual = pai;

        // normalmente sempre precisa de pos para andar pelas ligacoes corretas
        int pos;
        while (atual != folha) {
            pai = atual;
            pos = atual.buscaPosicao(dado);
            atual = atual.getVFilho(pos);
        }

        return pai;
    }

    private void split(No no) {
        No pai;
        No cx1, cx2;
        int pos;

        while (no.getTl() == n) {
            pai = buscaPai(no, no.getVInfo(0));

            cx1 = new No(n);
            cx2 = new No(n);

            // split para no que nao e pagina
            if (no.getVFilho(0) != null) {
                // divide as duas metades como em uma btree
                for (int i = 0; i < n / 2; i++) {
                    cx1.setVInfo(i, no.getVInfo(i));
                    cx1.setVFilho(i, no.getVFilho(i));
                }
                cx1.setTl(n / 2);
                cx1.setVFilho(n / 2, no.getVFilho(n / 2));

                for (int i = n / 2 + 1; i < n; i++) {
                    cx2.setVInfo(i - n / 2 - 1, no.getVInfo(i));
                    cx2.setVFilho(i - n / 2 - 1, no.getVFilho(i));
                }
                cx2.setTl(n / 2 + (n % 2));
                cx2.setVFilho(n, no.getVFilho(n));

                if (pai != no) {
                    pos = pai.buscaPosicao(no.getVInfo(n / 2));
                    pai.remanejar(pos);
                    pai.setVInfo(pos, no.getVInfo(n / 2));
                    pai.setVFilho(pos, cx1);
                    pai.setVFilho(pos + 1, cx2);
                    pai.setTl(pai.getTl() + 1);
                } else {
                    pai.setTl(1);
                    pai.setVInfo(0, no.getVInfo(n / 2));
                    pai.setVFilho(0, cx1);
                    pai.setVFilho(1, cx2);
                }

                // split para pagina
            } else {
                // pega a primeira metade e a segunda incluindo o meio
                for (int i = 0; i < n / 2; i++)
                    cx1.setVInfo(i, no.getVInfo(i));
                cx1.setTl(n / 2);

                for (int i = n / 2; i < n; i++)
                    cx2.setVInfo(i - n / 2, no.getVInfo(i));
                cx2.setTl(n / 2 + (n % 2));

                if (no != pai) {
                    pos = pai.buscaPosicao(no.getVInfo(n / 2));
                    pai.remanejar(pos);
                    pai.setVInfo(pos, no.getVInfo(n / 2));
                    pai.setVFilho(pos, cx1);
                    pai.setVFilho(pos + 1, cx2);
                    pai.setTl(pai.getTl() + 1);
                } else {
                    pai.setVInfo(0, no.getVInfo(n / 2));
                    pai.setVFilho(0, cx1);
                    pai.setVFilho(1, cx2);
                    pai.setTl(1);
                }

                // define as ligacoes
                cx2.setProx(no.getProx());
                cx1.setProx(cx2);
                if (no != pai) {
                    pos = pai.buscaPosicao(no.getVInfo(n / 2));
                    if (pos > 0) {
                        No aux = pai.getVFilho(pos - 1);
                        aux.setProx(cx1);
                    }
                }
            }

            no = pai;
        }
    }

    public void inserir(int dado) {
        No aux;
        int pos;

        if (root == null) {
            root = new No(n);
            root.setVInfo(root.getTl(), dado);
            root.setTl(root.getTl() + 1);
        } else {
            aux = buscaFolhaInsercao(dado);
            pos = aux.buscaPosicao(dado);

            aux.remanejar(pos);
            aux.setVInfo(pos, dado);
            aux.setTl(aux.getTl() + 1);
            if (aux.getTl() == n)
                split(aux);
        }
    }

    public void inOrdem() {
        inOrdem(root);
    }

    private void inOrdem(No no) {
        if (no != null) {
            for (int i = 0; i < no.getTl(); i++) {
                if (no.getVFilho(i) != null) {
                    inOrdem(no.getVFilho(i));
                }
                System.out.print(no.getVInfo(i) + " ");
            }
            if (no.getVFilho(no.getTl()) != null) {
                inOrdem(no.getVFilho(no.getTl()));
            }
        }
        if (no == root) {
            System.out.println();
        }
    }

    public void exibeLista() {
        No aux = root;
        while (aux.getVFilho(0) != null)
            aux = aux.getVFilho(0);
        while (aux != null) {
            for (int i = 0; i < aux.getTl(); i++)
                System.out.println(aux.getVInfo(i));
            aux = aux.getProx();
        }

    }


}
