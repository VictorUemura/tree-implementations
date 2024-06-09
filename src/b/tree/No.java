package b.tree;

public class No {
    public static int m = 2;

    private int vInfo[];
    private No vLig[];
    private int vPos[]; // -> Para que serve um vetor de posicao?
    private int tl;

    public No() {
        vInfo = new int[m * 2 + 1];
        vPos = new int[m * 2 + 1];
        vLig = new No[m * 2 + 2];
        tl = 0;
    }

    public No(int info, int posArq) {
        this();
        vInfo[0] = info;
        vPos[0] = posArq;
        tl++;
    }

    public int getVInfo(int pos) {
        return vInfo[pos];
    }

    public void setVInfo(int pos, int info) {
        vInfo[pos] = info;
    }

    public int getVPos(int pos) {
        return vPos[pos];
    }

    public void setVPos(int pos, int posArq) {
        vPos[pos] = posArq;
    }

    public No getVLig(int pos) {
        return vLig[pos];
    }

    public void setVLig(int pos, No lig) {
        vLig[pos] = lig;
    }

    public int getTl() {
        return tl;
    }

    public void setTl(int tl) {
        this.tl = tl;
    }

    public int procurarPosicao(int info) {
        int pos = 0;
        while (pos < tl && vInfo[pos] < info)
            pos++;
        return pos;
    }

    public void remanejar(int pos) {
        vLig[tl + 1] = vLig[tl];
        for (int i = tl; i > pos; i--) {
            vInfo[i] = vInfo[i - 1];
            vPos[i] = vPos[i - 1];
            vLig[i] = vLig[i - 1];
        }
    }

    public void remanejarExclusao(int pos) {
        for (int i = pos; i < tl; i++) {
            vPos[i] = vPos[i + 1];
            vLig[i] = vLig[i + 1];
            vInfo[i] = vInfo[i + 1];
        }
        vLig[tl] = vLig[tl + 1];
    }
}
