package org.example;

/**
 * Clase que representa un nodo en un árbol de Huffman.
 * Cada nodo puede ser una hoja que contiene un carácter y su frecuencia,
 * o un nodo interno que tiene referencias a nodos hijos.
 */
public class NodoHuffman implements Comparable<NodoHuffman> {
    /**
     * Carácter representado por el nodo (solo para nodos hoja).
     */
    public char caracter;

    /**
     * Frecuencia del carácter o suma de frecuencias en nodos internos.
     */
    public int frecuencia;

    /**
     * Referencia al nodo hijo izquierdo.
     */
    public NodoHuffman izquierdo;

    /**
     * Referencia al nodo hijo derecho.
     */
    public NodoHuffman derecho;

    /**
     * Constructor para nodos hoja.
     * 
     * @param caracter   El carácter representado por el nodo.
     * @param frecuencia La frecuencia del carácter.
     */
    public NodoHuffman(char caracter, int frecuencia) {
        this.caracter = caracter;
        this.frecuencia = frecuencia;
    }

    /**
     * Constructor para nodos internos.
     * 
     * @param frecuencia La suma de frecuencias de los nodos hijos.
     * @param izquierdo  Referencia al nodo hijo izquierdo.
     * @param derecho    Referencia al nodo hijo derecho.
     */
    public NodoHuffman(int frecuencia, NodoHuffman izquierdo, NodoHuffman derecho) {
        this.caracter = '\0'; // nodo interno (no es hoja)
        this.frecuencia = frecuencia;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    /**
     * Verifica si el nodo es una hoja.
     * 
     * @return true si el nodo es una hoja, false en caso contrario.
     */
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    /**
     * Compara este nodo con otro nodo de Huffman basado en la frecuencia.
     * 
     * @param otro El otro nodo a comparar.
     * @return Un valor negativo si este nodo tiene menor frecuencia,
     *         un valor positivo si tiene mayor frecuencia,
     *         o 0 si las frecuencias son iguales.
     */
    @Override
    public int compareTo(NodoHuffman otro) {
        return Integer.compare(this.frecuencia, otro.frecuencia);
    }
}