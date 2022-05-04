/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winerna;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static winerna.GeraCSV.geraCSV;

/**
 *
 * @author jeffersonpasserini
 */
public class WineRNA {

    //atributos gerais
    static public List<Wine> baseDados = new ArrayList<Wine>();
    //array de resultado
    static public List<Wine> resultado = new ArrayList<Wine>();
    
    //base individuais Neuronio Classe 01 / 02 /03
    static public double[][] baseTreina;
    static public double[][] baseValida;
    static public double[][] baseTeste;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        //estatistica final
        ArrayList<Estatistica> lstEstFinal = new ArrayList<Estatistica>();
        
        //define padrões de execução
        double bias = 1;
        double taxaAprendizado = 0.01;
        double[] pesoW = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int nroIteracoes = 20000;
        
        boolean geraBaseValidacao = false;
        boolean normalizaBase = true;
        
        //dados arquivo base de dados
        String arquivo = "/home/jeffersonpasserini/dados/BaseWine/wine.data";
        
        List<Wine> base = readFile(arquivo);
        
        //verifica se os dados irão ser normalizados.
        if(normalizaBase)
            baseDados = normalizaBase(base);
        else
            baseDados = base;
        
        //monta laço para n testes
        for(int nroTestes=0; nroTestes<100; nroTestes++)
        {
            System.out.println("Execução numero: "+(nroTestes+1) );
            double[] wC1 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            double[] wC2;
            double[] wC3;           
           
            /* ------treinamento dos neuronios------- */
            //define neuronio artifical para Classe 1 de vinhos
            Perceptron oRnaC1 = new Perceptron();
            oRnaC1.setAlfa(taxaAprendizado);
            oRnaC1.setBias(bias);
            oRnaC1.setNET(bias);
            oRnaC1.setW(pesoW);
            oRnaC1.setMaxIte(nroIteracoes);
                       
            //sorteia bases para classe 1
            System.out.println("Gerando bases...");
            sorteiaBases(baseDados, geraBaseValidacao, 1);
            
            System.out.println("Treinamento neuronio Classe 1...");
            if (geraBaseValidacao)
                wC1 = oRnaC1.treinar(baseTreina, baseValida);
            else
                wC1 = oRnaC1.treinar(baseTreina);
                        
            //reclassifica bases para classe 2
            System.out.println("Atualizando bases...");
            atualizaBases(2);
            
            
            //define neuronio artifical para Classe 2 de vinhos
            Perceptron oRnaC2 = new Perceptron();
            oRnaC2.setAlfa(taxaAprendizado);
            oRnaC2.setBias(bias);
            oRnaC2.setNET(bias);
            oRnaC2.setW(pesoW);
            oRnaC2.setMaxIte(nroIteracoes);
            
            System.out.println("Treinamento neuronio Classe 2...");
            if (geraBaseValidacao)
                wC2 = oRnaC2.treinar(baseTreina, baseValida);
            else
                wC2 = oRnaC2.treinar(baseTreina);
            
            //reclassifica bases para classe 3
            System.out.println("Atualizando bases...");
            atualizaBases(3);
            
            //define neuronio artifical para Classe 3 de vinhos
            Perceptron oRnaC3 = new Perceptron();
            oRnaC3.setAlfa(taxaAprendizado);
            oRnaC3.setBias(bias);
            oRnaC3.setNET(bias);
            oRnaC3.setW(pesoW);
            oRnaC3.setMaxIte(nroIteracoes);
            
            System.out.println("Treinamento neuronio Classe 3...");
            if (geraBaseValidacao)
                wC3 = oRnaC3.treinar(baseTreina, baseValida);
            else
                wC3 = oRnaC3.treinar(baseTreina);
            
            
            Perceptron oRnaSaida = new Perceptron();
            oRnaSaida.setAlfa(taxaAprendizado);
            oRnaSaida.setBias(bias);
            oRnaSaida.setNET(bias);
            oRnaSaida.setW(pesoW);
            oRnaSaida.setMaxIte(nroIteracoes);
            
            System.out.println("Executando classificação da base de testes...");
            List<Wine> baseClassificada = oRnaSaida.executarRNA(baseTeste, wC1, wC2, wC3);
            
            System.out.println("Gerando Estatisticas...");
            int acertos=0, erros=0;
            for(Wine oWine : baseClassificada)
            {    
                if (oWine.getClassWine() == oWine.getClassificacao())
                    acertos++;
                else
                    erros++;
            }
            Estatistica oEst = new Estatistica(String.valueOf(acertos),String.valueOf(erros),"0","0");
            lstEstFinal.add(oEst);
        }
        
        geraCSV(lstEstFinal,"/home/jeffersonpasserini/dados/BaseWine/resfinal.csv"); 
    }
    
    static private List<Wine> readFile(String arquivo)
    {
        List<Wine> lstBase = new ArrayList<Wine>();
        try{
          
          BufferedReader base = new BufferedReader(new FileReader(arquivo));
          while(base.ready())
          {
             //realiza a leitura linha a linha
             String lineBase = base.readLine();
             // os valores na linha pelo separador ","
             String[] valueFields = lineBase.split(",");
             //converte os valores lidos de String para double.            
             Wine oWine = new Wine();
             oWine.setClassWine(Integer.parseInt(valueFields[0]));
             oWine.setA1(Double.parseDouble(valueFields[1]));
             oWine.setA2(Double.parseDouble(valueFields[2]));
             oWine.setA3(Double.parseDouble(valueFields[3]));
             oWine.setA4(Double.parseDouble(valueFields[4]));
             oWine.setA5(Double.parseDouble(valueFields[5]));
             oWine.setA6(Double.parseDouble(valueFields[6]));
             oWine.setA7(Double.parseDouble(valueFields[7]));
             oWine.setA8(Double.parseDouble(valueFields[8]));
             oWine.setA9(Double.parseDouble(valueFields[9]));
             oWine.setA10(Double.parseDouble(valueFields[10]));
             oWine.setA11(Double.parseDouble(valueFields[11]));
             oWine.setA12(Double.parseDouble(valueFields[12]));
             oWine.setA13(Double.parseDouble(valueFields[13]));
             
             lstBase.add(oWine);
          }
          base.close();
          
        }catch(IOException e)
        {
           e.printStackTrace();
        }
        return lstBase;
    }
    
    static public double calcularMedia(double[] listaValores)
    {
        double resultadoMedia = 0;
        double somatorio = 0;
        int tamanhoVetor = listaValores.length;

        for (int i = 0; i < tamanhoVetor; i++)
        {
            somatorio = somatorio + listaValores[i];
        }

        resultadoMedia = somatorio / tamanhoVetor;

        return resultadoMedia;
    }
    
    static public double calcularDesvioPadrao(double[] listaValores)
    {
        double media = calcularMedia(listaValores);
        double somatorio = 0;
        int tamanhoVetor = listaValores.length;

        for (int i = 0; i < tamanhoVetor; i++)
        {
            somatorio = somatorio+ Math.pow(listaValores[i]-media, 2);
        }

        double valorVariancia = 0;
        double valorDesvioPadrao = 0;

        // soma dos quadrados da diferença entre cada valor e
        // a média aritmética, dividida pela quantidade de elementos observados.
        valorVariancia = somatorio/tamanhoVetor;

        // Desvio padrão é a raiz quadrada da variância.
        valorDesvioPadrao = Math.sqrt(valorVariancia);

        return valorDesvioPadrao;
    }
    
    static public List<Wine> normalizaBase(List<Wine> lstWine)
    {
        //define listas individuais de cada atributo
        double[] a1 = new double[lstWine.size()];
        double[] a2 = new double[lstWine.size()];
        double[] a3 = new double[lstWine.size()];
        double[] a4 = new double[lstWine.size()];
        double[] a5 = new double[lstWine.size()];
        double[] a6 = new double[lstWine.size()];
        double[] a7 = new double[lstWine.size()];
        double[] a8 = new double[lstWine.size()];
        double[] a9 = new double[lstWine.size()];
        double[] a10 = new double[lstWine.size()];
        double[] a11 = new double[lstWine.size()];
        double[] a12 = new double[lstWine.size()];
        double[] a13 = new double[lstWine.size()];
        
        //gera vetores
        int contaElementos = 0;
        for(Wine oWine : lstWine)
        {
            a1[contaElementos] = oWine.getA1();
            a2[contaElementos] = oWine.getA2();
            a3[contaElementos] = oWine.getA3();
            a4[contaElementos] = oWine.getA4();
            a5[contaElementos] = oWine.getA5();
            a6[contaElementos] = oWine.getA6();
            a7[contaElementos] = oWine.getA7();
            a8[contaElementos] = oWine.getA8();
            a9[contaElementos] = oWine.getA9();
            a10[contaElementos] = oWine.getA10();
            a11[contaElementos] = oWine.getA11();
            a12[contaElementos] = oWine.getA12();
            a13[contaElementos] = oWine.getA13();
            contaElementos++;
        }
        //calcula média aritmética e desvio padrão de cada vetor
        double dvA1 = calcularDesvioPadrao(a1);
        double mediaA1 = calcularMedia(a1);

        double dvA2 = calcularDesvioPadrao(a2);
        double mediaA2 = calcularMedia(a2);

        double dvA3 = calcularDesvioPadrao(a3);
        double mediaA3 = calcularMedia(a3);

        double dvA4 = calcularDesvioPadrao(a4);
        double mediaA4 = calcularMedia(a4);

        double dvA5 = calcularDesvioPadrao(a5);
        double mediaA5 = calcularMedia(a5);

        double dvA6 = calcularDesvioPadrao(a6);
        double mediaA6 = calcularMedia(a6);

        double dvA7 = calcularDesvioPadrao(a7);
        double mediaA7 = calcularMedia(a7);

        double dvA8 = calcularDesvioPadrao(a8);
        double mediaA8 = calcularMedia(a8);

        double dvA9 = calcularDesvioPadrao(a9);
        double mediaA9 = calcularMedia(a9);

        double dvA10 = calcularDesvioPadrao(a10);
        double mediaA10 = calcularMedia(a10);

        double dvA11 = calcularDesvioPadrao(a11);
        double mediaA11 = calcularMedia(a11);

        double dvA12 = calcularDesvioPadrao(a12);
        double mediaA12 = calcularMedia(a12);

        double dvA13 = calcularDesvioPadrao(a13);
        double mediaA13 = calcularMedia(a13);

        //normaliza elementos da base
        List<Wine> baseNormalizada = new ArrayList<Wine>();
        for(Wine oWine : lstWine)
        {
            Wine oWineNorm = new Wine();
            oWineNorm.setA1((oWine.getA1()-mediaA1)/dvA1);
            oWineNorm.setA2((oWine.getA2()-mediaA2)/dvA2);
            oWineNorm.setA3((oWine.getA3()-mediaA3)/dvA3);
            oWineNorm.setA4((oWine.getA4()-mediaA4)/dvA4);
            oWineNorm.setA5((oWine.getA5()-mediaA5)/dvA5);
            oWineNorm.setA6((oWine.getA6()-mediaA6)/dvA6);
            oWineNorm.setA7((oWine.getA7()-mediaA7)/dvA7);
            oWineNorm.setA8((oWine.getA8()-mediaA8)/dvA8);
            oWineNorm.setA9((oWine.getA9()-mediaA9)/dvA9);
            oWineNorm.setA10((oWine.getA10()-mediaA10)/dvA10);
            oWineNorm.setA11((oWine.getA11()-mediaA11)/dvA11);
            oWineNorm.setA12((oWine.getA12()-mediaA12)/dvA12);
            oWineNorm.setA13((oWine.getA13()-mediaA13)/dvA13);
            oWineNorm.setClassWine(oWine.getClassWine());
            
            baseNormalizada.add(oWineNorm);
            
        }

        return baseNormalizada;

    }

    static public void sorteiaBases(List<Wine> lstWine, boolean valida, int classe)
    {
            //define percentual da divisão da base de dados
            double percTreina = 0, percValida = 0, percTeste = 0;
            if (valida)
            {
                percTreina = 0.50; percValida = 0.20; percTeste = 0.30;
            }
            else
            {
                percTreina = 0.70; percValida = 0; percTeste = 0.3;
            }

            //conta nro de casos
            int nroC1 = 0, nroC2 = 0, nroC3 = 0;
            int nroBaseTreinaC1 = 0, nroBaseTreinaC2 = 0, nroBaseTreinaC3 = 0;
            int nroBaseValidaC1 = 0, nroBaseValidaC2 = 0, nroBaseValidaC3 = 0;
            int nroBaseTesteC1 = 0, nroBaseTesteC2 = 0, nroBaseTesteC3 = 0;
            
            //conta numero de ocorrencias de cada classe na base de dados
            for (Wine oWine : lstWine)
            {
                if (oWine.getClassWine() == 1)
                    nroC1++;
                else if (oWine.getClassWine() == 2)
                    nroC2++;
                else if (oWine.getClassWine() == 3)
                    nroC3++;
            }

            //define tamanho de cada tipo de base para cada classe
            nroBaseTreinaC1 = (int) (nroC1 * percTreina);
            nroBaseTreinaC2 = (int) (nroC2 * percTreina);
            nroBaseTreinaC3 = (int) (nroC3 * percTreina);

            nroBaseValidaC1 = (int) (nroC1 * percValida);
            nroBaseValidaC2 = (int) (nroC2 * percValida);
            nroBaseValidaC3 = (int) (nroC3 * percValida);

            nroBaseTesteC1 = (nroC1 - (nroBaseTreinaC1 + nroBaseValidaC1));
            nroBaseTesteC2 = (nroC2 - (nroBaseTreinaC2 + nroBaseValidaC2));
            nroBaseTesteC3 = (nroC3 - (nroBaseTreinaC3 + nroBaseValidaC3));

            //declara o tamanho de cada base
            int tamBaseTreina = 0, tamBaseValida = 0, tamBaseTeste = 0;
            if (nroBaseValidaC1 > 0)
            {
                //se houver validação
                tamBaseTreina = nroBaseTreinaC1 + nroBaseTreinaC2 + nroBaseTreinaC3;
                tamBaseValida = nroBaseValidaC1 + nroBaseValidaC2 + nroBaseValidaC3;
                tamBaseTeste = lstWine.size() - (tamBaseTreina + tamBaseValida);
                baseTreina = new double[tamBaseTreina][15];
                baseValida = new double[tamBaseValida][15];
                baseTeste = new double[tamBaseTeste][15];
            }
            else
            {
                //se não houver validação
                tamBaseTreina = nroBaseTreinaC1 + nroBaseTreinaC2 + nroBaseTreinaC3;
                tamBaseValida = 0;
                tamBaseTeste = lstWine.size() - (tamBaseTreina + tamBaseValida);
                baseTreina = new double[tamBaseTreina][15];
                baseTeste = new double[tamBaseTeste][15];
            }

            int sorteia = 0;
            int ctaBase1Classe1 = 0, ctaBase1Classe2 = 0, ctaBase1Classe3 = 0;
            int ctaBase2Classe1 = 0, ctaBase2Classe2 = 0, ctaBase2Classe3 = 0;
            int ctaBase3Classe1 = 0, ctaBase3Classe2 = 0, ctaBase3Classe3 = 0;
            int linhaBase1 = 0, linhaBase2 = 0, linhaBase3 = 0;

            Random randBase = new Random();

            for (Wine oSepara : lstWine)
            {
                //seleciona 0 ou 1 se for a classe de interesse
                int valorD = 0;
                if (oSepara.getClassWine() == classe)
                    valorD = 1;

                /* sorteia qual base vai pertencer o elemento
                 * proporcional ao tamanho da base
                 * respeitando o tamanho das bases
                 */
                Boolean passa;
                do
                {
                    passa = true;
                    int nroRandomico = randBase.nextInt(99);
                    
                    if (tamBaseValida > 0)
                    {
                        if (nroRandomico <= 69)
                            sorteia = 1;
                        else if (nroRandomico > 69 && nroRandomico <= 84)
                            sorteia = 2;
                        else if (nroRandomico > 84 && nroRandomico <= 99)
                            sorteia = 3;
                    }
                    else
                    {
                        if (nroRandomico <= 69)
                            sorteia = 1;
                        else if (nroRandomico > 69 && nroRandomico <= 99)
                            sorteia = 2;
                    }

                    int classeElemento = oSepara.getClassWine();
                    
                    //valida
                    if (sorteia == 1 && linhaBase1 < tamBaseTreina &&
                       ((oSepara.getClassWine() == 1 && ctaBase1Classe1 < nroBaseTreinaC1) 
                       || (oSepara.getClassWine() == 2 && ctaBase1Classe2 < nroBaseTreinaC2) 
                       || (oSepara.getClassWine() == 3 && ctaBase1Classe3 < nroBaseTreinaC3)))
                        
                        passa = false;
                    
                    else if (sorteia == 2 && linhaBase2 < tamBaseTeste &&
                         ((oSepara.getClassWine() == 1 && ctaBase2Classe1 < nroBaseTesteC1) 
                         || (oSepara.getClassWine() == 2 && ctaBase2Classe2 < nroBaseTesteC2) 
                         || (oSepara.getClassWine() == 3 && ctaBase2Classe3 < nroBaseTesteC3)))
                        
                        passa = false;
                    
                    else if (sorteia == 3 && linhaBase3 < tamBaseValida &&
                         ((oSepara.getClassWine() == 1 && ctaBase3Classe1 < nroBaseValidaC1) 
                         || (oSepara.getClassWine() == 2 && ctaBase3Classe2 < nroBaseValidaC2) 
                         || (oSepara.getClassWine() == 3 && ctaBase3Classe3 < nroBaseValidaC3)))
                        
                        passa = false;
                    
                } while (passa);

                if (sorteia == 1)
                {
                    //base treinamento
                    baseTreina[linhaBase1][0] = oSepara.getA1();
                    baseTreina[linhaBase1][1] = oSepara.getA2();
                    baseTreina[linhaBase1][2] = oSepara.getA3();
                    baseTreina[linhaBase1][3] = oSepara.getA4();
                    baseTreina[linhaBase1][4] = oSepara.getA5();
                    baseTreina[linhaBase1][5] = oSepara.getA6();
                    baseTreina[linhaBase1][6] = oSepara.getA7();
                    baseTreina[linhaBase1][7] = oSepara.getA8();
                    baseTreina[linhaBase1][8] = oSepara.getA9();
                    baseTreina[linhaBase1][9] = oSepara.getA10();
                    baseTreina[linhaBase1][10] = oSepara.getA11();
                    baseTreina[linhaBase1][11] = oSepara.getA12();
                    baseTreina[linhaBase1][12] = oSepara.getA13();
                    baseTreina[linhaBase1][13] = oSepara.getClassWine();
                    baseTreina[linhaBase1][14] = valorD;
                    linhaBase1++;
                    //acumula tipo de classe inserida na base
                    if (oSepara.getClassWine() == 1)
                        ctaBase1Classe1++;
                    else if (oSepara.getClassWine() == 2)
                        ctaBase1Classe2++;
                    else if (oSepara.getClassWine() == 3)
                        ctaBase1Classe3++;
                }
                else if (sorteia == 2)
                {
                    //base teste
                    baseTeste[linhaBase2][0] = oSepara.getA1();
                    baseTeste[linhaBase2][1] = oSepara.getA2();
                    baseTeste[linhaBase2][2] = oSepara.getA3();
                    baseTeste[linhaBase2][3] = oSepara.getA4();
                    baseTeste[linhaBase2][4] = oSepara.getA5();
                    baseTeste[linhaBase2][5] = oSepara.getA6();
                    baseTeste[linhaBase2][6] = oSepara.getA7();
                    baseTeste[linhaBase2][7] = oSepara.getA8();
                    baseTeste[linhaBase2][8] = oSepara.getA9();
                    baseTeste[linhaBase2][9] = oSepara.getA10();
                    baseTeste[linhaBase2][10] = oSepara.getA11();
                    baseTeste[linhaBase2][11] = oSepara.getA12();
                    baseTeste[linhaBase2][12] = oSepara.getA13();
                    baseTeste[linhaBase2][13] = oSepara.getClassWine();
                    baseTeste[linhaBase2][14] = valorD;
                    linhaBase2++;
                    //acumula tipo de classe inserida na base
                    if (oSepara.getClassWine() == 1)
                        ctaBase2Classe1++;
                    else if (oSepara.getClassWine() == 2)
                        ctaBase2Classe2++;
                    else if (oSepara.getClassWine() == 3)
                        ctaBase2Classe3++;
                }
                else if (sorteia == 3)
                {
                    //base validação
                    baseValida[linhaBase3][0] = oSepara.getA1();
                    baseValida[linhaBase3][1] = oSepara.getA2();
                    baseValida[linhaBase3][2] = oSepara.getA3();
                    baseValida[linhaBase3][3] = oSepara.getA4();
                    baseValida[linhaBase3][4] = oSepara.getA5();
                    baseValida[linhaBase3][5] = oSepara.getA6();
                    baseValida[linhaBase3][6] = oSepara.getA7();
                    baseValida[linhaBase3][7] = oSepara.getA8();
                    baseValida[linhaBase3][8] = oSepara.getA9();
                    baseValida[linhaBase3][9] = oSepara.getA10();
                    baseValida[linhaBase3][10] = oSepara.getA11();
                    baseValida[linhaBase3][11] = oSepara.getA12();
                    baseValida[linhaBase3][12] = oSepara.getA13();
                    baseValida[linhaBase3][13] = oSepara.getClassWine();
                    baseValida[linhaBase3][14] = valorD;
                    linhaBase3++;
                    //acumula tipo de classe inserida na base
                    if (oSepara.getClassWine() == 1)
                        ctaBase3Classe1++;
                    else if (oSepara.getClassWine() == 2)
                        ctaBase3Classe2++;
                    else if (oSepara.getClassWine() == 3)
                        ctaBase3Classe3++;
                }
            }
    }
    
    static public void atualizaBases(int classe)
    {
        int tamBaseTreina = baseTreina.length;
        int tamBaseTeste = baseTeste.length;
        int tamBaseValida = 0;
        if (!(baseValida == null))
        {
            tamBaseValida = baseValida.length;
        }

        int valorD = 1;

        for (int x = 0; x < tamBaseTreina; x++)
        {
            if (baseTreina[x][13] == classe)
                baseTreina[x][14] = valorD;
            else
                baseTreina[x][14] = 0;
        }

        for (int x = 0; x < tamBaseTeste; x++)
        {
            if (baseTeste[x][13] == classe)
                baseTeste[x][14] = valorD;
            else
                baseTeste[x][14] = 0;
        }

        for (int x = 0; x < tamBaseValida; x++)
        {
            if (baseValida[x][13] == classe)
                baseValida[x][14] = valorD;
            else
                baseValida[x][14] = 0;
        }

    }
    
    
}

