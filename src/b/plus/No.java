package b.plus;

public class No {
    private int vInfo[];
    private int tl;
    private No vFilho[];
    private No prox;

    public No(int n) {
        vInfo = new int[n];
        tl = 0;
        vFilho = new No[n + 1];
    }

    public void remanejar(int pos) {
        vFilho[tl + 1] = vFilho[tl];
        for (int i = tl; i > pos; i--) {
            vFilho[i] = vFilho[i - 1];
            vInfo[i] = vInfo[i - 1];
        }
    }

    public int buscaPosicao(int dado) {
        int i = 0;
        while (i < tl && vInfo[i] < dado)
            i++;
        return i;
    }

    public int getVInfo(int pos) {
        return vInfo[pos];
    }

    public No getVFilho(int pos) {
        return vFilho[pos];
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }

    public void setVInfo(int pos, int info) {
        vInfo[pos] = info;
    }

    public void setVFilho(int pos, No filho) {
        vFilho[pos] = filho;
    }

    public void setTl(int tl) {
        this.tl = tl;
    }

    public int getTl() {
        return tl;
    }
}
