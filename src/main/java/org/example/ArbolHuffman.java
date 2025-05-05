package org.example;
import java.util.*;

/**
 * Clase que representa un árbol de Huffman utilizado para la compresión de datos.
 * Permite construir el árbol a partir de frecuencias de caracteres y generar
 * los códigos binarios correspondientes.
 */
public class ArbolHuffman {
    /**
     * Nodo raíz del árbol de Huffman.
     */
    public NodoHuffman raiz;

    /**
     * Construye el árbol de Huffman a partir de un mapa de frecuencias de caracteres.
     * 
     * @param frecuencias Un mapa donde las claves son caracteres y los valores son
     *                    sus frecuencias.
     */
    public void construir(Map<Character, Integer> frecuencias) {
        PriorityQueue<NodoHuffman> cola = new PriorityQueue<>();

        // Crear un nodo hoja para cada carácter y añadirlo a la cola de prioridad
        for (Map.Entry<Character, Integer> entrada : frecuencias.entrySet()) {
            cola.add(new NodoHuffman(entrada.getKey(), entrada.getValue()));
        }

        // Construir el árbol combinando los nodos con menor frecuencia
        while (cola.size() > 1) {
            NodoHuffman n1 = cola.poll();
            NodoHuffman n2 = cola.poll();
            NodoHuffman combinado = new NodoHuffman(n1.frecuencia + n2.frecuencia, n1, n2);
            cola.add(combinado);
        }

        // El último nodo en la cola es la raíz del árbol
        raiz = cola.poll();
    }

    /**
     * Genera los códigos binarios de Huffman de forma recursiva a partir del árbol.
     * 
     * @param nodo         El nodo actual del árbol.
     * @param codigo       El código binario acumulado hasta el nodo actual.
     * @param tablaCodigos Un mapa donde se almacenan los códigos binarios generados.
     */
    private void generarCodigos(NodoHuffman nodo, String codigo, Map<Character, String> tablaCodigos) {
        if (nodo != null) {
            // Si el nodo es una hoja, añadir el carácter y su código a la tabla
            if (nodo.esHoja()) {
                tablaCodigos.put(nodo.caracter, codigo);
            }
            // Recorrer el hijo izquierdo añadiendo "0" al código
            generarCodigos(nodo.izquierdo, codigo + "0", tablaCodigos);
            // Recorrer el hijo derecho añadiendo "1" al código
            generarCodigos(nodo.derecho, codigo + "1", tablaCodigos);
        }
    }

    /**
     * Obtiene un mapa con los códigos binarios de Huffman para cada carácter.
     * 
     * @return Un mapa donde las claves son caracteres y los valores son sus
     *         códigos binarios.
     */
    public Map<Character, String> obtenerCodigos() {
        Map<Character, String> tabla = new HashMap<>();
        generarCodigos(raiz, "", tabla);
        return tabla;
    }
}