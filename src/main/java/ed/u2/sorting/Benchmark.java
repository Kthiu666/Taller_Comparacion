package ed.u2.sorting;

import java.io.IOException;
import java.util.Arrays;

public class Benchmark {
    private static final int TOTAL_RUNS = 13;
    private static final int WARMUP = 3;
    private static final int VALID_RUNS = TOTAL_RUNS - WARMUP;

    public static void main(String[] args) {
        System.out.println("BENCHMARK DE ALGORITMOS DE ORDENACIÓN");
        System.out.println("=====================================\n");

        try {
            // Cargar datasets
            DatasetLoader.Cita[] citas100 = DatasetLoader.loadCitasDataset("citas_100.csv");
            DatasetLoader.Cita[] citasCasi = DatasetLoader.loadCitasDataset("citas_100_casi_ordenadas.csv");
            DatasetLoader.Paciente[] pacientes = DatasetLoader.loadPacientesDataset("pacientes_500.csv");
            DatasetLoader.InventarioItem[] inventario = DatasetLoader.loadInventarioDataset("inventario_500_inverso.csv");

            // Ejecutar benchmarks
            runBenchmark(citas100, "citas_100.csv", "n=100, aleatorio");
            runBenchmark(citasCasi, "citas_100_casi_ordenadas.csv", "n=100, casi-ordenado");
            runBenchmark(pacientes, "pacientes_500.csv", "n=500, muchos duplicados");
            runBenchmark(inventario, "inventario_500_inverso.csv", "n=500, orden inverso");

        } catch (IOException e) {
            System.err.println("❌ Error cargando datasets: " + e.getMessage());
        }
    }

    private static <T extends Comparable<T>> void runBenchmark(T[] data, String filename, String desc) {
        System.out.println("=== " + filename + " ===");
        System.out.println(desc + " | n = " + data.length);

        if (data instanceof DatasetLoader.Paciente[]) {
            showDuplicates((DatasetLoader.Paciente[]) data);
        }
        System.out.println();

        BenchmarkResult[] results = new BenchmarkResult[VALID_RUNS];

        for (int r = 0; r < TOTAL_RUNS; r++) {
            T[] copyBubble = Arrays.copyOf(data, data.length);
            T[] copySelection = Arrays.copyOf(data, data.length);
            T[] copyInsertion = Arrays.copyOf(data, data.length);

            SortMetrics mBubble = runAlgorithm(copyBubble, "BUBBLE");
            SortMetrics mSelection = runAlgorithm(copySelection, "SELECTION");
            SortMetrics mInsertion = runAlgorithm(copyInsertion, "INSERTION");

            if (r >= WARMUP) {
                results[r - WARMUP] = new BenchmarkResult(mBubble, mSelection, mInsertion);
            }
        }

        showResults(calculateMedian(results), data.length);
        System.out.println();
    }

    private static <T extends Comparable<T>> SortMetrics runAlgorithm(T[] data, String algo) {
        SortMetrics m = new SortMetrics();
        switch (algo) {
            case "BUBBLE": BubbleSort.sort(data, m); break;
            case "SELECTION": SelectionSort.sort(data, m); break;
            case "INSERTION": InsertionSort.sort(data, m); break;
        }
        return m;
    }

    private static void showResults(BenchmarkResult median, int n) {
        System.out.println("RESULTADOS (Medianas):");
        System.out.println("┌───────────────────┬──────────────┬──────────────┬───────────────┬──────────────┐");
        System.out.println("│     Algoritmo     │ Comparaciones │    Swaps     │  Tiempo (ns)  │ Tiempo (ms)  │");
        System.out.println("├───────────────────┼──────────────┼──────────────┼───────────────┼──────────────┤");

        System.out.printf("│ %-17s │ %-12d │ %-12d │ %-13d │ %-12.3f │%n",
                "Bubble Sort", median.bubbleComparisons, median.bubbleSwaps, median.bubbleTimeNs, median.bubbleTimeNs / 1e6);
        System.out.printf("│ %-17s │ %-12d │ %-12d │ %-13d │ %-12.3f │%n",
                "Selection Sort", median.selectionComparisons, median.selectionSwaps, median.selectionTimeNs, median.selectionTimeNs / 1e6);
        System.out.printf("│ %-17s │ %-12d │ %-12d │ %-13d │ %-12.3f │%n",
                "Insertion Sort", median.insertionComparisons, median.insertionSwaps, median.insertionTimeNs, median.insertionTimeNs / 1e6);

        System.out.println("└───────────────────┴──────────────┴──────────────┴───────────────┴──────────────┘");

        // Análisis rápido
        String fastest = findFastest(median);
        System.out.println("\nANÁLISIS RÁPIDO:");
        System.out.println("• Más rápido: " + fastest);
        System.out.printf("• Selection Sort: %d comparaciones (teóricas: %d)%n",
                median.selectionComparisons, n * (n - 1) / 2);
    }

    private static BenchmarkResult calculateMedian(BenchmarkResult[] results) {
        long[] bTimes = new long[VALID_RUNS];
        long[] sTimes = new long[VALID_RUNS];
        long[] iTimes = new long[VALID_RUNS];

        for (int i = 0; i < VALID_RUNS; i++) {
            bTimes[i] = results[i].bubbleTimeNs;
            sTimes[i] = results[i].selectionTimeNs;
            iTimes[i] = results[i].insertionTimeNs;
        }

        Arrays.sort(bTimes);
        Arrays.sort(sTimes);
        Arrays.sort(iTimes);

        int mid = VALID_RUNS / 2;
        BenchmarkResult r = results[mid];

        return new BenchmarkResult(
                r.bubbleComparisons, r.bubbleSwaps, bTimes[mid],
                r.selectionComparisons, r.selectionSwaps, sTimes[mid],
                r.insertionComparisons, r.insertionSwaps, iTimes[mid]
        );
    }

    private static String findFastest(BenchmarkResult median) {
        long b = median.bubbleTimeNs, s = median.selectionTimeNs, i = median.insertionTimeNs;
        if (b <= s && b <= i) return "Bubble Sort";
        if (s <= b && s <= i) return "Selection Sort";
        return "Insertion Sort";
    }

    private static void showDuplicates(DatasetLoader.Paciente[] pacientes) {
        int dup = 0;
        for (int i = 1; i < pacientes.length; i++) {
            if (pacientes[i].apellido.equals(pacientes[i-1].apellido)) dup++;
        }
        System.out.printf("Duplicados: %d (%.1f%%)", dup, (dup * 100.0) / pacientes.length);
    }

    static class BenchmarkResult {
        long bubbleComparisons, bubbleSwaps, bubbleTimeNs;
        long selectionComparisons, selectionSwaps, selectionTimeNs;
        long insertionComparisons, insertionSwaps, insertionTimeNs;

        public BenchmarkResult(SortMetrics b, SortMetrics s, SortMetrics i) {
            this.bubbleComparisons = b.comparisons;
            this.bubbleSwaps = b.swaps;
            this.bubbleTimeNs = b.timeNs;
            this.selectionComparisons = s.comparisons;
            this.selectionSwaps = s.swaps;
            this.selectionTimeNs = s.timeNs;
            this.insertionComparisons = i.comparisons;
            this.insertionSwaps = i.swaps;
            this.insertionTimeNs = i.timeNs;
        }

        public BenchmarkResult(long bc, long bs, long bt, long sc, long ss, long st, long ic, long is, long it) {
            this.bubbleComparisons = bc;
            this.bubbleSwaps = bs;
            this.bubbleTimeNs = bt;
            this.selectionComparisons = sc;
            this.selectionSwaps = ss;
            this.selectionTimeNs = st;
            this.insertionComparisons = ic;
            this.insertionSwaps = is;
            this.insertionTimeNs = it;
        }
    }
}