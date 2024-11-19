import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        //=========Declaração de variáveis=========
        String textoDesc;
        int l,c;
        Scanner ler = new Scanner(System.in);

        //===========Leitura de Dados (a)==========
        textoDesc = ler.nextLine();
        l=ler.nextInt();
        c=ler.nextInt();
        int[][] planeamento = new int[l][c];
        for(int i=0 ; i<l; i++) for(int j=0 ; j<c; j++) planeamento[i][j]=ler.nextInt();

        //=========Saída do Planeamento(b)=========
        SaidaPlaneamento(planeamento);
    }















    public static void SaidaPlaneamento(int[][] planeamento) {


        System.out.printf("%-6s", "dia:");
        for (int j = 0; j < planeamento[0].length; j++) {
            System.out.printf("|%6d", j);
        }
        System.out.printf("%n");
        System.out.print("------");
        for (int j = 0; j < planeamento[0].length; j++) {
            System.out.print("|------");
        }
        System.out.printf("%n");

        for (int i = 0; i < planeamento.length; i++) {
            System.out.printf("%-6s", "V" + i + ":");
            for (int j = 0; j < planeamento[0].length; j++) {
                System.out.printf("|%6d", planeamento[i][j]);
            }
            System.out.printf("%n");
        }
    }


}
