import java.util.*; // Se importa el paquete util para usar Map, List, HashMap, ArrayList, etc.

// Clase que representa el grafo dirigido y ponderado
public class Grafo {
    // Mapa que representa la lista de adyacencia del grafo
    // Cada clave es un nodo (Nodo origen) y su valor es una lista de aristas (conexiones salientes de ese nodo)
    private Map<Nodo, List<Edge>> adjList;

    // Constructor de la clase Grafo
    public Grafo() {
        // Inicializa el mapa como un HashMap vacío al crear un nuevo grafo
        adjList = new HashMap<>();
    }

    // Agrega un nuevo nodo al grafo
    public void agregarNodo(Nodo nodo) {
        // Si el nodo no existe en la lista de adyacencia, lo agrega con una lista vacía de aristas salientes
        adjList.putIfAbsent(nodo, new ArrayList<>());
    }


    // Elimina un nodo y todas las aristas que lo tienen como destino
    public void eliminarNodo(Nodo nodo) {
        // Elimina la entrada completa del nodo en la lista de adyacencia (quita sus aristas salientes)
        adjList.remove(nodo);

        // Recorre todas las listas de aristas salientes de los demás nodos
        for (List<Edge> edges : adjList.values()) {
            // Elimina todas las aristas cuyo destino sea el nodo que se quiere eliminar
            edges.removeIf(e -> e.destino.equals(nodo));
        }
    }


    // Agrega una arista dirigida con peso entre dos nodos
    public void agregarArista(Nodo origen, Nodo destino, int peso) {
        // Verifica que ambos nodos existan en el grafo antes de agregar la arista
        if (!adjList.containsKey(origen) || !adjList.containsKey(destino)) {
            // Si alguno no existe, muestra un mensaje de error y no agrega la arista
            System.out.println("Uno de los nodos no existe.");
            return;
        }

        // Agrega una nueva arista al final de la lista de adyacencia del nodo origen
        adjList.get(origen).add(new Edge(destino, peso));
    }


    // Elimina una arista desde un nodo origen hacia un nodo destino
    public void eliminarArista(Nodo origen, Nodo destino) {
        // Verifica que el nodo origen exista en la lista de adyacencia
        if (adjList.containsKey(origen)) {
            // Elimina todas las aristas cuyo destino coincida con el nodo destino dado
            adjList.get(origen).removeIf(e -> e.destino.equals(destino));
        }
    }


    // Modifica el peso de una arista existente entre dos nodos en el grafo
    public void modificarArista(Nodo origen, Nodo destino, int nuevoPeso) {
        
        // Verifica si el nodo origen existe en la lista de adyacencia (es decir, si tiene aristas salientes)
        if (adjList.containsKey(origen)) {

            // Recorre todas las aristas salientes del nodo origen
            for (Edge e : adjList.get(origen)) {

                // Compara si el destino de la arista actual es igual al nodo destino que estamos buscando
                if (e.destino.equals(destino)) {

                    // Si lo encuentra, actualiza el peso de la arista con el nuevo valor proporcionado
                    e.peso = nuevoPeso;

                    // Termina la ejecución del método porque ya se encontró y actualizó la arista
                    return;
                }
            }
        }
    }


    // Muestra todos los nodos y sus aristas
    public void mostrarGrafo() {
        for (Nodo nodo : adjList.keySet()) {
            System.out.print(nodo + " -> ");
            for (Edge e : adjList.get(nodo)) {
                System.out.print("[" + e.destino.getId() + ", peso: " + e.peso + "] ");
            }
            System.out.println();
        }
    }

    // Dijkstra: calcula la distancia mínima desde el nodo origen a todos los demás nodos del grafo
    public Map<Nodo, Integer> dijkstra(Nodo origen) {

        // Mapa que almacena la distancia mínima conocida desde el nodo origen a cada nodo
        Map<Nodo, Integer> distancias = new HashMap<>();

        // Mapa que almacena el nodo anterior en el camino más corto hacia cada nodo
        Map<Nodo, Nodo> previos = new HashMap<>();

        // Conjunto que registra los nodos que ya han sido procesados (para no repetir)
        Set<Nodo> visitados = new HashSet<>();

        // Inicializar todas las distancias como "infinito" (Integer.MAX_VALUE), excepto la del nodo origen
        for (Nodo nodo : adjList.keySet()) {
            distancias.put(nodo, Integer.MAX_VALUE);
        }
        distancias.put(origen, 0); // La distancia desde el nodo origen a sí mismo es 0

        // Cola de prioridad que siempre da el nodo con la menor distancia acumulada (según 'distancias')
        PriorityQueue<Nodo> cola = new PriorityQueue<>(Comparator.comparingInt(distancias::get));

        // Se agrega el nodo origen como punto de partida
        cola.add(origen);

        // Bucle principal del algoritmo: se repite mientras haya nodos por procesar
        while (!cola.isEmpty()) {

            // Extrae el nodo con la menor distancia actual conocida
            Nodo actual = cola.poll();

            // Si el nodo ya fue visitado antes, se ignora (ya se procesó de forma óptima)
            if (visitados.contains(actual)) continue;

            // Se marca el nodo como visitado para evitar procesarlo de nuevo
            visitados.add(actual);

            // Iterar sobre todas las aristas salientes desde el nodo actual
            for (Edge arista : adjList.getOrDefault(actual, new ArrayList<>())) {

                // Nodo vecino alcanzable desde el nodo actual
                Nodo vecino = arista.destino;

                // Calcular la nueva distancia acumulada al vecino pasando por el nodo actual
                int nuevaDistancia = distancias.get(actual) + arista.peso;

                // Si la nueva distancia es mejor (menor) que la previamente registrada
                if (nuevaDistancia < distancias.get(vecino)) {

                    // Se actualiza la distancia mínima al vecino
                    distancias.put(vecino, nuevaDistancia);

                    // Se registra que para llegar al vecino, se pasó por el nodo actual
                    previos.put(vecino, actual);

                    // Se agrega el vecino a la cola de prioridad para ser procesado más adelante
                    cola.add(vecino);
                }
            }
        }

        // Mostrar en consola las distancias mínimas desde el nodo origen a cada nodo del grafo
        for (Nodo destino : distancias.keySet()) {
            System.out.println("Distancia desde " + origen.getId() + " hasta " + destino.getId() + ": " + distancias.get(destino));
        }

        // Retorna el mapa con las distancias mínimas calculadas desde el nodo origen
        return distancias;
    }


    // Método para realizar un recorrido BFS (Breadth-First Search) desde un nodo origen
    public void bfs(Nodo origen) {

        // Conjunto para registrar los nodos que ya han sido visitados
        Set<Nodo> visitados = new HashSet<>();

        // Cola para controlar el orden en que se visitan los nodos (estructura clave del BFS)
        Queue<Nodo> cola = new LinkedList<>();

        // Se marca el nodo de origen como visitado
        visitados.add(origen);

        // Se agrega el nodo de origen a la cola para iniciar el recorrido
        cola.add(origen);

        // Imprime el nodo desde el cual comienza el recorrido BFS
        System.out.println("BFS desde: " + origen.getId());

        // Bucle principal: se repite mientras haya nodos pendientes en la cola
        while (!cola.isEmpty()) {

            // Extrae el siguiente nodo en la cola (el primero en llegar)
            Nodo actual = cola.poll();

            // Muestra el identificador del nodo actual por consola
            System.out.print(actual.getId() + " -> ");

            // Recorre todas las aristas (conexiones) salientes desde el nodo actual
            for (Edge arista : adjList.getOrDefault(actual, new ArrayList<>())) {

                // Obtiene el nodo vecino (nodo al que apunta la arista)
                Nodo vecino = arista.destino;

                // Si el vecino aún no ha sido visitado, se marca y se agrega a la cola
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }

        // Imprime un salto de línea final para cerrar la salida del recorrido
        System.out.println();
    }


    // Método público para realizar un recorrido en profundidad (DFS) desde un nodo origen
    public void dfs(Nodo origen) {

        // Conjunto para registrar los nodos que ya han sido visitados
        Set<Nodo> visitados = new HashSet<>();

        // Imprime el nodo desde el cual se inicia el recorrido DFS
        System.out.println("DFS desde: " + origen.getId());

        // Inicia el recorrido DFS llamando al método recursivo
        dfsRecursivo(origen, visitados);

        // Imprime un salto de línea al final del recorrido
        System.out.println();
    }

    // Método recursivo que implementa DFS (Depth-First Search)
    private void dfsRecursivo(Nodo actual, Set<Nodo> visitados) {

        // Marca el nodo actual como visitado
        visitados.add(actual);

        // Imprime el identificador del nodo actual
        System.out.print(actual.getId() + " -> ");

        // Recorre todas las aristas salientes del nodo actual
        for (Edge arista : adjList.getOrDefault(actual, new ArrayList<>())) {

            // Obtiene el nodo vecino al que apunta la arista
            Nodo vecino = arista.destino;

            // Si el vecino aún no ha sido visitado, se llama recursivamente para continuar el recorrido
            if (!visitados.contains(vecino)) {
                dfsRecursivo(vecino, visitados);
            }
        }
    }

    // Método principal que verifica si el grafo contiene ciclos dirigidos
    public boolean tieneCiclos() {

        // Conjunto para registrar los nodos ya visitados por completo (procesados)
        Set<Nodo> visitados = new HashSet<>();

        // Conjunto para llevar el seguimiento de los nodos en la pila de recursión actual
        Set<Nodo> enRecursion = new HashSet<>();

        // Recorre todos los nodos del grafo (necesario porque puede haber nodos no conectados)
        for (Nodo nodo : adjList.keySet()) {

            // Si se detecta un ciclo al hacer DFS desde este nodo, se retorna true
            if (tieneCicloDFS(nodo, visitados, enRecursion)) {
                return true; // ¡Ajá! Un ciclo ha sido descubierto en las sombras del grafo
            }
        }

        // Si se ha revisado todo el grafo y no se halló ningún ciclo, se retorna false
        return false;
    }

    // Método recursivo que realiza DFS y detecta si hay un ciclo desde el nodo actual
    private boolean tieneCicloDFS(Nodo actual, Set<Nodo> visitados, Set<Nodo> enRecursion) {

        // Si el nodo ya está en el conjunto de recursión, significa que hemos vuelto a él: ¡hay un ciclo!
        if (enRecursion.contains(actual)) return true;

        // Si ya fue visitado antes y no estaba en la pila de recursión, no hay necesidad de explorarlo de nuevo
        if (visitados.contains(actual)) return false;

        // Se marca el nodo como visitado completamente (aunque aún no se ha salido de su rama)
        visitados.add(actual);

        // Se añade el nodo al conjunto de recursión para indicar que está en el camino actual
        enRecursion.add(actual);

        // Recorre todos los vecinos del nodo actual
        for (Edge arista : adjList.getOrDefault(actual, new ArrayList<>())) {

            // Llamada recursiva para continuar el DFS desde el vecino
            if (tieneCicloDFS(arista.destino, visitados, enRecursion)) {
                return true; // Si en cualquier rama se detecta un ciclo, se propaga el true
            }
        }

        // Una vez terminada la exploración del nodo, se retira de la pila de recursión
        enRecursion.remove(actual);

        // No se encontró ciclo en esta ruta, se retorna false
        return false;
    }


    // Método que muestra las zonas aisladas del grafo (nodos sin conexiones salientes)
    public void mostrarZonasAisladas() {

        // Conjunto para llevar el registro de los nodos ya explorados
        Set<Nodo> visitados = new HashSet<>();

        // Se recorre cada nodo presente en el grafo
        for (Nodo nodo : adjList.keySet()) {

            // Si el nodo aún no ha sido visitado, se explora su componente conexa
            if (!visitados.contains(nodo)) {

                // Conjunto temporal que almacenará todos los nodos de la componente conexa de 'nodo'
                Set<Nodo> componente = new HashSet<>();

                // Llamada recursiva para llenar el conjunto con todos los nodos alcanzables desde 'nodo'
                dfsComponente(nodo, componente);

                // Se marcan como visitados todos los nodos de esta componente para no volver a analizarlos
                visitados.addAll(componente);

                // Si el nodo no tiene conexiones salientes y está solo en su componente
                if (componente.size() == 1 && adjList.get(nodo).isEmpty()) {
                    System.out.println("Zona aislada: " + nodo.getId());
                }
            }
        }
    }


    // Método auxiliar que realiza una DFS para obtener todos los nodos conectados a partir de 'nodo'
    private void dfsComponente(Nodo nodo, Set<Nodo> componente) {

        // Se añade el nodo actual al conjunto de nodos de la componente
        componente.add(nodo);

        // Se recorre cada arista saliente desde el nodo actual
        for (Edge e : adjList.getOrDefault(nodo, new ArrayList<>())) {

            // Si el nodo destino aún no está en la componente, se sigue explorando recursivamente
            if (!componente.contains(e.destino)) {
                dfsComponente(e.destino, componente);
            }
        }
    }


    // Método público que devuelve el conjunto de nodos del grafo
    public Set<Nodo> getNodos() {
        // Retorna el conjunto de claves del mapa adjList, es decir, todos los nodos del grafo
        return adjList.keySet();
    }

    // Método público que devuelve la lista de aristas salientes desde un nodo dado
    public List<Edge> getAristas(Nodo nodo) {
        // Retorna la lista de aristas asociadas al nodo.
        // Si el nodo no tiene entradas en el mapa, se devuelve una lista vacía.
        return adjList.getOrDefault(nodo, new ArrayList<>());
    }
}
