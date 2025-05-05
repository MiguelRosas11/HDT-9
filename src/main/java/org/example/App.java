package org.example;
import java.io.*;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //IMPORTANTE: Cambia la ruta base a la ubicación de tus archivos donde está la carpeta de todo el proyecto
        String base = "C:/Users/juang/OneDrive/Documents/Algoritmos y estructura de datos java/ejercicio1/HDT-9";
        String inputDir = base + "/archivos-txt";
        String outputDir = base + "/archivos-huff";

        // Crear carpeta de salida si no existe
        new File(outputDir).mkdirs();

        while (true) {
            System.out.println("\n=== MENÚ HUFFMAN ===");
            System.out.println("1. Comprimir archivo");
            System.out.println("2. Descomprimir archivo");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    try {
                        System.out.print("Nombre del archivo de entrada (.txt) en /archivos-txt/: ");
                        String nombreEntrada = scanner.nextLine();
                        String rutaEntrada = inputDir + "/" + nombreEntrada;

                        System.out.print("Nombre del archivo .huff (ej: comprimido.huff): ");
                        String nombreHuff = scanner.nextLine();
                        String rutaHuff = outputDir + "/" + nombreHuff;

                        System.out.print("Nombre del archivo .hufftree (ej: arbol.hufftree): ");
                        String nombreTree = scanner.nextLine();
                        String rutaHuffTree = outputDir + "/" + nombreTree;

                        Compresor compresor = new Compresor();
                        compresor.comprimir(rutaEntrada, rutaHuff, rutaHuffTree);
                        System.out.println("Compresión completada. Archivos generados en /archivos-huff/");
                    } catch (IOException e) {
                        System.err.println("Error al comprimir: " + e.getMessage());
                    }
                    break;

                case "2":
                    try {
                        System.out.print("Nombre del archivo .huff en /archivos-huff/: ");
                        String nombreHuff = scanner.nextLine();
                        String rutaHuff = outputDir + "/" + nombreHuff;

                        System.out.print("Nombre del archivo .hufftree en /archivos-huff/: ");
                        String nombreTree = scanner.nextLine();
                        String rutaHuffTree = outputDir + "/" + nombreTree;

                        System.out.print("Nombre del archivo descomprimido (.txt) para guardar en /arhivos-txt/: ");
                        String nombreSalida = scanner.nextLine();
                        String rutaSalida = inputDir + "/" + nombreSalida;

                        Descompresor descompresor = new Descompresor();
                        descompresor.descomprimir(rutaHuff, rutaHuffTree, rutaSalida);
                        System.out.println("Descompresión completada. Archivo guardado en /output/" + nombreSalida);
                    } catch (IOException e) {
                        System.err.println("Error al descomprimir: " + e.getMessage());
                    }
                    break;

                case "3":
                    System.out.println("Programa finalizado.");
                    return;

                default:
                    System.out.println("Opción inválida. Intenta de nuevo.");
            }
        }
    }
}