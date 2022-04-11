/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package puzzle;

import classes.Operator;
import classes.QueuePriority;
import classes.SetNodes;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author josed
 */
public class Puzzle {

    private static long milisInicio;
    private static int exploredNodes;
    public static final Heuristic manhattan = new Heuristic();

    private static Status heuristicSearchManhattan(Status inicial) {
        SetNodes<Status> visited = new SetNodes<>();
        QueuePriority<Status> frontier = new QueuePriority<>(
                (Status arg0, Status arg1) -> manhattan.compare(arg0, arg1));
        frontier.add(inicial);
        visited.add(inicial);
        try {
            while (!frontier.isEmpty()) {
                Status e = frontier.poll();
                exploredNodes++;
        
                if (e.esObjetivo()) {
                    System.out.print("Solución\n" + e.solution());
                    return e;
                }
                for (Operator<Status> op : Status.moves) {
                    Status n = op.run(e);
                    if ((n != null) && (visited.get(n) == null)) {
                        frontier.add(n);
                        visited.add(n);
                    }
                }
            }
        } catch (OutOfMemoryError e) {
        }
        return null;
    }

    private static void printSolution(Status e) {
        long milisTotal = System.currentTimeMillis() - milisInicio;
        System.out.println("\nBusqueda heurística Manhattan");

        if (e == null) {
            System.out.println("No se ha encontrado la solución.");
        }

        System.out.println("Nodos explorados: " + exploredNodes);
        System.out.println("Milisegundos: " + milisTotal);
    }

    public static void main(String args[]) {
        int option = Integer.parseInt(JOptionPane.showInputDialog("Seleccione el modo de uso 1.Manual 2.Aleatorio"));

        Status initial = null;
        int[] iEst = new int[16];

        switch (option) {
            case 1:
                String sEst = JOptionPane.showInputDialog("Ingrese el estado separado por comas").replace(" ", "");
                int i = 0;
                System.out.println(sEst);
                for (String c : sEst.split(",")) {
                    try {
                        iEst[i] = Integer.parseInt(c);
                        i++;
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Ingrese una lista con valores enteros");
                    }
                }
                initial = new Status(iEst);
                break;
            case 2:
                Integer[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
                List<Integer> list = Arrays.asList(numbers);
                List<Integer> randomPicks = pickNRandom(list, 16);
                int j = 0;
                for (Integer num : randomPicks) {
                    iEst[j] = num;
                    j++;
                }
                System.out.println("Random List: " + randomPicks.toString());
                initial = new Status(iEst);
                break;


        }

        Status result = null;

        milisInicio = System.currentTimeMillis();

        result = heuristicSearchManhattan(initial);
        printSolution(result);
    }

    public static List<Integer> pickNRandom(List<Integer> lst, int n) {
        List<Integer> copy = new ArrayList<>(lst);
        Collections.shuffle(copy);
        return n > copy.size() ? copy.subList(0, copy.size()) : copy.subList(0, n);
    }

}
