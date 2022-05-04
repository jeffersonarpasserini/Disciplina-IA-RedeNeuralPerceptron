/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winerna;

/**
 *
 * @author jeffersonpasserini
 */
public class Wine {
    
    /*
         * 1) Alcohol
         * 2) Malic acid
         * 3) Ash
         * 4) Alcalinity of ash  
         * 5) Magnesium
         * 6) Total phenols
         * 7) Flavanoids
         * 8) Nonflavanoid phenols
         * 9) Proanthocyanins
         * 10)Color intensity
         * 11)Hue
         * 12)OD280/OD315 of diluted wines
         * 13)Proline 
         *   
         * Exemplo:
         * 1,14.23,1.71,2.43,15.6,127,2.8,3.06,.28,2.29,5.64,1.04,3.92,1065
    */

    private double a1;
    private double a2;
    private double a3;
    private double a4;
    private double a5;
    private double a6;
    private double a7;
    private double a8;
    private double a9;
    private double a10;
    private double a11;
    private double a12;
    private double a13;
    private int classWine;
    private int classificacao;

    public Wine() {
    }

    public Wine(double a1, double a2, double a3, double a4, double a5, double a6, double a7, double a8, double a9, double a10, double a11, double a12, double a13, int classWine, int classificacao) {
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.a5 = a5;
        this.a6 = a6;
        this.a7 = a7;
        this.a8 = a8;
        this.a9 = a9;
        this.a10 = a10;
        this.a11 = a11;
        this.a12 = a12;
        this.a13 = a13;
        this.classWine = classWine;
        this.classificacao = classificacao;
    }

    public int getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

    public int getClassWine() {
        return classWine;
    }

    public void setClassWine(int classWine) {
        this.classWine = classWine;
    }

    public double getA1() {
        return a1;
    }

    public void setA1(double a1) {
        this.a1 = a1;
    }

    public double getA2() {
        return a2;
    }

    public void setA2(double a2) {
        this.a2 = a2;
    }

    public double getA3() {
        return a3;
    }

    public void setA3(double a3) {
        this.a3 = a3;
    }

    public double getA4() {
        return a4;
    }

    public void setA4(double a4) {
        this.a4 = a4;
    }

    public double getA5() {
        return a5;
    }

    public void setA5(double a5) {
        this.a5 = a5;
    }

    public double getA6() {
        return a6;
    }

    public void setA6(double a6) {
        this.a6 = a6;
    }

    public double getA7() {
        return a7;
    }

    public void setA7(double a7) {
        this.a7 = a7;
    }

    public double getA8() {
        return a8;
    }

    public void setA8(double a8) {
        this.a8 = a8;
    }

    public double getA9() {
        return a9;
    }

    public void setA9(double a9) {
        this.a9 = a9;
    }

    public double getA10() {
        return a10;
    }

    public void setA10(double a10) {
        this.a10 = a10;
    }

    public double getA11() {
        return a11;
    }

    public void setA11(double a11) {
        this.a11 = a11;
    }

    public double getA12() {
        return a12;
    }

    public void setA12(double a12) {
        this.a12 = a12;
    }

    public double getA13() {
        return a13;
    }

    public void setA13(double a13) {
        this.a13 = a13;
    }
    
    
    
}
