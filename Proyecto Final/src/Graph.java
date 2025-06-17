import java.util.*; // Se importa el paquete util para usar Map, List, HashMap, ArrayList

// Clase que representa el grafo dirigido y ponderado
public class Graph {
    private Map<Nodo, List<Edge>> adjList;
    // Estructura de datos principal:
    // Cada Nodo (ubicación del almacén) tiene asociada una lista de aristas (rutas hacia otros nodos)

    public Graph() {
        adjList = new HashMap<>();
        // Inicializa el mapa vacío de adyacencias (grafo sin nodos ni aristas)
    }

    // Agrega un nuevo nodo al grafo si no existe ya
    public void agregarNodo(Nodo nodo) {
        adjList.putIfAbsent(nodo, new ArrayList<>());
    }

    // Elimina un nodo del grafo y todas las aristas que lo tengan como destino
    public void eliminarNodo(Nodo nodo) {
        adjList.remove(nodo); // Elimina el nodo y sus aristas salientes

        // Elimina todas las aristas entrantes hacia el nodo eliminado
        for (List<Edge> edges : adjList.values()) {
            edges.removeIf(e -> e.destino.equals(nodo));
        }
    }

    // Agrega una arista dirigida con peso entre dos nodos existentes
    public void agregarArista(Nodo origen, Nodo destino, int peso) {
        if (!adjList.containsKey(origen) || !adjList.containsKey(destino)) {
            System.out.println("Uno de los nodos no existe.");
            return;
        }
        adjList.get(origen).add(new Edge(destino, peso));
    }

    // Elimina una arista desde un nodo origen hacia un nodo destino
    public void eliminarArista(Nodo origen, Nodo destino) {
        if (adjList.containsKey(origen)) {
            adjList.get(origen).removeIf(e -> e.destino.equals(destino));
        }
    }

    // Modifica el peso de una arista entre dos nodos
    public void modificarArista(Nodo origen, Nodo destino, int nuevoPeso) {
        if (adjList.containsKey(origen)) {
            for (Edge e : adjList.get(origen)) {
                if (e.destino.equals(destino)) {
                    e.peso = nuevoPeso;
                    return;
                }
            }
        }
    }

    // Muestra el contenido del grafo: nodos y sus aristas
    public void mostrarGrafo() {
        for (Nodo nodo : adjList.keySet()) {
            System.out.print(nodo + " -> ");
            for (Edge e : adjList.get(nodo)) {
                System.out.print("[" + e.destino.getId() + ", peso: " + e.peso + "] ");
            }
            System.out.println();
        }
    }
}
