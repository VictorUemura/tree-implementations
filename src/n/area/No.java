package n.area;

public class No {

    // ligacoes que guardam os valores -> tam = N - 1
    private int vInfo[];
    // ligacoes que ficam entre os nos -> tam = N
    private No vLig[];

    private int Tl;

    // variavel para definir o numero de ligacoes para o no -> onde N deve ser maior ou igual a 2
    public static int N = 3;

    public No(int info) {
        vInfo = new int[N - 1];
        vLig = new No[N];
        Tl = 1;
        vInfo[0] = info;
    }

    public int getInfo(int pos) {
        return vInfo[pos];
    }

    public void setInfo(int pos, int info) {
        vInfo[pos] = info;
    }

    public No getLig(int pos) {
        return vLig[pos];
    }

    public void setLig(int pos, No lig) {
        vLig[pos] = lig;
    }

    public int getTl() {
        return Tl;
    }

    public void setTl(int tl) {
        Tl = tl;
    }

    public int buscaPos(int info) {
        int i = 0;
        while(i < Tl && info > vInfo[i])
            i++;
        return i;
    }

    // remanejar joga os elementos a partir de uma posição para frente tornando-a livre
    public void remanejar(int pos) {
        int i = Tl;
        while(i > pos) {
            vInfo[i] = vInfo[i - 1];
            i--;
        }
    }
}
