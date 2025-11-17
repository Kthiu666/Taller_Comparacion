package ed.u2.sorting;

import java.util.Arrays; // Importante que esté

public class SortingDemo {
    public static void main(String[] args) {
        // Datasets de la sección 7
        int[] A = {8, 3, 6, 3, 9};       // [cite: 79]
        int[] B = {5, 4, 3, 2, 1}; // (inverso) [cite: 80]
        int[] C = {1, 2, 3, 4, 5}; // (ya ordenado) [cite: 81]
        int[] D = {2, 2, 2, 2}; // (duplicados) [cite: 82]
        int[] E = {9, 1, 8, 2};       // [cite: 83]
        // Casos adicionales [cite: 60]
        int[] F = {7}; // Tamaño 1
        int[] G = {};  // Vacío

        // Arreglo de datasets para iterar
        int[][] datasets = {A, B, C, D, E, F, G};
        String[] names = {"A", "B", "C", "D", "E", "F (Tamaño 1)", "G (Vacío)"};

        System.out.println("INICIO DE DEMO DE ORDENACIÓN");
        System.out.println("=============================");

        for (int i = 0; i < datasets.length; i++) {
            System.out.println("\n===== Procesando Dataset: " + names[i] + " =====");
            System.out.println("Original: " + Arrays.toString(datasets[i]));


            int[] arrIns = Arrays.copyOf(datasets[i], datasets[i].length);
            int[] arrSel = Arrays.copyOf(datasets[i], datasets[i].length);
            int[] arrBub = Arrays.copyOf(datasets[i], datasets[i].length);

            // 1. Insertion Sort
            System.out.println("\n---- InsertionSort (Trazas) ----");
            InsertionSort.sort(arrIns, true);
            System.out.println("RESULTADO (Insertion): " + Arrays.toString(arrIns));


            // 2. Selection Sort
            System.out.println("\n---- SelectionSort (Trazas) ----");
            SelectionSort.sort(arrSel, true); // ¡Descomentado!
            System.out.println("RESULTADO (Selection): " + Arrays.toString(arrSel));

            // 3. Bubble Sort
            System.out.println("\n---- BubbleSort (Trazas) ----");
            BubbleSort.sort(arrBub, true); // ¡Descomentado!
            System.out.println("RESULTADO (Bubble): " + Arrays.toString(arrBub));

            System.out.println("---------------------------------");
        }
    }
}
