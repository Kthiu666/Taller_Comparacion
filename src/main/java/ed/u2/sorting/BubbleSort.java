package ed.u2.sorting;

import java.util.Arrays;

public class BubbleSort {

    //Metodo de ordenacion sin trazas
    public static void sort(int[] a) {
        sort(a, false);
    }
    //Ordenar el arreglo
    public static void sort(int[] a, boolean trace) {
        if (a == null) {
            if (trace) {
                System.out.println("El arreglo es nulo");
            }
            return;
        }

        int n = a.length;
        if (n <= 1) {
            if (trace) {
                System.out.println("El arreglo esta vacio o es de tamaño 1");
                System.out.println("--- Estado:  " +  Arrays.toString(a));
            }
            return;
        }
        for (int i = 0; i < n - 1; i++) {
            //Bandera para saber si hubo algun intercambio
            boolean Swaps = false;
            if (trace) {
                System.out.println();
                System.out.println("Iteracion " + (i + 1));
                System.out.println();
            }

            //Recorrer elementos
            for (int j = 0; j < n - i - 1; j++) {
                //Comparar elementos
                if (a[j] > a[j + 1]) {
                    //Intercambiar elementos
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                    Swaps = true;
                    //mostrar traza del swap
                    if (trace) {
                        System.out.println(" Swap en " + j + " y " + (j + 1));
                        System.out.println("  Estado:  " +  Arrays.toString(a));
                    }

                }
            }

            //Condicion en caso de que no se realicen cambios
            if (!Swaps) {
                if (trace) {
                    System.out.println("La lista ya esta ordenada");
                }
                break;
            }
        }
    }
}
