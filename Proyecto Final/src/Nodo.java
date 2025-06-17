public class Nodo {
    // Atributos del nodo (ubicación del almacén)
    
    private String id;        // Identificador único del nodo, por ejemplo: "Estantería A", "Pasillo 1"
    private String tipoZona;  // Tipo de zona que representa: "Estantería", "Pasillo", "Carga", etc.
    private int capacidad;    // Capacidad de la zona (por ejemplo, cuántos productos puede almacenar)

    // Constructor: inicializa un nodo con su id, tipo y capacidad
    public Nodo(String id, String tipoZona, int capacidad) {
        this.id = id;
        this.tipoZona = tipoZona;
        this.capacidad = capacidad;
    }

    // Métodos de acceso (getters) para obtener los valores de los atributos

    public String getId() {
        return id; // Devuelve el identificador del nodo
    }

    public String getTipoZona() {
        return tipoZona; // Devuelve el tipo de zona del nodo
    }

    public int getCapacidad() {
        return capacidad; // Devuelve la capacidad de la zona
    }

    // Método toString(): se usa para imprimir una representación legible del nodo
    @Override
    public String toString() {
        return id + " (" + tipoZona + ", capacidad: " + capacidad + ")";
        // Ejemplo de salida: "Estantería A (Estantería, capacidad: 50)"
    }
}
