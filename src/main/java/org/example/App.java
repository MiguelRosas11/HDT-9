package org.example;
import java.io.*;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String base = "C:/Users/rogue/OneDrive/Escritorio/uvg tareas y clases/PROGRAMAS/HDT-9";
        String inputDir = base + "/input";
        String outputDir = base + "/output";

        // Crear carpeta de salida si no existe
        new File(outputDir).mkdirs();

        while (true) {
            System.out.println("\n=== MENÚ HUFFMAN ===");
            System.out.println("1. Comprimir archivo");
            System.out.println("2. Descomprimir archivo");
            System.out.println("3. Salir");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    try {
                        System.out.print("Nombre del archivo de entrada (.txt) en /input/: ");
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
                        System.out.println("Compresión completada. Archivos generados en /output/");
                    } catch (IOException e) {
                        System.err.println("Error al comprimir: " + e.getMessage());
                    }
                    break;

                case "2":
                    try {
                        System.out.print("Nombre del archivo .huff en /output/: ");
                        String nombreHuff = scanner.nextLine();
                        String rutaHuff = outputDir + "/" + nombreHuff;

                        System.out.print("Nombre del archivo .hufftree en /output/: ");
                        String nombreTree = scanner.nextLine();
                        String rutaHuffTree = outputDir + "/" + nombreTree;

                        System.out.print("Nombre del archivo descomprimido (.txt) para guardar en /output/: ");
                        String nombreSalida = scanner.nextLine();
                        String rutaSalida = outputDir + "/" + nombreSalida;

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