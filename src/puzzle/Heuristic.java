/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puzzle;

import java.util.*;

/**
 *
 * @author josed
 */
public class Heuristic implements Comparator<Status> {

    public static final List<Integer> objetivo = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0);

    /**
     * Compara dos estados según su heurística.
     *
     * @param a Primer estado a comparar.
     * @param b Segundo estado a comparar.
     * @return Un número negativo, cero, o positivo si el primer estado tiene
     * una heurística menor, igual o mayor que el segundo.
     */
    @Override
    public int compare(Status a, Status b) {
        int heuristicA = get(a);
        int heuristicB = get(b);

        if (heuristicA < heuristicB) {
            return -1;
        } else if (heuristicA == heuristicB) {
            return 0;
        } else {
            return +1;
        }
    }

//------------------------------------------------------------------------
    public int get(Status e) {
        return getG(e) + getH(e);
    }

    /**
     * Calcula el coste hasta el estado indicado.
     *
     * @param e estado
     * @return coste hasa el estado indicado
     */
    public int getG(Status e) {
        return e.getDepth();
    }

    /**
     * Calcula el coste estimado desde el estado indicado hasta un objetivo.
     *
     * @param e estado
     * @return coste estimado desde el estado indicado hasta un objetivo
     */
    public int getH(Status e) {
        int res = 0;
        int indexNumberObjetivo = 0;
        for (int i = 0; i < 16; i++) {
            indexNumberObjetivo = objetivo.indexOf(e.tiles[i]);
            if (i != indexNumberObjetivo) {
                //columna en la que esta - columna en la que deberia estar
                int dx = Math.abs((i % 4) - (indexNumberObjetivo % 4));
                //fila en la que esta - fila en la que deberia estar
                int dy = Math.abs((i / 4) - (indexNumberObjetivo / 4));
                res += dx + dy;
            }
        }
        return res;
    }
}
