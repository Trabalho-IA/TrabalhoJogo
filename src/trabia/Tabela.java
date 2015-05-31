/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trabia;

/**
 *
 * @author Tadeu
 */
public class Tabela {
    
    private final int n; //dimensao da tabela (nxn)
    private final int[][] qs; //qs[i][j] = pontuacao do quadrado i,j
    private final boolean[][][] as; //as[i][j][k] = valor da aresta que parte do vertice i,j
                      //false se desligada, true se ligada
                      //se k = 0, é horizontal e une os vertices (i,j) e (i+1,j)
                      //caso contrario, vertical que vai de (i,j) até (i,j+1)
    private int cont;//conta o numero de arestas ligadas
    private int pts;
    private boolean full;//verdadeiro se count = (n-1)^2
    
    
    public Tabela(int dimensao){
        n = dimensao;
        qs = new int[n-1][n-1];
        as = new boolean[n][n][2];
        cont = 0;
        pts = 0;
    }
    
    boolean copia(Tabela tab){
        if(tab.tam()!=n) return false;
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-1; j++) {
                qs[i][j] = tab.quad(i, j);
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < 2; k++) {
                    as[i][j][k] = tab.arst(i, j, k);
                }
            }
        }
        cont = tab.cont();
        pts = tab.get_point();
        full = tab.cheio();
        return true;
    }
    
    int marca_aresta(boolean jog, boolean d, int i, int j){
        if(d) as[i][j][1] = true;
        else as[i][j][0] = true;
        int resp = 0;
        if(d){
            if(j>0) resp += marca_quadrado(jog,i,j-1);
            if(j<n-1) resp += marca_quadrado(jog,i,j);
        }
        else{
            if(i>0) resp += marca_quadrado(jog,i-1,j);
            if(i<n-1) resp += marca_quadrado(jog,i,j);
        }
        cont++;
        if(cont==(n-1)*(n-1)) full = true;
        return resp;
    }
    //retorna num de quadrados preenchidos (0<=n<=2)
    
    int marca_quadrado(boolean jog, int i, int j){
        qs[i][j]++;
        if(qs[i][j]==4){
            if(!jog){
                qs[i][j] = -1;
                pts--;
            }
            else pts++;
            return 1;
        }
        return 0;
    }
    //soma pontuacao do quadrado
    
    @Override
    public String toString(){
        String resp = "";
        for(int i = 0; i < n-1;i++){
            for(int j = 0; j < n-1; j++) resp += as[i][j][0]+" ";
            resp += "\n";
            for(int k = 0; k < n; k++) resp += as[i][k][1]+" ";
            resp += "\n";
        }
        for(int i = 0; i < n-1; i++) resp += as[n-1][i][0]+" ";
        resp += "\n";
        return resp;
    }
    
    public void imprime_quads(){
        for(int i = 0; i < n; i++){
            for (int j = 0; j < qs.length; j++) {
                System.out.print(qs[i][j] + " ");
                
            }
            System.out.println("");
        }
    }
    
    public boolean cheio(){
        return full;
    }
    
    public int get_point(){
        return pts;
    }
    
    public int length(){
        return n;
    }
    
    public int tam(){
        return n;
    }
    
    public int quad(int x, int y){
        return qs[x][y];
    }
    
    public boolean arst(int x, int y, int z){
        return as[x][y][z];
    }
    
    public int cont(){
        return cont;
    }
    
}