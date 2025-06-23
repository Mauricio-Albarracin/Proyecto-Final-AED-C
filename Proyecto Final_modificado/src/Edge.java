// Clase que representa una arista en un grafo dirigido y ponderado
public class Edge {
    public Nodo destino; // Nodo al que se dirige esta arista
    public int peso;     // Peso o costo de la arista (puede representar distancia, tiempo, etc.)

    // Constructor de la arista, recibe el nodo destino y el peso de la conexión
    public Edge(Nodo destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }
}
