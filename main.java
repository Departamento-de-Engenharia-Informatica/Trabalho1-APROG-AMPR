import java.util.Scanner;

public class main {

    public static void main(String[] args) {

        //=========Declaração de variáveis=========
        int[] arrayTotalKm;
        double[] arrayMediaKmFrota;
        int[][] planeamento,matrizQntRecargas;
        double[][] matrizCargaFinalDia;
        String textoDesc;
        int l,c;
        Scanner ler = new Scanner(System.in);

        //===========Leitura de Dados e Saída do Planeamento (a)==========
        textoDesc = ler.nextLine();
        planeamento = lerPlaneamento(ler);
        SaidaPlaneamento(planeamento);

        //=========Saída do total de Kms(b)=========
        arrayTotalKm = TotalKms(planeamento);

        //=========Recarga das Baterias(c)=========
        matrizQntRecargas = RecargaBaterias(planeamento);

        //=========Carga Final do Dia(d)=========
        matrizCargaFinalDia = CargaFinalDia(planeamento);

        //=========Media Km Frota(e)=========
        arrayMediaKmFrota = MediaKmFrota(planeamento);
    }
    public static double[] MediaKmFrota(int[][] planeamento) {
        double[] arrayMediaKmFrota = new double[planeamento[0].length];
        //OBTER ARRAY
        for (int j = 0; j < planeamento[0].length; j++) {
            int soma = 0;
            for (int i = 0; i < planeamento.length; i++) {
                soma += planeamento[i][j];
            }
            arrayMediaKmFrota[j] = (double) soma / planeamento.length;
        }
        //VISUALIZAR ARRAY
        System.out.println("\ne) Média de km diários da frota:");
        System.out.printf("%4s", "Dia:");
        for (int j = 0; j < planeamento[0].length; j++) {
            System.out.printf(" %8d", j);
        }
        System.out.println();
        System.out.print("----|");
        for (int j = 0; j < planeamento[0].length; j++) {
            System.out.print("--------|");
        }
        System.out.println();
        System.out.printf("Km  :");
        for (int j = 0; j < arrayMediaKmFrota.length; j++) {
            System.out.printf(" %7.1f ", arrayMediaKmFrota[j]);
        }
        System.out.println();

        return arrayMediaKmFrota;
    }
    public static int[][] RecargaBaterias(int[][] planeamento) {
        int cargaAtual, nrCargas;
        int[][] matrizQntRecargas = new int[planeamento.length][planeamento[0].length];
        //OBTER MATRIZ
        for (int i = 0; i < planeamento.length; i++) {
            cargaAtual = 100;
            for (int j = 0; j < planeamento[i].length; j++) {
                nrCargas = 0;
                if (cargaAtual < planeamento[i][j]) {
                    while (cargaAtual <= planeamento[i][j]) {
                        cargaAtual += 100;
                        nrCargas++;
                    }
                }
                cargaAtual -= planeamento[i][j];
                matrizQntRecargas[i][j] = nrCargas;
            }
        }

        //VISUALIZAR MATRIZ
        System.out.println("\nc) Recargas das baterias:");
        System.out.printf("%4s", "dia:");
        for (int j = 0; j < planeamento[0].length; j++) {
            System.out.printf(" %8d", j);
        }
        System.out.println();
        System.out.print("----|");
        for (int j = 0; j < planeamento[0].length; j++) {
            System.out.print("--------|");
        }
        System.out.println();
        for (int i = 0; i < matrizQntRecargas.length; i++) {
            System.out.printf("V%d  :", i);
            for (int j = 0; j < matrizQntRecargas[i].length; j++) {
                System.out.printf(" %7d ", matrizQntRecargas[i][j]);
            }
            System.out.println();
        }

        return matrizQntRecargas;
    }
    public static int[] TotalKms(int[][] planeamento) {
        System.out.printf("\nb) total de km a percorrer\n");
        int[] totalKmArray = new int[planeamento.length];
        //OBTER ARRAY
        for (int i = 0; i < planeamento.length; i++) {
            int totalKms = 0;
            for (int j = 0; j < planeamento[i].length; j++) {
                totalKms += planeamento[i][j];
            }
            totalKmArray[i] = totalKms;
        }
        //VISUALIZAR ARRAY
        for(int i=0;i<totalKmArray.length; i++) System.out.printf("V%-1d :     %4d km\n", i, totalKmArray[i]);
        return totalKmArray;
    }
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
    public static double[][] CargaFinalDia(int[][] planeamento) {
        double[][] matrizCargaFinalDia = new double[planeamento.length][planeamento[0].length];
        //OBTER MATRIZ
        for (int i = 0; i < planeamento.length; i++) {
            float bateria = 100;
            for (int j = 0; j < planeamento[i].length; j++) {
                bateria -= planeamento[i][j];
                while (bateria <= 0) {
                    bateria += 100;
                }
                matrizCargaFinalDia[i][j] = bateria;
            }
        }
        //VISUALIZAR MATRIZ
        System.out.println("\nd) Carga final das baterias:");
        System.out.printf("%4s", "dia:");
        for (int j = 0; j < planeamento[0].length; j++) {
            System.out.printf(" %8d", j);
        }
        System.out.println();

        System.out.print("----|");
        for (int j = 0; j < planeamento[0].length; j++) {
            System.out.print("--------|");
        }
        System.out.println();

        for (int i = 0; i < matrizCargaFinalDia.length; i++) {
            System.out.printf("V%d  :", i);
            for (int j = 0; j < matrizCargaFinalDia[i].length; j++) {
                System.out.printf("%7.1f%% ", matrizCargaFinalDia[i][j]);
            }
            System.out.println();
        }
        return matrizCargaFinalDia;
    }
    public static void SaidaPlaneamento(int[][] planeamento) {
        System.out.printf("a) planeamento (km/dia/veículo)\n");
        System.out.printf("%4s", "dia:");
        for (int j = 0; j < planeamento[0].length; j++) {
            System.out.printf(" %8d", j);
        }
        System.out.println();

        System.out.print("----|");
        for (int j = 0; j < planeamento[0].length; j++) {
            System.out.print("--------|");
        }
        System.out.println();

        for (int i = 0; i < planeamento.length; i++) {
            System.out.printf("V%d  :", i);
            for (int j = 0; j < planeamento[i].length; j++) {
                System.out.printf(" %7d ", planeamento[i][j]);
            }
            System.out.println();
        }
    }

}
