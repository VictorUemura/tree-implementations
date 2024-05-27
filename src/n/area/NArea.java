package n.area;

public class NArea {
    private No raiz;

    public NArea() {
        raiz = null;
    }

    public No getRaiz() {
        return raiz;
    }

    public void insere(int info) {
        // 1 caso -> raiz é nulo
        if(raiz == null) {
            raiz = new No(info);
        }
        // proximos casos
        else {
            No aux = raiz;
            int pos;
            boolean flag = false;
            // precisa da iteração para andar pela árvore até encontrar a posição ideal
            while(!flag) {
                pos = aux.buscaPos(info);
                // 2 caso -> as posicoes de elementos nao estao totalmente preenchidas
                if(aux.getTl() < No.N - 1) {
                    aux.remanejar(pos);
                    aux.setInfo(pos, info);
                    aux.setTl(aux.getTl() + 1);
                    flag = true;
                    // 3 caso -> a posicao da ligação que deve ser inserido é nulo e assim, deve ser inserido um novo nó
                } else if(aux.getLig(pos) == null) {
                    aux.setLig(pos, new No(info));
                    flag = true;
                    // 4 caso -> a posição da ligação deve ser inserido não é nulo e já contém elementos
                } else {
                    aux = aux.getLig(pos);
                }
            }
        }
    }

    public void inOrdem() {
        System.out.println("Exibicao in-ordem da arvore:");
        inOrdem(raiz);
    }

    public void inOrdem(No no) {
        // É preciso verificar se o no tem valor nulo pois é preciso parar de chamar a pilha de recursão
        if (no != null) {
            for (int i = 0; i < no.getTl(); i++) {
                inOrdem(no.getLig(i));
                System.out.println(no.getInfo(i));
            }
            // É preciso exibir o último valor que tem o valor de Tl pois é a última ligação que não é exibida
            inOrdem(no.getLig(no.getTl()));
        }
    }
}
