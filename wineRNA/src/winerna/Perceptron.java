/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winerna;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeffersonpasserini
 */
public class Perceptron {
    
    // pesos sinápticos
    private double[] w;
    // atributo responsável pelo somatório(rede).
    private double NET;
    // atributo taxa de aprendizado
    private double alfa;
    // valor do bias
    private double bias;
    // maximo de iterações do aprendizado
    private int maxIte;
    // atributo responsável pelo número máximo de épocas
    private final int epocasMax = 30;
    // atributo responsável pela contagem das épocas durante o treinamento
    private int count = 0;
    // declara o vetor da matriz de aprendizado
    private int[][] matrizAprendizado = new int[5][5];

    //sets
    public void setW(double[] w) {
        this.w = w;
    }

    public void setNET(double NET) {
        this.NET = NET;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public void setMaxIte(int maxIte) {
        this.maxIte = maxIte;
    }
    
    // execução da classificação
    public List<Wine> executarRNA(double[][] base, double[] w_c1, double[] w_c2, double[] w_c3)
    {
        //lista resultado
        List<Wine> lstResultado = new ArrayList<Wine>();
        
        //retorno perceptron
        double y = 0;
        double d = 0;
        
        //inicializa o Bias
        for (int x = 0; x < base.length; x++)
        {
            base[x][0] = this.bias;
            base[x][14] = 0;
        }
        
        for (int x=0; x<base.length; x++)
        {
            
            d = base[x][13]; //pega a classe do vinho
            
            //pega amostra
            double[] amostra = new double[15];
            for(int j=0; j<15; j++)
            {
                amostra[j] = base[x][j];
            }
            
            //testar a amostra com os neuronios
            double rna1 = funcaoSoma(amostra,w_c1);
            double rna2 = funcaoSoma(amostra,w_c2);
            double rna3 = funcaoSoma(amostra,w_c3);
            
            int resultado = classificacao(rna1,rna2,rna3);
                            
            Wine oWine = new Wine();
            oWine.setA1(amostra[0]);
            oWine.setA2(amostra[1]);
            oWine.setA3(amostra[2]);
            oWine.setA4(amostra[3]);
            oWine.setA5(amostra[4]);
            oWine.setA6(amostra[5]);
            oWine.setA7(amostra[6]);
            oWine.setA8(amostra[7]);
            oWine.setA9(amostra[8]);
            oWine.setA10(amostra[9]);
            oWine.setA11(amostra[10]);
            oWine.setA12(amostra[11]);
            oWine.setA13(amostra[12]);
            oWine.setClassWine((int) amostra[13]);
            oWine.setClassificacao(resultado);
            
            lstResultado.add(oWine);
        }

        //base classificada
        return lstResultado;
    }
    
    private double funcaoSoma(double[] amostra, double[] w)
    {
        //retorno perceptron
        double y = 0;
        //Função Soma (retorna o valor NET)
        double soma = 0;
        for (int x = 0; x<w.length; x++)
        {
            soma = soma + (amostra[x]*w[x]);
        }
        //Função de Ativação (Linear) onde o retorno da função Soma é o retorno
        //da função de ativação e do perceptron
        y = soma;
        return y;
    }
    
    private int classificacao(double rna1, double rna2, double rna3){
        if(rna1>rna2 && rna1>rna3)
            return 1;
        else if (rna2>rna1 && rna2>rna3)
            return 2;
        else
            return 3;
    }
    
    /* Treina perceptron sem validação */
    public double[] treinar(double[][] baseTreina)
    {
        //inicializa o vetor W (pesos)
        double[] w = new double[13];

        //inicializa o vetor W (pesos)
        for (int j = 0; j < w.length; j++)
        {
            //inicializa W com pesos igual a zero
            w[j] = 0;
        }

        //inicializa X0 igual ao Bias em todas as amostras da base
        for (int x = 0; x < baseTreina.length; x++)
        {
            baseTreina[x][0] = this.bias;
        }

        //contador de iteracao
        int iteracao = 1;
        //inicializa soma dos erros e erro
        double erro = 1;
        //faça enquanto iteração for menor que o maximo e iterações e
        // enquanto erro for diferente de 0
        while (iteracao < this.maxIte && erro != 0)
        {
            erro = 0;

            //inicia loop de treinamento
            for (int i = 0; i < baseTreina.length; i++)
            {
                //inicializa o fator de erro
                double y = 0, soma = 0;
                //variavel d --> resposta esperada
                double d = baseTreina[i][14];

                //Calcula a saida da função soma
                                
                soma = ((baseTreina[i][0] * w[0]) + 
                        (baseTreina[i][1] * w[1]) + 
                        (baseTreina[i][2] * w[2]) + 
                        (baseTreina[i][3] * w[3]) + 
                        (baseTreina[i][4] * w[4]) +
                        (baseTreina[i][5] * w[5]) +
                        (baseTreina[i][6] * w[6]) +
                        (baseTreina[i][7] * w[7]) +
                        (baseTreina[i][8] * w[8]) +
                        (baseTreina[i][9] * w[9]) +
                        (baseTreina[i][10] * w[10]) +
                        (baseTreina[i][11] * w[11]) +
                        (baseTreina[i][12] * w[12]));

                //funação de ativação binária
                if (soma > 0)
                    y = 1;
                else
                    y = 0;

                //realiza o aprendizado supervisionado
                //verifica a resposta calculada com a esperada
                if (y != d)
                {
                    //atualiza o vetor de pesos W
                    for (int j = 0; j < w.length; j++)
                    {
                        //formula aprendizado
                        //peso0 = peso0 + alfa * (d-y) * x0
                        w[j] = w[j] + this.alfa * (d-y) * baseTreina[i][j];
                    }
                    
                    //acumula o erro
                    erro = erro + Math.pow((d-y),2);
                }
            }

            iteracao++;
        }

        return w;
    }    
    
    /* treina perceptron com validação */
    public double[] treinar(double[][] baseTreina, double[][] baseValidacao)
    {
        /* variaveis de controle para validacao */
        float melhorAcuracia = 9999999;
        int nroIteSemMelhora = 0;
        int tempoMaxSemMelhora = Integer.parseInt(String.valueOf(this.maxIte*0.1));
        
        //inicializa o vetor W (pesos)
        double[] w = new double[13];

        //inicializa o vetor W (pesos)
        for (int j = 0; j < w.length; j++)
        {
            //inicializa W com pesos igual a zero
            w[j] = 0;
        }

        //inicializa X0 igual ao Bias em todas as amostras da base
        for (int x = 0; x < baseTreina.length; x++)
        {
            baseTreina[x][0] = this.bias;
        }
        
        //inicializa X0 igual ao Bias em todas as amostras da base Validacao
        for (int x = 0; x < baseValidacao.length; x++)
        {
            baseValidacao[x][0] = this.bias;
        }

        //controle de encerramento do loop pela variavel de overfitting
        Boolean paraTreinamento = true;
        //contador de iteracao
        int iteracao = 1;
        //inicializa soma dos erros
        double erro = 1;
        // erros de validacao
        double erroValida = 1;
        //controle de parada do erro quadratico
        double ultErroQuadratico = 999999;
        //controle o numero de verificações do paraTreinamento (basevalidação)
        int controleParada = 0;

        
        //faça enquanto iteração for menor que o maximo e iterações e
        // enquanto erro for diferente de 0
        // e paraTreinamento == true
        while (iteracao < this.maxIte && erro != 0 && paraTreinamento)
        {
            erro = 0;
            erroValida = 0;

            //inicia loop de treinamento
            for (int i = 0; i < baseTreina.length; i++)
            {
                //inicializa o fator de erro
                double y = 0, soma = 0;
                //variavel d --> resposta esperada
                double d = baseTreina[i][14];

                //Calcula a saida da função soma
                soma = ((baseTreina[i][0] * w[0]) + 
                        (baseTreina[i][1] * w[1]) + 
                        (baseTreina[i][2] * w[2]) + 
                        (baseTreina[i][3] * w[3]) + 
                        (baseTreina[i][4] * w[4]) +
                        (baseTreina[i][5] * w[5]) +
                        (baseTreina[i][6] * w[6]) +
                        (baseTreina[i][7] * w[7]) +
                        (baseTreina[i][8] * w[8]) +
                        (baseTreina[i][9] * w[9]) +
                        (baseTreina[i][10] * w[10]) +
                        (baseTreina[i][11] * w[11]) +
                        (baseTreina[i][12] * w[12]) +
                        (baseTreina[i][13] * w[13]));

                //funação de ativação binária
                if (soma > 0)
                    y = 1;
                else
                    y = 0;

                //realiza o aprendizado supervisionado
                //verifica a resposta calculada com a esperada
                if (y != d)
                {
                    //atualiza o vetor de pesos W
                    for (int j = 0; j < w.length; j++)
                    {
                        //formula aprendizado
                        //peso0 = peso0 + alfa * (d-y) * x0
                        w[j] = w[j] + this.alfa * (d-y) * baseTreina[i][j];
                    }
                    
                    //acumula o erro
                    erro = erro + Math.pow((d-y),2);
                }
            }
            
            /* realiza teste do treinamento */
            Estatistica resultadoTeste = testarTreinamento(baseValidacao,w);
            /* calcula a acuracia */
            int verdadeiroPositivo = Integer.parseInt(resultadoTeste.getVP());
            int verdadeiroNegativo = Integer.parseInt(resultadoTeste.getVN());
            float acuracia = (verdadeiroPositivo+verdadeiroNegativo)/baseValidacao.length;
            
            //verifica melhoria de acuracia e acumula contador
            if (acuracia > melhorAcuracia){
                melhorAcuracia = acuracia;
                /* zera contador de iteracao sem melhora de acuracia */
                nroIteSemMelhora = 0;
            } else {
                //acumula iteracao sem melhora de acuracia
                nroIteSemMelhora++;
            }
            
            /* verifica se interrompe treinamento */
            if(nroIteSemMelhora >= tempoMaxSemMelhora){
                paraTreinamento = false;
            }

            iteracao++;
        }

        return w;
    }

    //Testa treinamento com base Teste 
    public Estatistica testarTreinamento(double[][] baseValidacao, double[] w)
    {
        List<Wine> lstResultado = new ArrayList<Wine>();
        
        int[] classificacao = new int[baseValidacao.length];

        int vp=0,vn=0,fp=0,fn=0;
        
        //inicializa X0 igual ao Bias em todas as amostras da base
        for (int x = 0; x < baseValidacao.length; x++)
        {
            baseValidacao[x][0] = this.bias;
        }

        //inicia loop para classificação de dados
        for (int i = 0; i < baseValidacao.length; i++)
        {
            //inicializa o fator de erro
            double y = 0, soma = 0;
            //inicializa variaveis de atributos
            double d = baseValidacao[i][14];

            //Calcula a saida da função de ativação
            soma = 0;
            for (int x = 0; x<w.length; x++)
            {
                soma = soma + (baseValidacao[i][x]*w[x]);
            }
            
            //saída do neuronio
            if (soma > 0)
                y = 1;
            else
                y = 0;

            if (y == d)
            {
                if (d == 0)
                    vn++;
                else
                    vp++;
            }
            else
            {
                if (d == 0)
                    fn++;
                else
                    fp++;
            }
        }
        
        Estatistica oEstatistica = new Estatistica(String.valueOf(vp),
                                                   String.valueOf(vn),
                                                   String.valueOf(fp),
                                                   String.valueOf(fn));
        return oEstatistica;
    }
}
