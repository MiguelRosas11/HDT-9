package org.example;
import java.util.*;

public class ArbolHuffman {
    public NodoHuffman raiz;

    // Construir el árbol a partir de frecuencias
    public void construir(Map<Character, Integer> frecuencias) {
        PriorityQueue<NodoHuffman> cola = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> entrada : frecuencias.entrySet()) {
            cola.add(new NodoHuffman(entrada.getKey(), entrada.getValue()));
        }

        while (cola.size() > 1) {
            NodoHuffman n1 = cola.poll();
            NodoHuffman n2 = cola.poll();
            NodoHuffman combinado = new NodoHuffman(n1.frecuencia + n2.frecuencia, n1, n2);
            cola.add(combinado);
        }

        raiz = cola.poll();
    }

    // Generar códigos de Huffman recursivamente
    private void generarCodigos(NodoHuffman nodo, String codigo, Map<Character, String> tablaCodigos) {
        if (nodo != null) {
            if (nodo.esHoja()) {
                tablaCodigos.put(nodo.caracter, codigo);
            }
            generarCodigos(nodo.izquierdo, codigo + "0", tablaCodigos);
            generarCodigos(nodo.derecho, codigo + "1", tablaCodigos);
        }
    }

    // Devuelve un Map con los códigos binarios
    public Map<Character, String> obtenerCodigos() {
        Map<Character, String> tabla = new HashMap<>();
        generarCodigos(raiz, "", tabla);
        return tabla;
    }
}