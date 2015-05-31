/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trabia;

import java.util.LinkedList;

/**
 *
 * @author Tadeu
 */
public class MyTree {
    
    private final int n;
    private int cont;
    private final Tabela t;
    private final MyTree[] sons;
    
    public MyTree(int tamanho, Tabela tab){
        t = tab;
        cont = 0;
        n = tamanho;
        sons = new MyTree[n];
    }
    
    boolean insert(Tabela tab){
        if(cont >= n) return false;
        sons[cont] = new MyTree(n-1, tab);
        cont++;
        return true;
    }
    
    int tam(){
        return n;
    }
    
    String print(int i){
        return sons[i].t.toString();
    }
    
    MyTree son_i(int i){
        return sons[i];
    }
    
    Tabela tab(){
        return t;
    }
}
