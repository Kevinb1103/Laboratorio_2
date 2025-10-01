public class Experimento3 {

    public static void ejecutarParte1() {
        System.out.println("--- Iniciando Experimento 3, Parte 1: Impacto de la Profundidad ---");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.printf("%-25s | %-20s | %-25s%n", "Límite de Recursión", "Tasa de Éxito (%)", "Tiempo de Ejecución (ms)");
        System.out.println("---------------------------------------------------------------------------------");

        final double probFalloFija = 0.10;
        final int numAlertasFijo = 100;
        final long semillaFija = 42;
        int[] limitesDeRecursion = {5, 10, 15, 50};

        for (int limite : limitesDeRecursion) {
            long startTime = System.currentTimeMillis();
            Alerta.setRandomSeed(semillaFija);
            ColaEnlazada<Alerta> colaDeAlertas = new ColaEnlazada<>();
            int alertasExitosas = 0;
            int alertasFallidas = 0;

            for (int i = 0; i < numAlertasFijo; i++) {
                colaDeAlertas.add(new Alerta("Alerta-Inicial-" + (i + 1), probFalloFija));
            }

            while (!colaDeAlertas.isEmpty()) {
                Alerta alertaActual = colaDeAlertas.poll();
                PilaEnlazada<Alerta> pilaDeLlamadas = new PilaEnlazada<>();
                pilaDeLlamadas.push(alertaActual);
                boolean fueExitoso = alertaActual.procesarLlamada(pilaDeLlamadas, limite);
                if (fueExitoso) {
                    alertasExitosas++;
                } else {
                    alertasFallidas++;
                }
            }

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            double tasaExito = (double) alertasExitosas / numAlertasFijo * 100.0;
            System.out.printf("%-25d | %-20.2f | %-25d%n", limite, tasaExito, duration);
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    public static void ejecutarParte2() {
        System.out.println("\n--- Iniciando Experimento 3, Parte 2: Impacto de la Fiabilidad ---");
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.printf("%-25s | %-20s | %-30s%n", "Probabilidad de Fallo", "Tasa de Éxito (%)", "Profundidad Promedio al Fallar");
        System.out.println("------------------------------------------------------------------------------------------");

        final int limiteRecursionFijo = 20;
        final int numAlertasFijo = 100;
        final long semillaFija = 42;
        double[] probabilidadesDeFallo = {0.01, 0.05, 0.10, 0.30};

        for (double probFallo : probabilidadesDeFallo) {
            Alerta.setRandomSeed(semillaFija);
            ColaEnlazada<Alerta> colaDeAlertas = new ColaEnlazada<>();
            int alertasExitosas = 0;
            int alertasFallidas = 0;
            int sumaProfundidadesFallo = 0;

            for (int i = 0; i < numAlertasFijo; i++) {
                colaDeAlertas.add(new Alerta("Alerta-Inicial-" + (i + 1), probFallo));
            }

            while (!colaDeAlertas.isEmpty()) {
                Alerta alertaActual = colaDeAlertas.poll();
                PilaEnlazada<Alerta> pilaDeLlamadas = new PilaEnlazada<>();
                pilaDeLlamadas.push(alertaActual);
                boolean fueExitoso = alertaActual.procesarLlamada(pilaDeLlamadas, limiteRecursionFijo);
                if (fueExitoso) {
                    alertasExitosas++;
                } else {
                    alertasFallidas++;
                    sumaProfundidadesFallo += pilaDeLlamadas.size();
                }
            }

            double tasaExito = (double) alertasExitosas / numAlertasFijo * 100.0;
            double profundidadPromedio = (alertasFallidas > 0) ? (double) sumaProfundidadesFallo / alertasFallidas : 0;
            System.out.printf("%-25s | %-20.2f | %-30.2f%n", String.format("%.0f%%", probFallo * 100), tasaExito, profundidadPromedio);
        }
        System.out.println("------------------------------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        ejecutarParte1();
        ejecutarParte2();
    }
}