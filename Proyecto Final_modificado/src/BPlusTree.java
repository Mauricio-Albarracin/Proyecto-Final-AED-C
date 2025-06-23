import java.util.*;

// Clase principal del árbol B+
public class BPlusTree {
    private final int grado; // Grado del árbol B+
    private Nodo raiz;       // Nodo raíz del árbol

    // Clase interna que representa un nodo del árbol
    private class Nodo {
        boolean esHoja;                 // Indica si el nodo es hoja
        List<Integer> claves;          // Claves almacenadas
        List<Nodo> hijos;              // Hijos si es nodo interno
        List<Producto> productos;      // Productos si es hoja
        Nodo siguiente;                // Apunta al siguiente nodo hoja (en lista enlazada)

        // Constructor
        Nodo(boolean esHoja) {
            this.esHoja = esHoja;
            claves = new ArrayList<>();
            if (esHoja) productos = new ArrayList<>();
            else hijos = new ArrayList<>();
        }
    }

    // Constructor del árbol
    public BPlusTree(int grado) {
        this.grado = grado;
        raiz = new Nodo(true); // Se comienza con una raíz hoja vacía
    }

    // Inserta un producto en el árbol B+
    public void insertar(Producto producto) {
        // Llama a la inserción recursiva sobre la raíz actual
        Nodo nuevaRaiz = insertar(raiz, producto);

        // Si insertar() retorna una nueva raíz (por división), se crea un nuevo nodo raíz
        if (nuevaRaiz != null) {
            // Crear una nueva raíz (nodo interno, no hoja)
            Nodo nueva = new Nodo(false);

            // Agregar la primera clave del nuevo hijo como separador
            nueva.claves.add(nuevaRaiz.claves.get(0));

            // Enlazar los dos hijos: el antiguo raíz y la nueva mitad
            nueva.hijos.add(raiz);
            nueva.hijos.add(nuevaRaiz);

            // Actualizar la raíz del árbol
            raiz = nueva;
        }
    }


    // Inserta recursivamente un producto en el árbol B+, devuelve un nuevo nodo si hubo división
    private Nodo insertar(Nodo nodo, Producto producto) {
        int i = 0;

        // Si el nodo actual es una hoja:
        if (nodo.esHoja) {
            // Encontrar la posición adecuada para insertar la clave del producto
            while (i < nodo.claves.size() && producto.getClave() > nodo.claves.get(i)) i++;

            // Insertar la clave y el producto en la posición encontrada
            nodo.claves.add(i, producto.getClave());
            nodo.productos.add(i, producto);

            // Si se excede la capacidad del nodo hoja, dividirlo
            return nodo.claves.size() > grado ? dividirHoja(nodo) : null;
        } else {
            // Si el nodo actual es interno (no hoja):

            // Determinar el hijo correcto al que se debe descender
            while (i < nodo.claves.size() && producto.getClave() > nodo.claves.get(i)) i++;

            // Insertar recursivamente en el hijo correspondiente
            Nodo nuevoHijo = insertar(nodo.hijos.get(i), producto);

            // Si el hijo fue dividido y devolvió un nuevo nodo
            if (nuevoHijo != null) {
                // Agregar la clave promotora en el nodo actual (nodo padre)
                nodo.claves.add(i, nuevoHijo.claves.get(0));

                // Insertar el nuevo hijo en la posición siguiente
                nodo.hijos.add(i + 1, nuevoHijo);

                // Si el nodo interno se sobrecarga, dividirlo también
                return nodo.claves.size() > grado ? dividirInterno(nodo) : null;
            }

            // Si no hubo división en el hijo, no se necesita hacer nada más
            return null;
        }
    }


    // Divide un nodo hoja y devuelve el nuevo nodo creado
    private Nodo dividirHoja(Nodo nodo) {
        Nodo nuevo = new Nodo(true);
        int medio = (grado + 1) / 2;
        nuevo.claves.addAll(nodo.claves.subList(medio, nodo.claves.size()));
        nuevo.productos.addAll(nodo.productos.subList(medio, nodo.productos.size()));
        nodo.claves.subList(medio, nodo.claves.size()).clear();
        nodo.productos.subList(medio, nodo.productos.size()).clear();
        nuevo.siguiente = nodo.siguiente;
        nodo.siguiente = nuevo;
        return nuevo;
    }

    // Divide un nodo interno y devuelve el nuevo nodo creado
    private Nodo dividirInterno(Nodo nodo) {
        Nodo nuevo = new Nodo(false);
        int medio = (grado + 1) / 2;
        nuevo.claves.addAll(nodo.claves.subList(medio + 1, nodo.claves.size()));
        nuevo.hijos.addAll(nodo.hijos.subList(medio + 1, nodo.hijos.size()));
        nodo.claves.subList(medio, nodo.claves.size()).clear();
        nodo.hijos.subList(medio + 1, nodo.hijos.size()).clear();
        return nuevo;
    }

    // Busca un producto en el árbol a partir de su clave
    public Producto buscar(int clave) {
        // Llama a la búsqueda recursiva iniciando desde la raíz
        return buscar(raiz, clave);
    }


    // Búsqueda recursiva en el árbol B+
    private Producto buscar(Nodo nodo, int clave) {

        // Si el nodo actual es una hoja:
        if (nodo.esHoja) {

            // Recorre las claves almacenadas en el nodo hoja
            for (int i = 0; i < nodo.claves.size(); i++) {

                // Si encuentra la clave buscada, retorna el producto en la misma posición
                if (nodo.claves.get(i) == clave)
                    return nodo.productos.get(i);
            }

            // Si no se encontró la clave en esta hoja, retorna null (producto no encontrado)
            return null;
        } else {
            // Si el nodo no es hoja, es un nodo interno (con hijos):

            int i = 0;

            // Buscar el índice del hijo adecuado para continuar la búsqueda,
            // comparando la clave buscada con las claves internas del nodo
            while (i < nodo.claves.size() && clave > nodo.claves.get(i)) i++;

            // Llamar recursivamente al hijo correspondiente
            return buscar(nodo.hijos.get(i), clave);
        }
    }


    // Devuelve una lista de todos los productos
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        Nodo actual = raiz;
        // Ir al nodo hoja más a la izquierda
        while (!actual.esHoja) actual = actual.hijos.get(0);
        while (actual != null) {
            lista.addAll(actual.productos);
            actual = actual.siguiente;
        }
        return lista;
    }

    // Cuenta el total de elementos del árbol
    public int contarElementos() {
        return listar().size();
    }

    // Imprime todos los productos en orden
    public void mostrar() {
        Nodo actual = raiz;
        while (!actual.esHoja) actual = actual.hijos.get(0);
        while (actual != null) {
            for (Producto p : actual.productos) {
                System.out.println(p);
            }
            actual = actual.siguiente;
        }
    }

    // Elimina un producto del árbol B+ según su clave
    public void eliminar(int clave) {
        // Inicia la eliminación recursiva desde la raíz
        raiz = eliminar(raiz, clave);

        // Ajuste especial si la raíz se queda vacía y tiene hijos (es un nodo interno con solo un hijo)
        if (!raiz.esHoja && raiz.claves.isEmpty() && !raiz.hijos.isEmpty()) {
            raiz = raiz.hijos.get(0); // Promueve el único hijo como nueva raíz
        }
    }


    // Elimina recursivamente la clave desde el nodo actual
    private Nodo eliminar(Nodo nodo, int clave) {
        // Si el nodo es nulo, no hay nada que eliminar
        if (nodo == null) return null;

        // --- CASO 1: Si el nodo es una hoja ---
        if (nodo.esHoja) {
            int i = 0;
            // Buscar la posición donde puede estar la clave a eliminar
            while (i < nodo.claves.size() && nodo.claves.get(i) < clave) i++;
            // Si la clave está en el nodo, eliminar la clave y el producto correspondiente
            if (i < nodo.claves.size() && nodo.claves.get(i) == clave) {
                nodo.claves.remove(i);       // Elimina la clave
                nodo.productos.remove(i);    // Elimina el producto asociado
            }
            // Verificar si el nodo hoja tiene menos claves de las mínimas requeridas
            // Si es así, manejar el subdesbordamiento (redistribución o fusión)
            return nodo.claves.size() < (grado + 1) / 2
                ? manejarSubdesbordamientoHoja(nodo)
                : nodo;
        }
        // --- CASO 2: Si el nodo es interno ---
        else {
            int i = 0;
            // Buscar el hijo adecuado hacia donde descender según la clave
            while (i < nodo.claves.size() && clave > nodo.claves.get(i)) i++;

            // Eliminar recursivamente en el hijo correspondiente
            Nodo hijo = eliminar(nodo.hijos.get(i), clave);

            // Si el hijo fue modificado (por ejemplo, reestructurado tras eliminación)
            if (hijo != null) {
                // Si el índice aún es válido, actualizar la clave separadora del nodo actual
                if (i < nodo.claves.size()) {
                    nodo.claves.set(i, hijo.claves.get(0)); // Actualiza la clave con la primera del nuevo hijo
                }
                // Verificar si este nodo interno tiene menos claves de las mínimas requeridas
                // Si es así, también necesita manejo de subdesbordamiento
                if (nodo.claves.size() < (grado + 1) / 2) {
                    return manejarSubdesbordamientoInterno(nodo);
                }
            }
            // Retorna el nodo actual, modificado o no
            return nodo;
        }
    }

    // Maneja subdesbordamiento en nodo hoja
    private Nodo manejarSubdesbordamientoHoja(Nodo nodo) {
        if (nodo == null) return null;
        if (nodo.claves.size() >= (grado + 1) / 2) return nodo;

        Nodo padre = encontrarPadre(raiz, nodo);
        if (padre == null) return nodo; // Si es raíz, no hay acción

        int idx = padre.hijos.indexOf(nodo);
        Nodo hermanoIzq = idx > 0 ? padre.hijos.get(idx - 1) : null;
        Nodo hermanoDer = idx < padre.hijos.size() - 1 ? padre.hijos.get(idx + 1) : null;

        // Rebalanceo con hermano izquierdo
        if (hermanoIzq != null && hermanoIzq.claves.size() > (grado + 1) / 2) {
            nodo.claves.add(0, hermanoIzq.claves.remove(hermanoIzq.claves.size() - 1));
            nodo.productos.add(0, hermanoIzq.productos.remove(hermanoIzq.productos.size() - 1));
            padre.claves.set(idx - 1, nodo.claves.get(0));
            return nodo;
        }
        // Rebalanceo con hermano derecho
        if (hermanoDer != null && hermanoDer.claves.size() > (grado + 1) / 2) {
            nodo.claves.add(hermanoDer.claves.remove(0));
            nodo.productos.add(hermanoDer.productos.remove(0));
            padre.claves.set(idx, hermanoDer.claves.get(0));
            return nodo;
        }

        // Fusionar con hermano izquierdo
        if (hermanoIzq != null) {
            hermanoIzq.claves.addAll(nodo.claves);
            hermanoIzq.productos.addAll(nodo.productos);
            hermanoIzq.siguiente = nodo.siguiente;
            padre.hijos.remove(idx);
            padre.claves.remove(idx - 1);
            return padre.claves.isEmpty() ? null : padre;
        }

        // Fusionar con hermano derecho
        if (hermanoDer != null) {
            nodo.claves.addAll(hermanoDer.claves);
            nodo.productos.addAll(hermanoDer.productos);
            nodo.siguiente = hermanoDer.siguiente;
            padre.hijos.remove(idx + 1);
            padre.claves.remove(idx);
            return padre.claves.isEmpty() ? null : padre;
        }

        return nodo;
    }

    // Maneja subdesbordamiento en nodo interno
    private Nodo manejarSubdesbordamientoInterno(Nodo nodo) {
        if (nodo == null) return null;
        if (nodo.claves.size() >= (grado + 1) / 2) return nodo;

        Nodo padre = encontrarPadre(raiz, nodo);
        if (padre == null) return nodo;

        int idx = padre.hijos.indexOf(nodo);
        Nodo hermanoIzq = idx > 0 ? padre.hijos.get(idx - 1) : null;
        Nodo hermanoDer = idx < padre.hijos.size() - 1 ? padre.hijos.get(idx + 1) : null;

        // Rebalanceo con hermano izquierdo
        if (hermanoIzq != null && hermanoIzq.claves.size() > (grado + 1) / 2) {
            nodo.claves.add(0, padre.claves.get(idx - 1));
            padre.claves.set(idx - 1, hermanoIzq.claves.remove(hermanoIzq.claves.size() - 1));
            nodo.hijos.add(0, hermanoIzq.hijos.remove(hermanoIzq.hijos.size() - 1));
            return nodo;
        }

        // Rebalanceo con hermano derecho
        if (hermanoDer != null && hermanoDer.claves.size() > (grado + 1) / 2) {
            nodo.claves.add(padre.claves.get(idx));
            padre.claves.set(idx, hermanoDer.claves.remove(0));
            nodo.hijos.add(hermanoDer.hijos.remove(0));
            return nodo;
        }

        // Fusionar con hermano izquierdo
        if (hermanoIzq != null) {
            hermanoIzq.claves.add(padre.claves.remove(idx - 1));
            hermanoIzq.claves.addAll(nodo.claves);
            hermanoIzq.hijos.addAll(nodo.hijos);
            padre.hijos.remove(idx);
            return padre.claves.isEmpty() ? null : padre;
        }

        // Fusionar con hermano derecho
        if (hermanoDer != null) {
            nodo.claves.add(padre.claves.remove(idx));
            nodo.claves.addAll(hermanoDer.claves);
            nodo.hijos.addAll(hermanoDer.hijos);
            padre.hijos.remove(idx + 1);
            return padre.claves.isEmpty() ? null : padre;
        }

        return nodo;
    }

    // Encuentra el padre de un nodo dado (para rebalanceo)
    private Nodo encontrarPadre(Nodo actual, Nodo hijo) {
        if (actual == null || hijo == null) return null;
        if (!actual.esHoja && actual.hijos.contains(hijo)) return actual;

        if (!actual.esHoja) {
            for (Nodo h : actual.hijos) {
                Nodo padre = encontrarPadre(h, hijo);
                if (padre != null) return padre;
            }
        }
        return null;
    }
}
