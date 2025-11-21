package ed.u2.sorting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatasetLoader {

    // Clase para representar una cita
    public static class Cita implements Comparable<Cita> {
        public String id;
        public String apellido;
        public LocalDateTime fechaHora;

        public Cita(String id, String apellido, LocalDateTime fechaHora) {
            this.id = id;
            this.apellido = apellido;
            this.fechaHora = fechaHora;
        }

        @Override
        public int compareTo(Cita otra) {
            return this.fechaHora.compareTo(otra.fechaHora);
        }

        @Override
        public String toString() {
            return id + ";" + apellido + ";" + fechaHora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        }
    }

    // Clase para representar un paciente
    public static class Paciente implements Comparable<Paciente> {
        public String id;
        public String apellido;
        public int prioridad;

        public Paciente(String id, String apellido, int prioridad) {
            this.id = id;
            this.apellido = apellido;
            this.prioridad = prioridad;
        }

        @Override
        public int compareTo(Paciente otro) {
            return this.apellido.compareTo(otro.apellido);
        }
    }

    // Clase para representar un item de inventario
    public static class InventarioItem implements Comparable<InventarioItem> {
        public String id;
        public String insumo;
        public int stock;

        public InventarioItem(String id, String insumo, int stock) {
            this.id = id;
            this.insumo = insumo;
            this.stock = stock;
        }

        @Override
        public int compareTo(InventarioItem otro) {
            return Integer.compare(this.stock, otro.stock);
        }
    }

    // Cargar dataset de citas
    public static Cita[] loadCitasDataset(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        List<Cita> citas = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // Saltar encabezado
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (!line.isEmpty()) {
                String[] parts = line.split(";");
                if (parts.length >= 3) {
                    String id = parts[0];
                    String apellido = parts[1];
                    LocalDateTime fechaHora = LocalDateTime.parse(parts[2], formatter);
                    citas.add(new Cita(id, apellido, fechaHora));
                }
            }
        }

        return citas.toArray(new Cita[0]);
    }

    // Cargar dataset de pacientes
    public static Paciente[] loadPacientesDataset(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        List<Paciente> pacientes = new ArrayList<>();

        // Saltar encabezado
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (!line.isEmpty()) {
                String[] parts = line.split(";");
                if (parts.length >= 3) {
                    String id = parts[0];
                    String apellido = parts[1];
                    int prioridad = Integer.parseInt(parts[2]);
                    pacientes.add(new Paciente(id, apellido, prioridad));
                }
            }
        }

        return pacientes.toArray(new Paciente[0]);
    }

    // Cargar dataset de inventario
    public static InventarioItem[] loadInventarioDataset(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        List<InventarioItem> items = new ArrayList<>();

        // Saltar encabezado
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (!line.isEmpty()) {
                String[] parts = line.split(";");
                if (parts.length >= 3) {
                    String id = parts[0];
                    String insumo = parts[1];
                    int stock = Integer.parseInt(parts[2]);
                    items.add(new InventarioItem(id, insumo, stock));
                }
            }
        }

        return items.toArray(new InventarioItem[0]);
    }
}
