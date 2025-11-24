package ed.u2.sorting;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatasetGenerador {

    // Semilla fija para reproducibilidad
    private static final long SEED = 42;
    private static final Random random = new Random(SEED);

    // Pools de datos
    private static final String[] APELLIDOS_POOL = {
            "Guerrero", "Naranjo", "Cedeño", "Castillo", "Torres", "Flores", "Sánchez", "Romero",
            "Benitez", "Hidalgo", "Vargas", "Mora", "Rojas", "Salinas", "Pacheco", "Ponce",
            "Cordero", "Valarezo", "Vélez", "Loaiza", "Jaramillo", "Ortega", "Espinoza", "Reyes",
            "Molina", "Cueva", "Tapia", "Orellana", "Andrade", "Carrion", "Palta", "Sari"
    };

    private static final String[] INSUMOS_POOL = {
            "Guante Nitrilo Talla M", "Alcohol 70% 1L", "Gasas 10x10", "Jeringilla 5ml",
            "Mascarilla N95", "Bisturí #15", "Venda Elástica", "Suero Fisiológico",
            "Paracetamol 500mg", "Amoxicilina 500mg", "Ibuprofeno 400mg", "Algodón Paquete"
    };

    public static void main(String[] args) {
        System.out.println("Generando datasets con semilla " + SEED + "...");

        try {
            generateCitas100();
            generateCitas100CasiOrdenadas(); // Este depende del anterior conceptualmente, pero lo generamos autónomo
            generatePacientes500();
            generateInventario500Inverso();
            System.out.println("¡Éxito! Los 4 archivos CSV han sido creados.");
        } catch (IOException e) {
            System.err.println("Error al escribir archivos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- 1. Citas 100 (Aleatorio) ---
    private static void generateCitas100() throws IOException {
        List<Cita> citas = crearListaCitas(100);
        writeCsv("citas_100.csv", "id;apellido;fechaHora", citas);
    }

    // --- 2. Citas 100 Casi Ordenadas (5% swaps) ---
    private static void generateCitas100CasiOrdenadas() throws IOException {
        // 1. Crear lista base (reiniciando random para consistencia si fuera necesario, pero aquí seguimos el flujo)
        List<Cita> citas = crearListaCitas(100);

        // 2. Ordenar perfectamente por fecha
        citas.sort((c1, c2) -> c1.fechaRaw.compareTo(c2.fechaRaw));

        // 3. Perturbación: 5 swaps (5% de 100)
        int swapsTarget = 5;
        for (int k = 0; k < swapsTarget; k++) {
            int i = random.nextInt(citas.size());
            int j = random.nextInt(citas.size());
            // Asegurar que i != j
            while (i == j) {
                j = random.nextInt(citas.size());
            }
            // Swap
            Cita temp = citas.get(i);
            citas.set(i, citas.get(j));
            citas.set(j, temp);
        }

        writeCsv("citas_100_casi_ordenadas.csv", "id;apellido;fechaHora", citas);
    }

    // --- 3. Pacientes 500 (Muchos duplicados) ---
    private static void generatePacientes500() throws IOException {
        List<String> lines = new ArrayList<>();
        // Pool extendido o sesgado para forzar duplicados
        for (int i = 1; i <= 500; i++) {
            String id = String.format("PAC-%04d", i);

            // Sesgo simple: Los primeros 10 apellidos del pool salen el 60% de las veces
            String apellido;
            int r = random.nextInt(100);
            if (r < 60) {
                apellido = APELLIDOS_POOL[random.nextInt(10)]; // Muy frecuentes
            } else {
                apellido = APELLIDOS_POOL[random.nextInt(APELLIDOS_POOL.length)]; // Resto
            }

            int prioridad = random.nextInt(3) + 1; // 1, 2 o 3
            lines.add(id + ";" + apellido + ";" + prioridad);
        }
        writeCsvLines("pacientes_500.csv", "id;apellido;prioridad", lines);
    }

    // --- 4. Inventario 500 Inverso (Orden descendente estricto) ---
    private static void generateInventario500Inverso() throws IOException {
        List<String> lines = new ArrayList<>();
        int stock = 500;

        for (int i = 1; i <= 500; i++) {
            String id = String.format("ITEM-%04d", i);
            String insumo = INSUMOS_POOL[random.nextInt(INSUMOS_POOL.length)];
            // Stock desciende estrictamente: 500, 499, 498...
            lines.add(id + ";" + insumo + ";" + stock);
            stock--;
        }
        writeCsvLines("inventario_500_inverso.csv", "id;insumo;stock", lines);
    }

    // --- Utilidades ---

    private static List<Cita> crearListaCitas(int n) {
        List<Cita> lista = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // Rango de fechas: Marzo 2025
        LocalDateTime start = LocalDateTime.of(2025, 3, 1, 8, 0);

        for (int i = 1; i <= n; i++) {
            String id = String.format("CITA-%03d", i);
            String apellido = APELLIDOS_POOL[random.nextInt(APELLIDOS_POOL.length)];

            // Generar fecha aleatoria en el mes (aprox 30 días)
            long daysToAdd = random.nextInt(31);
            // Hora entre 08:00 y 18:00 (10 horas rango)
            long hoursToAdd = random.nextInt(11);
            long minutesToAdd = random.nextInt(60);

            LocalDateTime fecha = start.plusDays(daysToAdd).plusHours(hoursToAdd).plusMinutes(minutesToAdd);

            // Validar tope 18:00
            if (fecha.getHour() >= 18 && fecha.getMinute() > 0) {
                fecha = fecha.withHour(17).withMinute(59);
            }

            lista.add(new Cita(id, apellido, fecha, fecha.format(formatter)));
        }
        return lista;
    }

    // Clase auxiliar interna para manipular citas antes de escribir
    static class Cita {
        String id, apellido, fechaStr;
        LocalDateTime fechaRaw;

        public Cita(String id, String apellido, LocalDateTime fechaRaw, String fechaStr) {
            this.id = id;
            this.apellido = apellido;
            this.fechaRaw = fechaRaw;
            this.fechaStr = fechaStr;
        }

        @Override
        public String toString() {
            return id + ";" + apellido + ";" + fechaStr;
        }
    }

    private static void writeCsv(String filename, String header, List<Cita> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, StandardCharsets.UTF_8))) {
            writer.write(header);
            writer.newLine();
            for (Cita c : data) {
                writer.write(c.toString());
                writer.newLine();
            }
        }
    }

    private static void writeCsvLines(String filename, String header, List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, StandardCharsets.UTF_8))) {
            writer.write(header);
            writer.newLine();
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}
