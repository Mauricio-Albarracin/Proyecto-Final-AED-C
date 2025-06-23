// Clase que representa un nodo dentro del almacén
public class Nodo {
    private String id;                   // Identificador único del nodo
    private String tipoZona;            // Tipo de zona del nodo (ej. "Refrigerado", "Seco", etc.)
    private int capacidad;              // Capacidad máxima de productos que puede almacenar
    private BPlusTree arbolProductos;   // Árbol B+ que almacena los productos en el nodo

    // Constructor del nodo, inicializa el árbol B+ con grado 3 por defecto
    public Nodo(String id, String tipoZona, int capacidad) {
        this.id = id;
        this.tipoZona = tipoZona;
        this.capacidad = capacidad;
        this.arbolProductos = new BPlusTree(3); // Árbol B+ con grado 3
    }

    // Retorna el identificador del nodo
    public String getId() {
        return id;
    }

    // Retorna el tipo de zona
    public String getTipoZona() {
        return tipoZona;
    }

    // Retorna la capacidad máxima del nodo
    public int getCapacidad() {
        return capacidad;
    }

    // Retorna la estructura de datos (árbol B+) que contiene los productos
    public BPlusTree getArbolProductos() {
        return arbolProductos;
    }

    // Intenta agregar un producto al nodo, verificando que no se exceda la capacidad
    public boolean agregarProducto(Producto p) {
        if (arbolProductos.contarElementos() < capacidad) {
            arbolProductos.insertar(p); // Inserta el producto en el árbol B+
            return true;
        } else {
            System.out.println("Capacidad excedida en " + id); // Muestra un mensaje si no hay espacio
            return false;
        }
    }

    // Representación textual del nodo (útil para mostrar en consola)
    @Override
    public String toString() {
        return id + " (" + tipoZona + ", capacidad: " + capacidad + ")";
    }
}
