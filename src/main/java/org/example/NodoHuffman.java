package org.example;

public class NodoHuffman implements Comparable<NodoHuffman> {
    public char caracter;
    public int frecuencia;
    public NodoHuffman izquierdo;
    public NodoHuffman derecho;

    public NodoHuffman(char caracter, int frecuencia) {
        this.caracter = caracter;
        this.frecuencia = frecuencia;
    }

    public NodoHuffman(int frecuencia, NodoHuffman izquierdo, NodoHuffman derecho) {
        this.caracter = '\0'; // nodo interno (no es hoja)
        this.frecuencia = frecuencia;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    @Override
    public int compareTo(NodoHuffman otro) {
        return Integer.compare(this.frecuencia, otro.frecuencia);
    }
}