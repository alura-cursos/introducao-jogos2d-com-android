package br.com.alura.forca;

import android.util.Log;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class ForcaController {

    private String          palavraParaAdvinhar;//Palavra a ser advinhada
    private Set<Character>  letrasUsadas;//Lista q contera todas as letras q foram utilizadas sem itens repetidos
    private int             qntErros         = -1;//Qntidade de erros

    public ForcaController(String palavra) {
        letrasUsadas = new HashSet<Character>();
        qntErros = -1;
        this.palavraParaAdvinhar = palavra;
    }

    public void joga(Character letra) {
        if( letrasUsadas.contains( letra ) )//caso o Set contenha a letra jogada, saimos da função
            return;//sai da função
        letrasUsadas.add(letra);//Como a letra não foi jogada, então adiciona à lista

        if ( palavraParaAdvinhar.contains(letra.toString()) )//Verificamos se a string letra contém a letra a ser jogada
            return;//Se a lista contiver a letra a ser jogada, saímos da função

        qntErros++;
        //Como a letra não foi utilizada ainda e também, a mesma não consta na palavraParaAdvinhar
        //acrescentamos uma unidade na nossa variavel que controla o numero de erros
    }

    public String getPalavraAteAgora() {
        String visualizado = "";
        for( char c : palavraParaAdvinhar.toCharArray() ) {
            if(letrasUsadas.contains(c))
                visualizado += c;
            else
                visualizado += " ";
        }
        return visualizado;
    }

    public boolean isMorreu() {
        return getQntErros() == 5;
    }
    public boolean isGanhou() {
        return !getPalavraAteAgora().contains(" ");
    }
    public boolean isTerminou() {
        return isMorreu() || isGanhou();
    }
    public int getQntErros() {return qntErros;}
}



