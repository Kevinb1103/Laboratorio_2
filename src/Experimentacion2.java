import java.util.LinkedList;
import java.util.Queue;

public class Experimentacion2 {

    public static void main(String[] args) {
        System.out.println("--- Iniciando Experimento 2 con el nuevo código ---");

        // Parámetros fijos según la rúbrica
        final double umbralFallaFijo = 0.1;
        final long semillaFija = 42;
        Alerta.setRandomSeed(semillaFija);

        // Encabezado de la tabla de resultados
        System.out.println("---------------------------------------------------------------------------------");
        System.out.printf("%-20s | %-25s | %-25s%n", "N° de Alertas (n)", "Tiempo ColaEnlazada (ns)", "Tiempo Java Queue (ns)");
        System.out.println("---------------------------------------------------------------------------------");

        // Bucle para variar n de 10 a 100
        for (int i = 1; i <= 10; i++) {
            int n = 10 * i;
            int elementosAExtraer = n / 2;

            // --- Prueba para tu implementación: ColaEnlazada ---
            ColaEnlazada<Alerta> miCola = new ColaEnlazada<>();
            long startTimeColaEnlazada = System.nanoTime();

            for (int j = 0; j < n; j++) {
                miCola.add(new Alerta("Alerta-" + (j + 1), umbralFallaFijo));
            }
            for (int j = 0; j < elementosAExtraer; j++) {
                miCola.poll();
            }

            long endTimeColaEnlazada = System.nanoTime();
            long durationColaEnlazada = endTimeColaEnlazada - startTimeColaEnlazada;


            // --- Prueba para la implementación de Java: Queue (usando LinkedList) ---
            Queue<Alerta> javaQueue = new LinkedList<>();
            long startTimeJavaQueue = System.nanoTime();

            for (int j = 0; j < n; j++) {
                javaQueue.add(new Alerta("Alerta-" + (j + 1), umbralFallaFijo));
            }
            for (int j = 0; j < elementosAExtraer; j++) {
                javaQueue.poll();
            }

            long endTimeJavaQueue = System.nanoTime();
            long durationJavaQueue = endTimeJavaQueue - startTimeJavaQueue;

            // Imprimir los resultados de la iteración
            System.out.printf("%-20d | %-25d | %-25d%n", n, durationColaEnlazada, durationJavaQueue);
        }
        System.out.println("---------------------------------------------------------------------------------");
    }
}