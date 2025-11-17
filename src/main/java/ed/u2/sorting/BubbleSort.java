package ed.u2.sorting;

public class BubbleSort {

    //Llamar al metodo de ordenacion
    public static void sort(int[] a) {
        sort(a, false);
    }
    //Ordenar el arreglo
    public static void sort(int[] a, boolean traza) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            //Bandera para saber si hubo algun intercambio
            boolean Swaps = false;
            if (traza) {
                System.out.println("Iteracion " + (i + 1));
                System.out.println("\n");
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
                    if (traza) {
                        System.out.println(" swap en " + j + "y " + (j + 1));
                    }

                }
            }

            //Condicion en caso de que no se realizaran cambios
            if (!Swaps) {
                if (traza) {
                    System.out.println("La lista ya esta ordenada");
                }
                break;
            }
        }
    }
}
