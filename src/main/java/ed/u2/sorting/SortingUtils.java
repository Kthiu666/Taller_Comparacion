package ed.u2.sorting;

public class SortingUtils {
    /**
     * Crea y devuelve una copia de un arreglo.
     * Necesitamos probar los 3 algoritmos sobre los mismos datos iniciales.
     */
    public static int[] copyArray(int[] original) {
        if (original == null) return null;
        int[] copy = new int[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }
}
