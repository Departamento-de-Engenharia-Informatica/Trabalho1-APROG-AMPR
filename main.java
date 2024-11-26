import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class main {
    //=====DECLARAÇÃO DE VARIÁVEIS CONSTANTES=====
    public static final double CUSTORECARGA = 5.5;
    public static final int DiaX=4;
    public static final String CAMINHO_FICHEIRO="C:/Users/afoli/Downloads/planeamento.txt";
    public static void main(String[] args) {

        //=========Declaração de variáveis=========
        int[] arrayTotalKm;
        double[] arrayMediaKmFrota;
        int[][] planeamento,matrizQntRecargas;
        double[][] matrizCargaFinalDia;
        String textoDesc;
        int l,c,ficheiro;

        Scanner ler = new Scanner(System.in);

        //===========Leitura de Dados e Saída do Planeamento (a)==========
        System.out.println("INSERIR DADOS FICHEIRO - 1\nINSERIR MANUALMENTE - 2");
        ficheiro = ler.nextInt();
        ler.nextLine();

        if (ficheiro == 1) {
            planeamento = lerPlaneamentoFicheiro(CAMINHO_FICHEIRO);
        } else if (ficheiro == 2) {
            textoDesc = ler.nextLine();
            planeamento = lerPlaneamento(ler);
        } else {
            System.out.println("Opção inválida.");
            return;
        }
        SaidaPlaneamento(planeamento);

        //=========Saída do total de Kms(b)=========
        arrayTotalKm = TotalKms(planeamento);

        //=========Recarga das Baterias(c)=========
        matrizQntRecargas = RecargaBaterias(planeamento);

        //=========Carga Final do Dia(d)=========
        matrizCargaFinalDia = CargaFinalDia(planeamento);

        //=========Media Km Frota(e)=========
        arrayMediaKmFrota = MediaKmFrota(planeamento);

        //=======Desl. Acima da Média da Frota(f)=======
        DeslAcimaMedia(planeamento,arrayMediaKmFrota);

        //=====Carregados Mais Dias Consecutivos(g)=====
        CarrDiasConsec(matrizQntRecargas);

        //=====Dias Mais Tardio Carregamento Global(h)=====
        DiaMaisTardio(matrizQntRecargas);

        //======Custo das Recargas da Frota(i)======
        CustoRecargas(matrizQntRecargas);

        //=====Veículo de Prevenção no dia X(j)=====
        VeiculoPrevencao(planeamento,matrizCargaFinalDia);
    }
    public static void VeiculoPrevencao(int[][] planeamento, double[][] matrizCargaFinalDia) {
        int veiculoPrevencao = -1;
        int menorUso = Integer.MAX_VALUE;
        double maiorCarga = -1;

        for (int i = 0; i < planeamento.length; i++) {
            int usoDoDia = planeamento[i][DiaX];
            double cargaFinal = matrizCargaFinalDia[i][DiaX];
            if (usoDoDia < menorUso || (usoDoDia == menorUso && cargaFinal > maiorCarga) || (usoDoDia == menorUso && cargaFinal == maiorCarga && i < veiculoPrevencao)) {
                veiculoPrevencao = i;
                menorUso = usoDoDia;
                maiorCarga = cargaFinal;
            }
        }

        if (veiculoPrevencao != -1) {
            System.out.println("\nj) Veículo de prevenção no dia " + DiaX + " : V" + veiculoPrevencao);
        } else {
            System.out.println("Nenhum veículo disponível para prevenção.");
        }
    }
    public static void CustoRecargas(int[][] matrizQntRecargas){
        int nrRecargas=0;
        for(int i=0;i<matrizQntRecargas.length; i++){
            for(int j=0; j<matrizQntRecargas[i].length;j++)nrRecargas+=matrizQntRecargas[i][j];
        }
        if (nrRecargas != 0) System.out.println(String.format("\ni) custo das recargas da frota: %.2f€", nrRecargas * CUSTORECARGA));
        else System.out.println("Não existem recargas a fazer (Custo: 0€)");
    }
    public static void DiaMaisTardio(int[][] matrizQntRecargas){
        boolean carregamentoGeral=false;
        int dia=0;
        while(!carregamentoGeral && dia<matrizQntRecargas[0].length){
            boolean carregamentoGeralAux = true;
            for(int i=0;i<matrizQntRecargas.length; i++){
                if(matrizQntRecargas[i][dia]==0) carregamentoGeralAux =false;
            }
            if(carregamentoGeralAux) carregamentoGeral=true;
            dia++;
        }
        if (carregamentoGeral) System.out.println("\nh) Dia mais tardio em que todos os veículos necessitam de recarregar: " + (dia-1));
        else System.out.println("\nh) Não há dia em que todos os veículos necessitam de recarga.");

    }
    public static void CarrDiasConsec(int[][] matrizQntRecargas){
        int dias,diasVeiculo,maiorSeq=0;
        String veiculos="";
        for(int i=0;i<matrizQntRecargas.length; i++){
            dias=0;
            diasVeiculo=0;
            for (int j = 0; j < matrizQntRecargas[i].length; j++) {
                if (matrizQntRecargas[i][j] != 0) dias++;
                else {
                    if (dias > diasVeiculo) diasVeiculo = dias;
                    dias = 0;
                }
            }

            if (dias > diasVeiculo) diasVeiculo = dias;
            if(diasVeiculo>maiorSeq){
                maiorSeq=diasVeiculo;
                veiculos="[ V"+i+" ]";
            }
            else if(diasVeiculo==maiorSeq) veiculos+=" [ V"+i+" ]";
        }
        System.out.println("\ng) veículos com mais dias consecutivas a necessitar de recarga");
        if(maiorSeq>0) System.out.println(maiorSeq + " dias consecutivos, veículos : " + veiculos);
        else System.out.println("Os veículos não carregam dias consecutivos");
    }
    public static void DeslAcimaMedia(int[][] planeamento, double[]arrayMediaKmFrota){
        boolean acima;
        String veiculos="";
        int count=0;
        System.out.println("\nf) deslocações sempre acima da média diária");
        for(int i=0;i<planeamento.length; i++){
            acima=true;
            for(int j=0;j<planeamento[i].length;j++) if(planeamento[i][j]<arrayMediaKmFrota[i]) acima=false;
            if(acima){
                count+=1;
                veiculos+="[ V"+i+" ] ";
            }
        }
        if(count>=1) System.out.println(count + " veículos : "+ veiculos);
        else System.out.println("Não existem veículos com deslocações sempre acima da média diária");
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
                if (cargaAtual <= planeamento[i][j]) {
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
        int l, c;
        do {
            System.out.println("Digite o número de linhas (veículos) e o número de dias (colunas):");
            l = ler.nextInt();
            c = ler.nextInt();
            if (l <= 0 || c <= 0) System.out.println("Erro: O número de linhas e colunas deve ser positivo.\n");
            else if (c < DiaX) System.out.println("Erro: DiaX (" + DiaX + ") é maior que o número de dias (colunas).\n");
        } while (l <= 0 || c <= 0 || c < DiaX);
        System.out.println("Insira o planeamento (valores da matriz, positivos):");
        int[][] planeamento = new int[l][c];

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                int valor;
                do {
                    valor = ler.nextInt();
                    if (valor < 0) System.out.println("Erro: Valor tem de ser positivo.");
                } while (valor < 0);
                planeamento[i][j] = valor;
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
    public static int[][] lerPlaneamentoFicheiro(String caminhoFicheiro) {
        try (Scanner ficheiro = new Scanner(new File(caminhoFicheiro))) {
            String descricao = ficheiro.nextLine();
            System.out.println("Descrição do ficheiro: " + descricao);

            int l = ficheiro.nextInt();
            int c = ficheiro.nextInt();

            int[][] planeamento = new int[l][c];
            for (int i = 0; i < l; i++) {
                for (int j = 0; j < c; j++) {
                    planeamento[i][j] = ficheiro.nextInt();
                }
            }
            return planeamento;
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado: " + e.getMessage());
            return null;
        }
    }
}