public class Edge {
    // Representa una arista dirigida y ponderada en el grafo.

    public Nodo destino; // Nodo al que apunta esta arista (nodo de destino)
    public int peso;     // Peso de la arista (por ejemplo: distancia, tiempo, costo, etc.)

    // Constructor: crea una nueva arista hacia un nodo destino con un peso específico
    public Edge(Nodo destino, int peso) {
        this.destino = destino; // Se asigna el nodo destino
        this.peso = peso;       // Se asigna el valor del peso (ponderación)
    }
}
