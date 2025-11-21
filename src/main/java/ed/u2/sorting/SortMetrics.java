package ed.u2.sorting;

//Almacenar métricas de ejecución de los algoritmos
//Permite medir comparaciones, swaps, tiempo de ejecución

public class SortMetrics {

    //numero total de comparaciones realizadas para ordenar
    public long comparisons = 0;
    public long swaps = 0;   //numero de intercambios
    public long timeNs = 0;  //tiempo de ejecución

    //restaurar a cero antes de hacer una nueva ejecución
    public void reset(){
        comparisons = 0;
        swaps = 0;
        timeNs = 0;
    }
}
