package org.example;
import java.io.*;
import java.util.*;

public class ArchivoTxt {

    // Lee el archivo y devuelve el contenido completo como String
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

    // Calcula la frecuencia de cada carácter en el texto
    public static Map<Character, Integer> contarFrecuencias(String texto) {
        Map<Character, Integer> frecuencias = new HashMap<>();
        for (char c : texto.toCharArray()) {
            frecuencias.put(c, frecuencias.getOrDefault(c, 0) + 1);
        }
        return frecuencias;
    }
}