package ed.u2.sorting;

public class InsertionSort {
    /*
    Ordenar elementos y registrar comparaciones,
    tiempo de ejecución y swaps
    * */

    public static <T extends Comparable<T>> void sort(T[] a, SortMetrics m) {
        //Iniciar cronómetro
        long start = System.nanoTime();
        int n = a.length;

        for (int i = 1; i < n; i++) {
            //elemento a insertar en la posición correspondiente
            T key = a[i];

            int j = i - 1;

            while (j >= 0){
                m.comparisons++; //contar comparaciones
                if (a[j].compareTo(key) > 0){
                    a[j + 1] = a[j];
                    m.swaps++;
                    j--;
                }else {break;}
        }a[j + 1] = key;
        }
        //Registrar tiempo total
        m.timeNs = System.nanoTime() - start;
    }
}
