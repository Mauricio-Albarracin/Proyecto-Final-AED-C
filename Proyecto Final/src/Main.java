public class Main {
    public static void main(String[] args) {
        Graph almacen = new Graph();

        Nodo estA = new Nodo("Estantería A", "Estantería", 50);
        Nodo pasillo1 = new Nodo("Pasillo 1", "Pasillo", 100);
        Nodo carga = new Nodo("Zona Carga", "Carga", 80);

        almacen.agregarNodo(estA);
        almacen.agregarNodo(pasillo1);
        almacen.agregarNodo(carga);

        almacen.agregarArista(estA, pasillo1, 5);
        almacen.agregarArista(pasillo1, carga, 10);
        almacen.agregarArista(carga, estA, 15);

        almacen.modificarArista(pasillo1, carga, 8);
        almacen.eliminarNodo(carga);

        almacen.mostrarGrafo();
    }
}
