package ed.u2.sorting;
import java.util.Arrays;

public class InsertionSort {

    // Metodo de ordenación (sin trazas)
    public static void sort(int[] a) {
        sort(a, false);
    }

    // Sobrecarga para trazas
    public static void sort(int[] a, boolean trace) {
        if (a == null) {
            if (trace) {
                System.out.println("Arreglo es nulo. No se puede ordenar.");
            }
            return;
        }
        if (a.length <= 1) {
            if (trace) {
                System.out.println("Arreglo vacío o de tamaño 1. Se considera ordenado.");
                System.out.println("  -> Estado: " + Arrays.toString(a));
            }
            return;
        }

//        if (trace) {
//            System.out.println("Inicio de InsertionSort ");
//            System.out.println("  -> ORIGINAL: " + Arrays.toString(a));
//        }

        // Bucle externo desde i=1
        for (int i = 1; i < a.length; i++) {
            int key = a[i]; // Elemento a insertar
            int j = i - 1;

            // Mover elementos de a[0..i-1] que son mayores que key
            // a una posición adelante de su posición actual
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];
                j = j - 1;
            }
            a[j + 1] = key;

            // Trazas claras
            if (trace) {
                System.out.println("Iteración (i=" + i + "): Insertando " + key);
                System.out.println("  -> Estado: " + Arrays.toString(a));
            }
        }
    }
}
