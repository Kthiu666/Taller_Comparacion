package ed.u2.sorting;

import java.util.Arrays;

public class SelectionSort {

    public static void sort(int[] a) {
        sort(a, false);
    }

    public static void sort(int[] a, boolean trace) {
        if (a == null || a.length <= 1) {
            if (trace) {
                System.out.println("Arreglo nulo o de tamaño <= 1. No se requiere ordenación.");
            }
            return;
        }
        int n = a.length;
        int swaps = 0; //Contabilizar swaps

        if (trace) {
            System.out.println("--- Inicio SelectionSort ---");
            System.out.println("Original: " + Arrays.toString(a));
        }

        // recorre el arreglo para colocar el elemento correcto en a[i]
        for (int i = 0; i < n - 1; i++) {
            int indiceMinimo = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[indiceMinimo]) {
                    indiceMinimo = j;
                }
            }

            // hallar mínimo en i..n-1 y cambiar por a[i]"
            // Solo intercambiar si el mínimo no es ya el elemento en i
            if (indiceMinimo != i) {
                swap(a, i, indiceMinimo);
                swaps++;

                if (trace) {
                    System.out.printf("Paso %d (Swap %d <-> %d): %s\n",
                            i + 1, a[indiceMinimo], a[i], Arrays.toString(a));
                }
            } else {
                if (trace) {
                    System.out.printf("Paso %d (Sin swap): %s\n",
                            i + 1, Arrays.toString(a));
                }
            }
        }

        if (trace) {
            System.out.println("Ordenado: " + Arrays.toString(a));
            System.out.println("Total de intercambios (swaps): " + swaps); //
            System.out.println("--- Fin SelectionSort ---");
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
