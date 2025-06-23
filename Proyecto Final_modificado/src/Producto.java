// Clase que representa un producto almacenado en un nodo del almacén
public class Producto {

    // Atributo único que identifica al producto (clave primaria)
    private int clave;

    // Nombre del producto (ej. "Arroz", "Leche", etc.)
    private String nombre;

    // Código del lote al que pertenece el producto
    private String lote;

    // Fecha de caducidad del producto (formato texto, ej. "2025-12-31")
    private String fechaCaducidad;

    // Constructor que inicializa todos los atributos del producto
    public Producto(int clave, String nombre, String lote, String fechaCaducidad) {
        this.clave = clave;
        this.nombre = nombre;
        this.lote = lote;
        this.fechaCaducidad = fechaCaducidad;
    }

    // Getter para obtener la clave del producto
    public int getClave() {
        return clave;
    }

    // Getter para obtener el nombre del producto
    public String getNombre() {
        return nombre;
    }

    // Getter para obtener el código de lote del producto
    public String getLote() {
        return lote;
    }

    // Getter para obtener la fecha de caducidad del producto
    public String getFechaCaducidad(){
        return fechaCaducidad;
    }

    // Método sobrescrito que devuelve una representación en texto del producto
    @Override
    public String toString() {
        return "Producto[" + clave + ", " + nombre + ", Lote: " + lote + ", Vence: " + fechaCaducidad + "]";
    }
}
