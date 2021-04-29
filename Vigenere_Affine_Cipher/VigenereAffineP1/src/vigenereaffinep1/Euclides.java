/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vigenereaffinep1;

/**
 *
 * @author net_m
 */
public class Euclides {

    static int obtener_mcd(int a, int n) {
        if (n == 0) {
            System.out.println("Resultado: " + a);
            return a;
        } else {
            return obtener_mcd(n, a % n);
        }
    }

    static int euclidesFunExtended(int alfa, int n) {
        int inverse = 0;
        int flag = 0;
        //Find a^-1 (the multiplicative inverse of a  
        //in the group of integers modulo m.)  
        for (int i = 0; i < n; i++) {
            flag = (alfa * i) % n;
            // Check if (a*i)%26 == 1, 
            // then i will be the multiplicative inverse of a 
            if (flag == 1) {
                inverse = i;
            }
        }
        if (inverse == 0) {
            System.out.println("Alpha: " + alfa + " n: " + n + " No tiene inversa alpha");
        } else {
            System.out.println("Alpha: " + alfa + " n: " + n + " Inversa:" + inverse);

        }
        return inverse;

    }
}
