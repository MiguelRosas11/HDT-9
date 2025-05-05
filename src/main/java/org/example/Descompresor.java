package org.example;

import java.io.*;

/**
 * Clase que implementa un descompresor de texto utilizando el algoritmo de Huffman.
 * Permite descomprimir un archivo binario comprimido y reconstruir el texto original
 * utilizando el árbol de Huffman almacenado.
 */
public class Descompresor {

    /**
     * Descomprime un archivo binario comprimido utilizando el árbol de Huffman.
     * 
     * @param rutaHuff      La ruta del archivo comprimido en formato binario (.huff).
     * @param rutaHuffTree  La ruta del archivo que contiene la representación del árbol de Huffman (.hufftree).
     * @param rutaSalida    La ruta donde se guardará el archivo descomprimido.
     * @throws IOException Si ocurre un error al leer o escribir archivos.
     */
    public void descomprimir(String rutaHuff, String rutaHuffTree, String rutaSalida) throws IOException {
        //Reconstruir el árbol desde .hufftree
        NodoHuffman raiz = reconstruirArbol(new BufferedReader(new FileReader(rutaHuffTree)));

        //Leer el archivo .huff y obtener la secuencia de bits
        String bits = leerBitsDesdeArchivo(rutaHuff);

        // Decodificar los bits usando el árbol de Huffman
        String textoOriginal = decodificar(bits, raiz);

        //Guardar el texto descomprimido
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaSalida))) {
            escritor.write(textoOriginal);
        }
    }

    /**
     * Reconstruye el árbol de Huffman a partir de su representación en un archivo.
     * 
     * @param lector Un BufferedReader para leer la representación del árbol en preorden.
     * @return La raíz del árbol de Huffman reconstruido.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private NodoHuffman reconstruirArbol(BufferedReader lector) throws IOException {
        String linea = lector.readLine();
        if (linea == null) return null;

        if (linea.startsWith("1")) {
            // Nodo hoja
            String contenido = linea.substring(1);
            char caracter;
            switch (contenido) {
                case "\\n": caracter = '\n'; break;
                case "\\t": caracter = '\t'; break;
                case "\\r": caracter = '\r'; break;
                default:
                    caracter = contenido.charAt(0); // para caracteres normales
                    break;
            } 
            return new NodoHuffman(caracter, 0);

        } else {
            // Nodo interno
            NodoHuffman izquierdo = reconstruirArbol(lector);
            NodoHuffman derecho = reconstruirArbol(lector);
            return new NodoHuffman(0, izquierdo, derecho);
        }
    }

    /**
     * Lee una secuencia de bits desde un archivo binario comprimido.
     * 
     * @param ruta La ruta del archivo binario comprimido (.huff).
     * @return Una cadena de bits leída desde el archivo.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private String leerBitsDesdeArchivo(String ruta) throws IOException {
        StringBuilder bits = new StringBuilder();
        try (DataInputStream entrada = new DataInputStream(new FileInputStream(ruta))) {
            int totalBits = entrada.readInt(); // Leer la longitud de la cadena de bits
            
            while (entrada.available() > 0) {
                int byteLeido = entrada.readUnsignedByte();
                String byteBinario = String.format("%8s", Integer.toBinaryString(byteLeido)).replace(' ', '0');
                bits.append(byteBinario);
            }

            return bits.substring(0, totalBits); // Retornar solo la longitud especificada
        }
    }

    /**
     * Decodifica una cadena de bits utilizando el árbol de Huffman.
     * 
     * @param bits La cadena de bits a decodificar.
     * @param raiz La raíz del árbol de Huffman utilizado para la decodificación.
     * @return El texto original decodificado.
     */
    private String decodificar(String bits, NodoHuffman raiz) {
        StringBuilder resultado = new StringBuilder();
        NodoHuffman actual = raiz;

        for (int i = 0; i < bits.length(); i++) {
            char bit = bits.charAt(i);
            actual = (bit == '0') ? actual.izquierdo : actual.derecho;

            if (actual.esHoja()) {
                resultado.append(actual.caracter);
                actual = raiz;
            }
        }

        return resultado.toString();
    }
}