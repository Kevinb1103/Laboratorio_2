import java.util.*;
import java.time.Duration;
import java.time.Instant;

public class Experimentacion {

    // --- Experimento 1: Pila ---
    public static void experimentoPila() {
        System.out.println("=== Experimento 1: Pila ===");
        double probFallo = 0.1;
        long semilla = 42;

        for (int i = 1; i <= 10; i++) {
            int n = 10 * i;

            // --- Con PilaEnlazada ---
            PilaEnlazada<Alerta> pila = new PilaEnlazada<>();
            Instant inicio = Instant.now();

            for (int j = 0; j < n; j++) {
                pila.push(new Alerta("A" + j, probFallo));
            }
            for (int j = 0; j < n / 2; j++) {
                pila.pop();
            }

            Instant fin = Instant.now();
            long duracionCustom = Duration.between(inicio, fin).toMillis();

            // --- Con Stack de Java ---
            Stack<Alerta> stackJava = new Stack<>();
            inicio = Instant.now();

            for (int j = 0; j < n; j++) {
                stackJava.push(new Alerta("A" + j, probFallo));
            }
            for (int j = 0; j < n / 2; j++) {
                stackJava.pop();
            }

            fin = Instant.now();
            long duracionJava = Duration.between(inicio, fin).toMillis();

            System.out.printf("n=%d | PilaEnlazada=%d ms | Stack=%d ms%n", n, duracionCustom, duracionJava);
        }
    }

    // --- Experimento 2: Cola ---
    public static void experimentoCola() {
        System.out.println("\n=== Experimento 2: Cola ===");
        double probFallo = 0.1;
        long semilla = 42;

        for (int i = 1; i <= 10; i++) {
            int n = 10 * i;

            // --- Con ColaEnlazada ---
            ColaEnlazada<Alerta> cola = new ColaEnlazada<>();
            Instant inicio = Instant.now();

            for (int j = 0; j < n; j++) {
                cola.enqueue(new Alerta("A" + j, probFallo));
            }
            for (int j = 0; j < n / 2; j++) {
                cola.dequeue();
            }

            Instant fin = Instant.now();
            long duracionCustom = Duration.between(inicio, fin).toMillis();

            // --- Con Queue de Java ---
            Queue<Alerta> colaJava = new LinkedList<>();
            inicio = Instant.now();

            for (int j = 0; j < n; j++) {
                colaJava.add(new Alerta("A" + j, probFallo));
            }
            for (int j = 0; j < n / 2; j++) {
                colaJava.poll();
            }

            fin = Instant.now();
            long duracionJava = Duration.between(inicio, fin).toMillis();

            System.out.printf("n=%d | ColaEnlazada=%d ms | Queue=%d ms%n", n, duracionCustom, duracionJava);
        }
    }

    // --- Experimento 3: Simulación ---
    public static void experimentoSimulacion() {
        System.out.println("\n=== Experimento 3: Simulación ===");

        // Parte 1: Impacto de la profundidad
        double probFallo = 0.10;
        int[] limites = {5, 10, 15, 50};
        for (int limite : limites) {
            Main.limiteRecursion = limite;
            Main.probFallo = probFallo;
            Main.generarAlertas(20);
            Instant inicio = Instant.now();
            Main.iniciarSimulacion();
            Instant fin = Instant.now();
            System.out.printf("Limite=%d | Tiempo=%d ms%n", limite, Duration.between(inicio, fin).toMillis());
        }

        // Parte 2: Impacto de la fiabilidad
        int limiteRec = 20;
        double[] probs = {0.01, 0.05, 0.10, 0.30};
        for (double p : probs) {
            Main.limiteRecursion = limiteRec;
            Main.probFallo = p;
            Main.generarAlertas(20);
            Instant inicio = Instant.now();
            Main.iniciarSimulacion();
            Instant fin = Instant.now();
            System.out.printf("ProbFallo=%.2f | Tiempo=%d ms%n", p, Duration.between(inicio, fin).toMillis());
        }
    }

    public static void main(String[] args) {
        experimentoPila();          // corre experimento 1
        experimentoCola();          // corre experimento 2
        experimentoSimulacion();    // corre experimento 3
    }
}