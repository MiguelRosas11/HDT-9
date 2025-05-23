package org.example;

import java.io.*;
import java.util.*;

/**
 * Clase que implementa un compresor de texto utilizando el algoritmo de Huffman.
 * Permite comprimir un archivo de texto y guardar tanto el archivo comprimido
 * como la representación del árbol de Huffman.
 */
public class Compresor {

    /**
     * Árbol de Huffman utilizado para la compresión.
     */
    private ArbolHuffman arbol;

    /**
     * Mapa que almacena los códigos binarios generados para cada carácter.
     */
    private Map<Character, String> codigos;

    /**
     * Comprime un archivo de texto utilizando el algoritmo de Huffman.
     * 
     * @param rutaEntrada   La ruta del archivo de texto a comprimir.
     * @param rutaHuff      La ruta donde se guardará el archivo comprimido en formato binario.
     * @param rutaHuffTree  La ruta donde se guardará la representación del árbol de Huffman.
     * @throws IOException Si ocurre un error al leer o escribir archivos.
     */
    public void comprimir(String rutaEntrada, String rutaHuff, String rutaHuffTree) throws IOException {
        // Leer y analizar el archivo original
        String texto = ArchivoTxt.leerArchivo(rutaEntrada);
        Map<Character, Integer> frecuencias = ArchivoTxt.contarFrecuencias(texto);

        // Construir árbol de Huffman
        arbol = new ArbolHuffman();
        arbol.construir(frecuencias);

        // Obtener los códigos binarios para cada carácter
        codigos = arbol.obtenerCodigos();

        // Codificar el texto
        StringBuilder binarioCodificado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            binarioCodificado.append(codigos.get(c));
        }

        // Guardar archivo binario .huff
        guardarComoBinario(binarioCodificado.toString(), rutaHuff);

        // Guardar archivo .hufftree con recorrido preorden
        guardarArbol(arbol.raiz, rutaHuffTree);
    }

    /**
     * Guarda una cadena de bits como un archivo binario.
     * 
     * @param bits La cadena de bits a guardar.
     * @param ruta La ruta donde se guardará el archivo binario.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    private void guardarComoBinario(String bits, String ruta) throws IOException {
        try (DataOutputStream salida = new DataOutputStream(new FileOutputStream(ruta))) {
            salida.writeInt(bits.length()); // Guardar la longitud de la cadena de bits

            int len = bits.length();
            for (int i = 0; i < len; i += 8) {
                String byteStr = bits.substring(i, Math.min(i + 8, len));
                while (byteStr.length() < 8) {
                    byteStr += "0"; // Rellenar con ceros al final
                }
                int valor = Integer.parseInt(byteStr, 2);
                salida.write(valor);
            }
        }
    }

    /**
     * Guarda la representación del árbol de Huffman en un archivo utilizando
     * un recorrido preorden.
     * 
     * @param nodo La raíz del árbol de Huffman.
     * @param ruta La ruta donde se guardará el archivo del árbol.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    private void guardarArbol(NodoHuffman nodo, String ruta) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta))) {
            guardarPreorden(nodo, escritor);
        }
    }

    /**
     * Realiza un recorrido preorden del árbol de Huffman y lo guarda en un archivo.
     * 
     * @param nodo     El nodo actual del árbol.
     * @param escritor El escritor para guardar la representación del árbol.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    private void guardarPreorden(NodoHuffman nodo, BufferedWriter escritor) throws IOException {
        if (nodo == null) return;

        if (nodo.esHoja()) {
            // Evita guardar saltos de línea o caracteres invisibles directamente
            char c = nodo.caracter;
            if (c == '\n') {
                escritor.write("1\\n\n");
            } else if (c == '\r') {
                escritor.write("1\\r\n");
            } else if (c == '\t') {
                escritor.write("1\\t\n");
            } else {
                escritor.write("1" + c + "\n");
            }
        } else {
            escritor.write("0\n");
        }

        guardarPreorden(nodo.izquierdo, escritor);
        guardarPreorden(nodo.derecho, escritor);
    }
}