package ed.u2.sorting;

public class SelectionSort {

    public static <T extends Comparable<T>> void sort(T[] a, SortMetrics m) {
        long startTime = System.nanoTime();
        int n = a.length;

        for (int i = 0; i < n - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < n; j++) {

                //contar comparaciones
                m.comparisons++;

                if (a[j].compareTo(a[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {

                T tmp = a[i];
                a[i] = a[minIndex];
                a[minIndex] = tmp;

                m.swaps++; //registrar swaps

            }
        }
        //registrar tiempo total
        m.timeNs = System.nanoTime() - startTime;
    }

}
