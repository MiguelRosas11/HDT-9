package org.example;

import java.io.*;
import java.util.*;

public class Compresor{

    private ArbolHuffman arbol;
    private Map<Character, String> codigos;

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

    private void guardarComoBinario(String bits, String ruta) throws IOException {
        try (FileOutputStream salida = new FileOutputStream(ruta)) {
            int len = bits.length();
            for (int i = 0; i < len; i += 8) {
                String byteStr = bits.substring(i, Math.min(i + 8, len));
                while (byteStr.length() < 8) {
                    byteStr += "0"; // rellenar con ceros al final
                }
                int valor = Integer.parseInt(byteStr, 2);
                salida.write(valor);
            }
        }
    }

    private void guardarArbol(NodoHuffman nodo, String ruta) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta))) {
            guardarPreorden(nodo, escritor);
        }
    }

    private void guardarPreorden(NodoHuffman nodo, BufferedWriter escritor) throws IOException {
        if (nodo == null) return;

        if (nodo.esHoja()) {
            // Evita guardar saltos de línea o caracteres invisibles directamente
            char c = nodo.caracter;
            if (c == '\n') {
                escritor.write("1\\n\n"); // Marca explícita
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