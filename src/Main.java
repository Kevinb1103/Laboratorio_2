import java.util.Random;

// ====================== Nodo ======================
class Nodo<T> {
    private T valor;
    private Nodo<T> siguiente;

    public Nodo(T valor) {
        this.valor = valor;
        this.siguiente = null;
    }

    public Nodo(T valor, Nodo<T> siguiente) {
        this.valor = valor;
        this.siguiente = siguiente;
    }

    // getters y setters
    public T getValor() {
        return valor;
    }
    public void setValor(T valor) {
        this.valor = valor;
    }
    public Nodo<T> getSiguiente() {
        return siguiente;
    }
    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}

// ====================== PilaEnlazada ======================
class PilaEnlazada<T> {
    private Nodo<T> tope;
    private int size;

    public PilaEnlazada() {
        this.tope = null;
        this.size = 0;
    }

    // Agrega un elemento al tope de la pila
    public void push(T valor) {
        Nodo<T> nuevoNodo = new Nodo<>(valor, this.tope);
        this.tope = nuevoNodo;
        this.size++;
    }

    // Elimina y devuelve el elemento del tope
    public T pop() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("La Pila está vacía. No se puede hacer pop");
        }
        T valor = this.tope.getValor();
        this.tope = this.tope.getSiguiente();
        this.size--;
        return valor;
    }

    // Devuelve el elemento del tope sin eliminarlo
    public T peek() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("La Pila está vacía. No se puede hacer peek.");
        }
        return this.tope.getValor();
    }

    // Devuelve true si la pila no tiene elementos
    public boolean isEmpty() {
        return this.size == 0;
    }

    // Devuelve el tamaño actual de la pila
    public int size() {
        return this.size;
    }

    // Vacía la pila
    public void vaciar() {
        this.tope = null;
        this.size = 0;
    }

    public String toString() {
        if (isEmpty()) {
            return "[] (Pila vacía)";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[Tope] -> ");
        Nodo<T> actual = this.tope;
        while (actual != null) {
            sb.append(actual.getValor().toString());
            if (actual.getSiguiente() != null) {
                sb.append(" -> ");
            }
            actual = actual.getSiguiente();
        }
        sb.append(" -> [Base] ");
        return sb.toString();
    }
}

// ====================== ColaEnlazada ======================
class ColaEnlazada<T> {
    private Nodo<T> inicio;
    private Nodo<T> fin;

    public ColaEnlazada() {
        this.inicio = null;
        this.fin = null;
    }

    // Agrega un elemento al final de la cola y retorna true si se agregó bien
    public boolean add(T valor) {
        Nodo<T> nuevoNodo = new Nodo<>(valor);
        if (isEmpty()) {
            this.inicio = nuevoNodo;
            this.fin = nuevoNodo;
        } else {
            this.fin.setSiguiente(nuevoNodo);
            this.fin = nuevoNodo;
        }
        return true;
    }

    public boolean enqueue(T valor) {
        return add(valor);
    }

    // Elimina y devuelve el primer elemento de la cola
    public T poll() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("La cola está vacía. No se puede hacer poll");
        }
        T valor = this.inicio.getValor();
        this.inicio = this.inicio.getSiguiente();
        if (this.inicio == null) {
            this.fin = null;
        }
        return valor;
    }

    public T dequeue() {
        return poll();
    }

    // Devuelve el elemento de enfrente sin eliminarlo
    public T peek() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("La Cola está vacía. No se puede hacer peek");
        }
        return this.inicio.getValor();
    }

    // Devuelve true si la cola está vacía
    public boolean isEmpty() {
        return this.inicio == null;
    }

    public String toString() {
        if (isEmpty()) {
            return "[] (Cola vacía)";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[Inicio] -> ");
        Nodo<T> actual = this.inicio;
        while (actual != null) {
            sb.append(actual.getValor().toString());
            if (actual.getSiguiente() != null) {
                sb.append(" -> ");
            }
            actual = actual.getSiguiente();
        }
        sb.append(" -> [Fin]");
        return sb.toString();
    }
}

// ====================== Alerta ======================
class Alerta {
    private String nombre;
    private double probFallo;
    private static Random random;
    private static int contadorAlertaSecundaria = 0;

    public Alerta(String name, double probFallo) {
        this.nombre = name;
        this.probFallo = probFallo;
    }

    // Método para inicializar el generador random
    public static void setRandomSeed(long semilla) {
        random = new Random(semilla);
    }

    // Método auxiliar para crear nueva alerta
    public static Alerta crearAlerta(String nombre, double probFallo) {
        return new Alerta(nombre, probFallo);
    }

    // Método auxiliar para crear el nombre de la alerta secundaria
    private static String generarNombreAlertaSecundaria(int nivel) {
        contadorAlertaSecundaria++;
        return "Alerta-Secundaria-" + nivel + "(" + contadorAlertaSecundaria + ")";
    }

    public boolean procesarLlamada(PilaEnlazada<Alerta> pila, int limite) {
        if (pila.size() >= limite) {
            return true;
        }

        double numeroAleatorio = random.nextDouble();
        if (numeroAleatorio < probFallo) {
            return false;
        }

        String idNuevaAlerta = generarNombreAlertaSecundaria(pila.size() + 1);
        Alerta alertaSecundaria = crearAlerta(idNuevaAlerta, this.probFallo);
        // Se agrega la nueva alerta a la pila
        pila.push(alertaSecundaria);
        return alertaSecundaria.procesarLlamada(pila, limite);
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

// ====================== Main ======================
public class Main {
    static long semilla = 42L; // Semilla para simulación
    static double probFallo = 0.10; // Probabilidad base de fallo
    static int numAlertas = 10; // Cantidad de alertas a generar
    static int limiteRecursion = 15; // Máximo de llamadas recursivas

    // Cola para almacenar alertas
    private static ColaEnlazada<Alerta> colaDeAlertas = new ColaEnlazada<>();

    // Contenedores de alertas fallidas y exitosas
    private static int alertasExitosas = 0;
    private static int alertasFallidas = 0;

    public static void generarAlertas(int numAlertas) {
        System.out.println("-> Generando " + numAlertas + " alertas iniciales...");
        colaDeAlertas = new ColaEnlazada<>();

        for (int i = 0; i < numAlertas; i++) {
            Alerta nuevaAlerta = new Alerta("Alerta-Inicial-" + (i + 1), probFallo);
            colaDeAlertas.enqueue(nuevaAlerta); // Agrega al final de la cola
        }
        System.out.println(" -> Generación completada. Cola: " + colaDeAlertas.toString());
    }

    public static void iniciarSimulacion() {
        System.out.println("\n--- INICIANDO SIMULACIÓN ---");
        System.out.println("Parámetros: Seed=" + semilla + ", Prob. Fallo=" + probFallo + ", Límite Rec.=" + limiteRecursion);

        // Inicializa el generador aleatorio
        Alerta.setRandomSeed(semilla);

        // Inicializa contadores
        alertasFallidas = 0;
        alertasExitosas = 0;

        while (!colaDeAlertas.isEmpty()) {
            // Extraer la alerta actual de la cola (FIFO)
            Alerta alertaActual = colaDeAlertas.dequeue();

            // Imprimir el progreso
            System.out.println("\n------------------------------------------------------------");
            System.out.println("Iniciando cascada para " + alertaActual.getNombre());

            // Crear una nueva pila para esta cascada
            PilaEnlazada<Alerta> pilaDeLlamadas = new PilaEnlazada<>();

            // La primera alerta de la cascada se apila antes de iniciar
            pilaDeLlamadas.push(alertaActual);

            // Iniciar la llamada recursiva
            boolean fueExitoso = alertaActual.procesarLlamada(pilaDeLlamadas, limiteRecursion);

            // Resultado
            if (fueExitoso) {
                System.out.println("ÉXITO: La cascada de análisis se completó (Profundidad " + limiteRecursion + ").");
                alertasExitosas++;
            } else {
                System.out.println("FALLO: La cascada de análisis se interrumpió.");
                System.out.println("Stack Trace: (Tope es el punto de fallo)");
                System.out.println(pilaDeLlamadas.toString()); // Estado de la pila al fallar
                alertasFallidas++;
            }

            // Limpiar la pila
            pilaDeLlamadas.vaciar();
            System.out.println("------------------------------------------------------------");
        }

        System.out.println("\n--- SIMULACIÓN FINALIZADA ---");
        System.out.println("Total de alertas procesadas: " + (alertasExitosas + alertasFallidas));
        System.out.println("Éxitos: " + alertasExitosas + ", Fallos: " + alertasFallidas);
        double tasaExito = (alertasExitosas / (double)(alertasExitosas + alertasFallidas)) * 100;
        System.out.printf("Tasa de Éxito: %.2f%%%n", tasaExito);
    }

    public static void main(String[] args) {
        // Ejecución inicial de la simulación
        generarAlertas(numAlertas);
        iniciarSimulacion();
    }
}

