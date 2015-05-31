/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabia;

import java.util.Scanner;

/**
 *
 * @author Tadeu
 */
public class TrabIA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Tabela t = new Tabela(3);
        int lin, col, sent;
        Scanner tec = new Scanner(System.in);
        for (int i = 1; i <= (t.length()) * (t.length() - 1) + (t.length() - 1) * (t.length()); i++) {
            if (i % 2 == 1) {
                //System.out.println("ok");
                t = alfabeta(i, t);
                System.out.println(t);
            } else {
                lin = tec.nextInt();
                col = tec.nextInt();
                sent = tec.nextInt();
            }
        }
    }

    public static Tabela alfabeta(int turno, Tabela t) {
        //se turno = 1, gerar campo de busca menor pro minimax
        MyTree arv;
        Tabela aux;
        if (turno == 1) {
            arv = gera_rot(t);
        } //caso contrario, gerar todas as possibilidades inserindo-as em mytree
        else {
            arv = gera_tot(true, t);
        }
        System.out.println("PAI:");
        System.out.println(arv.tab());
        System.out.println("FILHOS:");
        for (int i = 0; i < arv.tam(); i++) {
            System.out.println(i + " :");
            System.out.println(arv.son_i(i).tab());
        }
        MyTree resp = max_value(min_value(arv, Integer.MIN_VALUE, Integer.MAX_VALUE), Integer.MIN_VALUE, Integer.MAX_VALUE);
        return resp.tab();
    }

    public static MyTree max_value(MyTree t, int alfa, int beta) {
        //casos terminais
        if (t.tab().cheio() || t.tab().get_point() == -(((t.tab().tam() - 1) * (t.tab().tam() - 1) / 2) - 1)) {
            return t;
        } else {
            MyTree resp = gera_tot(true, t.tab());
            System.out.println("PAI(max):");
            System.out.println(resp.tab());
            System.out.println("FILHOS:");
            for (int i = 0; i < resp.tam(); i++) {
                System.out.println(i + " :");
                System.out.println(resp.son_i(i).tab());
            }
            MyTree aux = null;
            int v = Integer.MIN_VALUE;
            for (int i = 0; i < resp.tam(); i++) {
                aux = min_value(resp.son_i(i), alfa, beta);
                v = Integer.max(aux.tab().get_point(), v);
                if (v >= beta) {
                    return aux;
                }
                alfa = Integer.max(alfa, v);
            }
            return aux;
        }
    }

    public static MyTree min_value(MyTree t, int alfa, int beta) {
        if (t.tab().cheio() || t.tab().get_point() == -(((t.tab().tam() - 1) * (t.tab().tam() - 1) / 2) - 1)) {
            return t;
        } else {
            MyTree resp = gera_tot(true, t.tab());
            MyTree aux = null;
            System.out.println("PAI(min):");
            System.out.println(resp.tab());
            System.out.println("FILHOS:");
            for (int i = 0; i < resp.tam(); i++) {
                System.out.println(i + " :");
                System.out.println(resp.son_i(i).tab());
            }
            int v = Integer.MAX_VALUE;
            for (int i = 0; i < resp.tam(); i++) {
                aux = max_value(resp.son_i(i), alfa, beta);
                v = Integer.min(aux.tab().get_point(), v);
                if (v <= alfa) {
                    return aux;
                }
                beta = Integer.min(beta, v);
            }
            return aux;
        }
    }

    public static MyTree gera_tot(boolean jog, Tabela t) {
        MyTree arv;
        Tabela aux;
        arv = new MyTree((t.length()) * (t.length() - 1) + (t.length() - 1) * (t.length()) - t.cont(), t);
        //System.out.println(arv.tam());
        for (int i = 0; i < t.length() - 1; i++) {
            for (int j = 0; j < t.length() - 1; j++) {
                if(!t.arst(i, j, 0)){
                    aux = new Tabela(t.length());
                    aux.copia(t);
                    aux.marca_aresta(jog, false, i, j);
                    arv.insert(aux);
                }
            }
            for (int j = 0; j < t.length(); j++) {
                if(!t.arst(i, j, 1)){
                    aux = new Tabela(t.length());
                    aux.copia(t);
                    aux.marca_aresta(jog, true, i, j);
                    arv.insert(aux);
                }
            }
        }
        for (int i = 0; i < t.length() - 1; i++) {
            if(!t.arst(t.length()-1, i, 0)){
                aux = new Tabela(t.length());
                aux.copia(t);
                aux.marca_aresta(jog, false, t.length() - 1, i);
                arv.insert(aux);
            }
        }
        return arv;
    }

    public static MyTree gera_rot(Tabela t) {
        MyTree arv;
        Tabela aux;
        if (t.length() % 2 == 0) {
            arv = new MyTree((t.length() / 2) * (t.length() / 2), t);
        } else {
            arv = new MyTree(((t.length() / 2) * (t.length() / 2) + t.length() / 2), t);
        }
        System.out.println(arv.tam());
        //gerar opcoes protegidas por rotacao
        for (int i = 0; i < (t.length() - 1) / 2; i++) {
            for (int j = i; j < (t.length()) / 2; j++) {
                if (j == i || j == (t.length() - 1) / 2) {
                    if (j == i) {
                        aux = new Tabela(t.length());
                        aux.copia(t);
                        aux.marca_aresta(true, false, i, j);
                        arv.insert(aux);
                    } else {
                        aux = new Tabela(t.length());
                        aux.copia(t);
                        aux.marca_aresta(true, true, i, j);
                        arv.insert(aux);
                    }
                } else {
                    aux = new Tabela(t.length());
                    aux.copia(t);
                    aux.marca_aresta(true, false, i, j);
                    arv.insert(aux);
                    aux = new Tabela(t.length());
                    aux.copia(t);
                    aux.marca_aresta(true, true, i, j);
                    arv.insert(aux);
                }
            }

        }
        if (t.length() % 2 == 0) {
            for (int i = 0; i < (t.length()) / 2; i++) {
                aux = new Tabela(t.length());
                aux.copia(t);
                aux.marca_aresta(true, false, i, ((t.length() - 1) / 2));
                arv.insert(aux);
            }
        } else {
            for (int i = 0; i < ((t.length() - 1) / 2); i++) {
                aux = new Tabela(t.length());
                aux.copia(t);
                aux.marca_aresta(true, true, i, ((t.length() - 1) / 2));
                arv.insert(aux);
            }
        }
        return arv;
    }
}
