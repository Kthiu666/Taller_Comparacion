# Taller 6: Comparación de Algoritmos de Ordenación

**Grupo: V**

**Universidad Nacional de Loja** 

**Carrera:** Computación

**Asignatura:** Estructura de Datos 

**Unidad:** 2 - Ordenación y Búsqueda

**Docente:** Ing. Andrés Roberto Navas Castellanos

---

## Descripción y Objetivos
Este proyecto implementa y analiza comparativamente tres algoritmos de ordenamiento básicos (**Burbuja, Selección e Inserción**)

El objetivo principal es determinar experimentalmente cuándo conviene utilizar cada algoritmo en función de variables críticas como el tamaño del dataset ($n$), el grado de orden inicial y la presencia de duplicados

##  Metodología y Diseño Experimental
Siguiendo los lineamientos de la práctica, el sistema de medición implementa:

1.  **Instrumentación:**
    * Contador de **Comparaciones** (`comparisons++`)
    * Contador de **Intercambios** (`swaps++`)
    * Medición de tiempo real con `System.nanoTime()`

2.  **Protocolo de Benchmark ("Warmup"):**
    * Se ejecutan **13 repeticiones** por cada algoritmo y dataset.
    * Se **descartan las 3 primeras** ejecuciones (calentamiento de la JVM/JIT) para evitar distorsiones
    * Se reporta la **Mediana** de las 10 ejecuciones restantes para mayor precisión estadística

3.  **Aislamiento de I/O:** La carga de datos (CSV) se realiza antes de iniciar el cronómetro para medir exclusivamente el rendimiento del algoritmo[cite: 39].

##  Estructura del Proyecto

* `src/ed/u2/sorting/`
    * `Benchmark.java`: Clase principal que orquesta las pruebas y calcula las medianas.
    * `DatasetGenerador.java`: Crea los archivos `.csv` con semilla fija (42) para reproducibilidad.
    * `SortMetrics.java`: Clase contenedora para las métricas (tiempo, swaps, comparaciones).
    * ´BubbleSort.java`: Implementación con optimización de "corte temprano" (`swapped` flag)
    * `SelectionSort.java`: Implementación estándar minimizando swaps.
    * `InsertionSort.java`: Optimizado para inserción in-place.

##  Casos de Prueba (Datasets)
Se generan 4 escenarios específicos definidos en la guía[cite: 42, 45]:

1.  **Aleatorio (`citas_100.csv`):** Fechas y horas dispersas ($n=100$).
2.  **Casi Ordenado (`citas...casi_ordenadas.csv`):** Datos ordenados con un 5% de perturbación ($n=100$).
3.  **Muchos Duplicados (`pacientes_500.csv`):** Apellidos repetidos frecuentemente ($n=500$).
4.  **Orden Inverso (`inventario_500_inverso.csv`):** Stock descendente estricto ($n=500$).

### Tabla o Matriz de Recomendaciones


| Escenario                |                         InsertionSort (Pasadas)                        |                          SelectionSort (Pasadas)                          | BubbleSort (Pasadas) |
|:-------------------------|:----------------------------------------------------------------------:|:-------------------------------------------------------------------------:|:--------------------:|
| Casi ordenado<br/> n<500 |      Se recomienda insertionSort por que realiza menos movimientos     |                                                                           |                      |
| Muchos duplicados        | Aqui tambien se recomienda InsertionSort por que es estable y eficiente |                                                                           |                      |
| Orden inverso            |                                                                        | Se recomienda SelectionSort porque el comportamiento de este es constante |                      |
| Aleatorio peq/ med       |                                                                        |                                                                           | Se recomienda BubbleSort porque ofrece mejor optimizacion al momento de ordenar                     |
| Minimizar swaps          |                                                                        |      Se recomienda SelectionSort porque el maximo de swaps es de n-1      |                      |
|                          |                                                                        |                                                                           |                      |
|                          |                                                                        |                                                                           |                      |



## 5. Comparación

Esta tabla resume cuándo es preferible usar cada algoritmo, basado en sus características.

|   Algoritmo    |                            Cuándo Usarlo (Caso Ideal)                             |                                                Ventaja Clave                                                |
|:--------------:|:---------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------------:|
| **Inserción**  |                  Datos casi ordenados o con datos en tiempo real                  | Es el más eficaz en datos pequeños y  si el arreglo está casi ordenado porque realiza menos comparaciones . |
| **Selección**  |      Para minimizar escritura en memoria ya que realiza menos intercambios.       |   Es el más eficiente en intercambios por que realiza el mínimo número de intercambios de datos posible.    |
|  **Burbuja**   | **Datos pequeños** o para **detectar si un arreglo ya está ordenado** muy rápido. |               Su eficiencia en memoria ya que ordena, los elementos dentro del mismo arreglo. 