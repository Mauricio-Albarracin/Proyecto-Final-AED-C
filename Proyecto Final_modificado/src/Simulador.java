import java.util.Scanner;

public class Simulador {
    public static void main(String[] args) {
        /*Grafo grafo = new Grafo();

        // Crear nodos
        Nodo zonaCarga = new Nodo("ZonaCarga", "Zona de Carga", 2);
        Nodo pasillo1 = new Nodo("Pasillo1", "Pasillo", 3);
        Nodo estanteA = new Nodo("EstanteA", "Estantería", 5);
        Nodo estanteB = new Nodo("EstanteB", "Estantería", 5);
        Nodo estanteC = new Nodo("EstanteC", "Estantería", 5);
        Nodo pasillo2 = new Nodo("Pasillo2", "Pasillo", 3);
        Nodo zonaDescarga = new Nodo("ZonaDescarga", "Zona de Descarga", 2);

        // Insertar productos
        estanteA.agregarProducto(new Producto(101, "Jabón", "Lote-J", "2025-12-01"));
        estanteA.agregarProducto(new Producto(102, "Alcohol", "Lote-K", "2026-01-15"));
        estanteB.agregarProducto(new Producto(201, "Toallas", "Lote-T", "2025-11-20"));
        estanteC.agregarProducto(new Producto(301, "Detergente", "Lote-D", "2026-06-30"));

        // Agregar nodos al grafo
        grafo.agregarNodo(zonaCarga);
        grafo.agregarNodo(pasillo1);
        grafo.agregarNodo(estanteA);
        grafo.agregarNodo(estanteB);
        grafo.agregarNodo(estanteC);
        grafo.agregarNodo(pasillo2);
        grafo.agregarNodo(zonaDescarga);

        // Agregar aristas
        grafo.agregarArista(zonaCarga, pasillo1, 5);
        grafo.agregarArista(pasillo1, estanteA, 2);
        grafo.agregarArista(pasillo1, estanteB, 2);
        grafo.agregarArista(pasillo1, estanteC, 2);
        grafo.agregarArista(estanteB, pasillo2, 3);
        grafo.agregarArista(pasillo2, zonaDescarga, 4);

        // Mostrar en Swing
        VisualizadorGrafo.mostrar(grafo);*/

        Grafo grafo = new Grafo();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Sistema de Gestión de Almacenes ===");
            System.out.println("1. Agregar ubicación en el almacén");
            System.out.println("2. Crear una conexión (arista)");
            System.out.println("3. Eliminar ubicación");
            System.out.println("4. Eliminar una conexión");
            System.out.println("5. Agregar producto");
            System.out.println("6. Eliminar producto");
            System.out.println("7. Buscar producto");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    agregarUbicacion(grafo, scanner);
                    break;
                case 2:
                    crearConexion(grafo, scanner);
                    break;
                case 3:
                    eliminarUbicacion(grafo, scanner);
                    break;
                case 4:
                    eliminarConexion(grafo, scanner);
                    break;
                case 5:
                    agregarProducto(grafo, scanner);
                    break;
                case 6:
                    eliminarProducto(grafo, scanner);
                    break;
                case 7:
                    buscarProducto(grafo, scanner);
                    break;
                case 8:
                    System.out.println("Saliendo del sistema...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void agregarUbicacion(Grafo grafo, Scanner scanner) {
        System.out.println("\n=== Agregar Ubicación ===");
        System.out.println("Seleccione el tipo de ubicación:");
        System.out.println("1. Zona de Carga");
        System.out.println("2. Zona de Descarga");
        System.out.println("3. Estantería");
        System.out.println("4. Pasillo");
        System.out.print("Opción: ");

        int tipo = Integer.parseInt(scanner.nextLine());
        String tipoZona;
        switch (tipo) {
            case 1:
                tipoZona = "Zona de Carga";
                break;
            case 2:
                tipoZona = "Zona de Descarga";
                break;
            case 3:
                tipoZona = "Estantería";
                break;
            case 4:
                tipoZona = "Pasillo";
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        System.out.print("Ingrese el ID de la ubicación (e.g., ZonaCarga1, EstanteA): ");
        String id = scanner.nextLine();

        System.out.print("Ingrese la capacidad de la ubicación (número entero): ");
        int capacidad = Integer.parseInt(scanner.nextLine());

        Nodo nuevoNodo = new Nodo(id, tipoZona, capacidad);
        grafo.agregarNodo(nuevoNodo);
        System.out.println("Ubicación " + id + " (" + tipoZona + ") agregada con éxito.");
    }

    private static void crearConexion(Grafo grafo, Scanner scanner) {
        System.out.println("\n=== Crear Conexión ===");
        System.out.println("Ubicaciones disponibles:");
        for (Nodo nodo : grafo.getNodos()) {
            System.out.println("- " + nodo.getId() + " (" + nodo.getTipoZona() + ")");
        }

        System.out.print("Ingrese el ID de la ubicación origen: ");
        String idOrigen = scanner.nextLine();
        Nodo origen = null;
        for (Nodo nodo : grafo.getNodos()) {
            if (nodo.getId().equals(idOrigen)) {
                origen = nodo;
                break;
            }
        }

        System.out.print("Ingrese el ID de la ubicación destino: ");
        String idDestino = scanner.nextLine();
        Nodo destino = null;
        for (Nodo nodo : grafo.getNodos()) {
            if (nodo.getId().equals(idDestino)) {
                destino = nodo;
                break;
            }
        }

        if (origen == null || destino == null) {
            System.out.println("Uno de los nodos no existe.");
            return;
        }

        System.out.print("Ingrese el peso de la conexión (distancia en metros): ");
        int peso = Integer.parseInt(scanner.nextLine());

        grafo.agregarArista(origen, destino, peso);
        System.out.println("Conexión de " + idOrigen + " a " + idDestino + " con peso " + peso + " agregada con éxito.");
    }

    private static void eliminarUbicacion(Grafo grafo, Scanner scanner) {
        System.out.println("\n=== Eliminar Ubicación ===");
        System.out.println("Ubicaciones disponibles:");
        for (Nodo nodo : grafo.getNodos()) {
            System.out.println("- " + nodo.getId() + " (" + nodo.getTipoZona() + ")");
        }

        System.out.print("Ingrese el ID de la ubicación a eliminar: ");
        String id = scanner.nextLine();
        Nodo nodo = null;
        for (Nodo n : grafo.getNodos()) {
            if (n.getId().equals(id)) {
                nodo = n;
                break;
            }
        }

        if (nodo == null) {
            System.out.println("La ubicación no existe.");
            return;
        }

        grafo.eliminarNodo(nodo);
        System.out.println("Ubicación " + id + " eliminada con éxito.");
    }

    private static void eliminarConexion(Grafo grafo, Scanner scanner) {
        System.out.println("\n=== Eliminar Conexión ===");
        System.out.println("Ubicaciones disponibles:");
        for (Nodo nodo : grafo.getNodos()) {
            System.out.println("- " + nodo.getId() + " (" + nodo.getTipoZona() + ")");
        }

        System.out.print("Ingrese el ID de la ubicación origen: ");
        String idOrigen = scanner.nextLine();
        Nodo origen = null;
        for (Nodo nodo : grafo.getNodos()) {
            if (nodo.getId().equals(idOrigen)) {
                origen = nodo;
                break;
            }
        }

        System.out.print("Ingrese el ID de la ubicación destino: ");
        String idDestino = scanner.nextLine();
        Nodo destino = null;
        for (Nodo nodo : grafo.getNodos()) {
            if (nodo.getId().equals(idDestino)) {
                destino = nodo;
                break;
            }
        }

        if (origen == null || destino == null) {
            System.out.println("Uno de los nodos no existe.");
            return;
        }

        boolean aristaExiste = false;
        for (Edge arista : grafo.getAristas(origen)) {
            if (arista.destino.equals(destino)) {
                aristaExiste = true;
                break;
            }
        }

        if (!aristaExiste) {
            System.out.println("No existe una conexión de " + idOrigen + " a " + idDestino + ".");
            return;
        }

        grafo.eliminarArista(origen, destino);
        System.out.println("Conexión de " + idOrigen + " a " + idDestino + " eliminada con éxito.");
    }

    private static void agregarProducto(Grafo grafo, Scanner scanner) {
        System.out.println("\n=== Agregar Producto ===");
        System.out.println("Estanterías disponibles:");
        boolean hayEstanterias = false;
        for (Nodo nodo : grafo.getNodos()) {
            if (nodo.getTipoZona().equals("Estantería")) {
                System.out.println("- " + nodo.getId() + " (Capacidad: " + nodo.getCapacidad() + ")");
                hayEstanterias = true;
            }
        }

        if (!hayEstanterias) {
            System.out.println("No hay estanterías en el almacén. Agregue una estantería primero.");
            return;
        }

        System.out.print("Ingrese el ID de la estantería: ");
        String id = scanner.nextLine();
        Nodo estanteria = null;
        for (Nodo nodo : grafo.getNodos()) {
            if (nodo.getId().equals(id) && nodo.getTipoZona().equals("Estantería")) {
                estanteria = nodo;
                break;
            }
        }

        if (estanteria == null) {
            System.out.println("La estantería no existe o no es una estantería.");
            return;
        }

        System.out.print("Ingrese la clave del producto (número entero): ");
        int clave = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el lote del producto: ");
        String lote = scanner.nextLine();
        System.out.print("Ingrese la fecha de caducidad (YYYY-MM-DD): ");
        String fechaCaducidad = scanner.nextLine();

        Producto producto = new Producto(clave, nombre, lote, fechaCaducidad);
        if (estanteria.agregarProducto(producto)) {
            System.out.println("Producto " + nombre + " agregado con éxito a " + id + ".");
        } else {
            System.out.println("No se pudo agregar el producto: capacidad excedida en " + id + ".");
        }
    }

    private static void eliminarProducto(Grafo grafo, Scanner scanner) {
        System.out.println("\n=== Eliminar Producto ===");
        System.out.println("Estanterías disponibles:");
        boolean hayEstanterias = false;
        for (Nodo nodo : grafo.getNodos()) {
            if (nodo.getTipoZona().equals("Estantería")) {
                System.out.println("- " + nodo.getId() + " (Capacidad: " + nodo.getCapacidad() + ")");
                hayEstanterias = true;
            }
        }

        if (!hayEstanterias) {
            System.out.println("No hay estanterías en el almacén. Agregue una estantería primero.");
            return;
        }

        System.out.print("Ingrese el ID de la estantería: ");
        String id = scanner.nextLine();
        Nodo estanteria = null;
        for (Nodo nodo : grafo.getNodos()) {
            if (nodo.getId().equals(id) && nodo.getTipoZona().equals("Estantería")) {
                estanteria = nodo;
                break;
            }
        }

        if (estanteria == null) {
            System.out.println("La estantería no existe o no es una estantería.");
            return;
        }

        System.out.print("Ingrese la clave del producto a eliminar: ");
        int clave = Integer.parseInt(scanner.nextLine());

        Producto producto = estanteria.getArbolProductos().buscar(clave);
        if (producto == null) {
            System.out.println("No se encontró un producto con la clave " + clave + " en " + id + ".");
            return;
        }

        estanteria.getArbolProductos().eliminar(clave);
        System.out.println("Producto con clave " + clave + " eliminado con éxito de " + id + ".");
    }

    private static void buscarProducto(Grafo grafo, Scanner scanner) {
        System.out.println("\n=== Buscar Producto ===");
        System.out.println("Estanterías disponibles:");
        boolean hayEstanterias = false;
        for (Nodo nodo : grafo.getNodos()) {
            if (nodo.getTipoZona().equals("Estantería")) {
                System.out.println("- " + nodo.getId() + " (Capacidad: " + nodo.getCapacidad() + ")");
                hayEstanterias = true;
            }
        }

        if (!hayEstanterias) {
            System.out.println("No hay estanterías en el almacén. Agregue una estantería primero.");
            return;
        }

        System.out.print("Ingrese el ID de la estantería: ");
        String id = scanner.nextLine();
        Nodo estanteria = null;
        for (Nodo nodo : grafo.getNodos()) {
            if (nodo.getId().equals(id) && nodo.getTipoZona().equals("Estantería")) {
                estanteria = nodo;
                break;
            }
        }

        if (estanteria == null) {
            System.out.println("La estantería no existe o no es una estantería.");
            return;
        }

        System.out.print("Ingrese la clave del producto a buscar: ");
        int clave = Integer.parseInt(scanner.nextLine());

        Producto producto = estanteria.getArbolProductos().buscar(clave);
        if (producto == null) {
            System.out.println("No se encontró un producto con la clave " + clave + " en " + id + ".");
        } else {
            System.out.println("Producto encontrado en " + id + ": " + producto);
        }
    }
}
