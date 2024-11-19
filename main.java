import java.util.Scanner;

public class main {

    public static int[][] lerPlaneamento(Scanner ler) {
        int l = ler.nextInt();
        int c = ler.nextInt();

        int[][] planeamento = new int[l][c];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                planeamento[i][j] = ler.nextInt();
            }
        }
        return planeamento;
    }

    public static void main(String[] args) {

        //=========Declaração de variáveis=========
        String textoDesc;
        int l,c;
        Scanner ler = new Scanner(System.in);

        //===========Leitura de Dados e Saída do Planeamento (a)==========
        textoDesc = ler.nextLine();
        int[][] planeamento = lerPlaneamento(ler);
        SaidaPlaneamento(planeamento);

        //=========Saída do total de Kms(b)=========
        totalKms(planeamento);

    }















    public static void SaidaPlaneamento(int[][] planeamento) {
        System.out.printf("a) planeamento (km/dia/veículo)\n");
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

    public static void totalKms(int[][] planeamento) {
        System.out.printf("\nb) total de km a percorrer\n");
        for (int i = 0; i < planeamento.length; i++) {
            int totalKms = 0;
            for (int j = 0; j < planeamento[0].length; j++) {
                totalKms += planeamento[i][j];
            }
            System.out.printf("V%-1d :     %4d km\n", i, totalKms);
        }
    }

}
