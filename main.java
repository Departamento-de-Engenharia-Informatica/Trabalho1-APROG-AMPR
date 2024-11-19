import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        //=======Declaração de variáveis=======
        String textoDesc;
        int l,c;
        Scanner ler = new Scanner(System.in);

        //===========Leitura de Dados==========
        textoDesc = ler.nextLine();
        l=ler.nextInt();
        c=ler.nextInt();
        int[][] planeamento = new int[l][c];
        for(int i=0 ; i<l; i++) for(int j=0 ; j<c; j++) planeamento[i][j]=ler.nextInt();

    }
}
