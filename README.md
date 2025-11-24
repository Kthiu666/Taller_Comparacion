# Taller 6: Comparación Empírica de Algoritmos de Ordenación

**Grupo:** V
**Universidad Nacional de Loja**
**Carrera:** Computación
**Asignatura:** Estructura de Datos
**Unidad:** 2 - Ordenación y Búsqueda
**Docente:** Ing. Andrés Roberto Navas Castellanos

---

## 1. Descripción y Objetivos
Este proyecto implementa y analiza comparativamente tres algoritmos de ordenamiento básicos (**Burbuja, Selección e Inserción**).

El objetivo principal es determinar experimentalmente la eficiencia de cada algoritmo (medida en tiempo, comparaciones e intercambios) bajo diferentes escenarios controlados: tamaño del dataset ($n$), grado de orden inicial y presencia de duplicados.

## 2. Metodología y Diseño Experimental
El sistema de medición implementa:

1.  **Instrumentación:**
    * Clase `SortMetrics` para encapsular contadores.
    * Contador de **Comparaciones** (lecturas lógicas) `comparisons++`
    * Contador de **Intercambios/Desplazamientos** (escrituras en memoria). `swaps++`
    * Medición de tiempo de alta precisión con `System.nanoTime()`.

2.  **Protocolo de Benchmark ("Warmup" y Mediana):**
    * Se ejecutan **13 repeticiones** por cada algoritmo y dataset.
    * Se **descartan las 3 primeras** ejecuciones (calentamiento de la JVM/JIT Compiler) para evitar datos atípicos.
    * Se reporta la **Mediana** de las 10 ejecuciones válidas restantes para mayor robustez estadística.

3.  **Aislamiento de I/O:** La carga de datos (CSV) se realiza una sola vez antes de iniciar el cronómetro.
4.  **Integridad de Datos:** Se utiliza `Arrays.copyOf()` antes de cada ejecución para garantizar que cada algoritmo trabaje sobre una copia fresca del dataset original desordenado.

## 3. Estructura del Proyecto

* `src/ed/u2/sorting/`
    * **`Benchmark.java`**: Clase principal. Orquesta la carga, las 13 ejecuciones, el cálculo de medianas y la impresión de tablas.
    * **`DatasetGenerador.java`**: Genera los 4 archivos `.csv` utilizando una semilla fija (`SEED=42`) para garantizar reproducibilidad exacta.
    * **`DatasetLoader.java`**: Parsea los archivos CSV y los convierte en arreglos de objetos `Comparable` (Citas, Pacientes, Items).
    * **`SortMetrics.java`**: Objeto contenedor para transportar los resultados de cada ejecución.
    * **`BubbleSort.java`**: Implementación con optimización de "corte temprano".
    * **`SelectionSort.java`**: Implementación estándar orientada a minimizar swaps.
    * **`InsertionSort.java`**: Implementación estándar eficiente para datos casi ordenados.

## 4. Casos de Prueba (Datasets)

Se generaron 4 escenarios específicos para estresar los algoritmos de formas distintas:

1.  **Aleatorio (`citas_100.csv`):** 100 citas con fechas dispersas. Escenario promedio.
2.  **Casi Ordenado (`citas_100_casi_ordenadas.csv`):** Datos ordenados con solo un 5% de perturbación (swaps aleatorios). Prueba la adaptabilidad.
3.  **Muchos Duplicados (`pacientes_500.csv`):** 500 pacientes con apellidos muy repetidos. Prueba la estabilidad y el manejo de claves iguales.
4.  **Orden Inverso (`inventario_500_inverso.csv`):** 500 items con stock descendente estricto. El "Peor Caso" teórico para algoritmos $O(n^2)$.

---

## 5. Resultados Experimentales

A continuación se presentan las **medianas** obtenidas tras la ejecución del Benchmark en nuestro entorno de pruebas:

### Dataset 1: Aleatorio (n=100)
*Archivo: `citas_100.csv`*

| Algoritmo           | Comparaciones | Swaps | Tiempo (ms)  |
|:--------------------|:--------------|:------|:-------------|
| **Bubble Sort**     | 4,944         | 2,381 | 0.354 ms     |
| **Selection Sort**  | 4,950         | 95    | 0.195 ms     |
| **Insertion Sort**  | 2,476         | 2,381 | **0.103 ms** |

### Dataset 2: Casi Ordenado (n=100, 5% desorden)
*Archivo: `citas_100_casi_ordenadas.csv`*

| Algoritmo          | Comparaciones | Swaps | Tiempo (ms)  |
|:-------------------|:--------------|:------|:-------------|
| **Bubble Sort**    | 4,422         | 271   | 0.035 ms     |
| **Selection Sort** | 4,950         | 5     | 0.024 ms     |
| **Insertion Sort** | **370**       | 271   | **0.016 ms** |

### Dataset 3: Muchos Duplicados (n=500)
*Archivo: `pacientes_500.csv`*

| Algoritmo          | Comparaciones | Swaps   | Tiempo (ms)  |
|:-------------------|:--------------|:--------|:-------------|
| **Bubble Sort**    | 124,084       | 56,312  | 2.238 ms     |
| **Selection Sort** | 124,750       | **490** | 1.827 ms     |
| **Insertion Sort** | 56,808        | 56,312  | **0.785 ms** |

### Dataset 4: Orden Inverso (n=500)
*Archivo: `inventario_500_inverso.csv`*

| Algoritmo          | Comparaciones | Swaps   | Tiempo (ms)  |
|:-------------------|:--------------|:--------|:-------------|
| **Bubble Sort**    | 124,750       | 124,750 | 0.811 ms     |
| **Selection Sort** | 124,750       | **250** | **0.428 ms** |
| **Insertion Sort** | 124,750       | 124,750 | 0.502 ms     |

---

## 6. Análisis y Matriz de Recomendación

Basado en los datos empíricos presentados arriba, se establece la siguiente guía de selección:

| Escenario                     | Algoritmo Ganador  | Justificación Técnica basada en Resultados                                                                                                                                                                                                                                                |
|:------------------------------|:-------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Datos Casi Ordenados**      | **Insertion Sort** | **Adaptabilidad Extrema.** En el Dataset 2, Inserción redujo las comparaciones a solo **370** (vs 4,950 de Selección), logrando el tiempo récord de **0.016 ms**. Esto confirma que su complejidad se acerca a $O(n)$ cuando el desorden es bajo.                                         |
| **Minimizar Escrituras**      | **Selection Sort** | **Eficiencia de Escritura.** En todos los casos, Selección realizó drásticamente menos swaps. En el caso Inverso (Dataset 4), mientras Burbuja e Inserción hicieron **124,750** swaps, Selección solo hizo **250**. Si escribir en disco/memoria es costoso, este es el algoritmo a usar. |
| **Orden Inverso (Peor Caso)** | **Selection Sort** | **Sorpresa en Rendimiento.** Aunque teóricamente todos son $O(n^2)$, en el Dataset 4 Selección fue el más rápido (**0.428 ms** vs 0.811 ms de Burbuja) debido a que el costo de mover datos (swaps) dominó sobre el costo de comparar.                                                    |
| **Estabilidad (Duplicados)**  | **Insertion Sort** | En el Dataset 3 (Pacientes), Inserción fue el más rápido (**0.785 ms**) y, al ser estable, preserva el orden de llegada de los pacientes con la misma prioridad/apellido. Selection Sort no garantiza esto.                                                                               |
| **Caso General Pequeño**      | **Insertion Sort** | En el caso aleatorio (Dataset 1), Inserción fue 3 veces más rápido que Burbuja y 2 veces más rápido que Selección, confirmando que tiene las mejores constantes para $n$ pequeños.                                                                                                        |

## 7. Conclusiones Finales

1.  **Inserción:** En la mayoría de escenarios "naturales" (donde los datos tienen cierto orden parcial o son aleatorios), **Insertion Sort** ofreció el mejor tiempo de respuesta (0.103 ms en aleatorio vs 0.354 ms de Burbuja).

2.  **Selection Sort:** Se observó que Selection Sort realiza exactamente $N-1$ intercambios en el peor de los casos (o $N/2$ en el caso inverso). En el dataset inverso, esta característica le permitió ganar la carrera de tiempo (**0.428 ms**), superando incluso a Inserción.

3.  **Burbuja:** A pesar de la optimización de "corte temprano", Bubble Sort fue consistentemente el más lento en casi todos los escenarios (llegando a 2.238 ms en el dataset de pacientes), debido a la excesiva cantidad de intercambios redundantes que realiza para "burbujear" elementos.