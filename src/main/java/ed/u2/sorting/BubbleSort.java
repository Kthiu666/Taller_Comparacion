package ed.u2.sorting;

public class BubbleSort {

    public static <T extends Comparable<T>> void sort(T[] a, SortMetrics m) {
        //iniciar cronómetro
        long startTime = System.nanoTime();
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {

            //indicar si hay cambios
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {

                //registrar comparaciones
                m.comparisons++;

                if (a[j].compareTo(a[j + 1]) > 0) {
                    T temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;

                    //registrar swaps
                    m.swaps++;
                    swapped = true;
                }
            }

            //corte temprano
            if (!swapped) {break;}
        }

        //registrar tiempo total
        m.timeNs = System.nanoTime() - startTime;
    }

}
