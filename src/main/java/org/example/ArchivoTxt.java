package org.example;
import java.io.*;
import java.util.*;

/**
 * Clase que proporciona métodos para trabajar con archivos de texto.
 * Incluye funcionalidades para leer el contenido de un archivo y calcular
 * la frecuencia de caracteres en un texto.
 */
public class ArchivoTxt {

    /**
     * Lee el contenido completo de un archivo de texto y lo devuelve como un String.
     * 
     * @param ruta La ruta del archivo a leer.
     * @return El contenido del archivo como un String.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static String leerArchivo(String ruta) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                contenido.append(linea).append("\n"); // conservar saltos de línea
            }
        }
        return contenido.toString();
    }

    /**
     * Calcula la frecuencia de cada carácter en un texto dado.
     * 
     * @param texto El texto del cual se calcularán las frecuencias de caracteres.
     * @return Un mapa donde las claves son caracteres y los valores son sus frecuencias.
     */
    public static Map<Character, Integer> contarFrecuencias(String texto) {
        Map<Character, Integer> frecuencias = new HashMap<>();
        for (char c : texto.toCharArray()) {
            frecuencias.put(c, frecuencias.getOrDefault(c, 0) + 1);
        }
        return frecuencias;
    }
}