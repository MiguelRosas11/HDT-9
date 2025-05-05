package org.example;

import java.io.*;

public class Descompresor {

    public void descomprimir(String rutaHuff, String rutaHuffTree, String rutaSalida) throws IOException {
        // Paso 1: Reconstruir el árbol desde .hufftree
        NodoHuffman raiz = reconstruirArbol(new BufferedReader(new FileReader(rutaHuffTree)));

        // Paso 2: Leer el archivo .huff y obtener la secuencia de bits
        String bits = leerBitsDesdeArchivo(rutaHuff);

        // Paso 3: Decodificar los bits usando el árbol de Huffman
        String textoOriginal = decodificar(bits, raiz);

        // Paso 4: Guardar el texto descomprimido
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaSalida))) {
            escritor.write(textoOriginal);
        }
    }

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
            return new NodoHuffman(caracter, 0); // frecuencia no es relevante para hojas

        } else {
            // Nodo interno
            NodoHuffman izquierdo = reconstruirArbol(lector);
            NodoHuffman derecho = reconstruirArbol(lector);
            return new NodoHuffman(0, izquierdo, derecho);
        }
    }

    private String leerBitsDesdeArchivo(String ruta) throws IOException {
        StringBuilder bits = new StringBuilder();
        try (FileInputStream entrada = new FileInputStream(ruta)) {
            int byteLeido;
            while ((byteLeido = entrada.read()) != -1) {
                String byteBinario = String.format("%8s", Integer.toBinaryString(byteLeido & 0xFF)).replace(' ', '0');
                bits.append(byteBinario);
            }
        }
        return bits.toString();
    }

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