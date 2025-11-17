package ed.u2.sorting;

import java.util.Arrays;

public class SortingDemo {
    public static void main(String[] args) {
        // Datasets de la sección 7
        int[] A = {8, 3, 6, 3, 9};
        int[] B = {5, 4, 3, 2, 1}; // (inverso)
        int[] C = {1, 2, 3, 4, 5}; // (ya ordenado)
        int[] D = {2, 2, 2, 2}; // (duplicados)
        int[] E = {9, 1, 8, 2};
        // Casos borde adicionales
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

            // Copiamos los arreglos porque los sorts son IN-PLACE
            int[] arrIns = SortingUtils.copyArray(datasets[i]);
            int[] arrSel = SortingUtils.copyArray(datasets[i]);
            int[] arrBub = SortingUtils.copyArray(datasets[i]);

            // 1. Insertion Sort
            System.out.println("\n---- InsertionSort (Trazas) ----");
            InsertionSort.sort(arrIns, true);
            System.out.println("RESULTADO (Insertion): " + Arrays.toString(arrIns));

            // 2. Selection Sort
            System.out.println("\n---- SelectionSort (Trazas) ----");
            //SelectionSort.sort(arrSel, true);
            System.out.println("RESULTADO (Selection): " + Arrays.toString(arrSel));

            // 3. Bubble Sort
            System.out.println("\n---- BubbleSort (Trazas) ----");
            BubbleSort.sort(arrBub, true);
            System.out.println("RESULTADO (Bubble): " + Arrays.toString(arrBub));

            System.out.println("---------------------------------");
        }
    }
}
