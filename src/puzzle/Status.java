/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puzzle;

import classes.Operator;
import java.util.*;

/**
 *
 * @author josed
 */
public class Status implements Comparable<Status> {

    private static final int N = 16;
    public int pos_hole;
    public int tiles[] = new int[N];

    public Status parent;
    public int depth;

    /**
     * Construye un estado a partir de un array de fichas.
     *
     * @param f Lista de las 16 fichas (15 y el hueco) del puzzle.
     */
    public Status(int... f) {
        for (int i = 0; i < f.length; i++) {
            tiles[i] = f[i];
            if (f[i] == 0) {
                pos_hole = i;
            }
        }
        init(null);
    }

    private Status(Status p) {
        pos_hole = p.pos_hole;
        System.arraycopy(p.tiles, 0, tiles, 0, N);
        init(p);
    }

    public static final Operator<Status> up = new Operator<Status>() {
        @Override
        public Status run(Status t) {
            if (t.pos_hole < 4) {
                return null;
            }
            Status res = new Status(t);
            res.interchange(-4);
            return res;
        }
    };
    public static final Operator<Status> down = new Operator<Status>() {
        @Override
        public Status run(Status t) {
            if (t.pos_hole >= N - 4) {
                return null;
            }
            Status res = new Status(t);
            res.interchange(+4);
            return res;
        }
    };
    public static final Operator<Status> left = new Operator<Status>() {
        @Override
        public Status run(Status t) {
            if (t.pos_hole % 4 == 0) {
                return null;
            }
            Status res = new Status(t);
            res.interchange(-1);
            return res;
        }
    };
    public static final Operator<Status> right = new Operator<Status>() {
        /* RIGHT MOVEMENT */
        @Override
        public Status run(Status t) {
            if (t.pos_hole % 4 == 3) {
                return null;
            }
            Status res = new Status(t);
            res.interchange(+1);
            return res;
        }
    };

    @SuppressWarnings("unchecked")
    public static final Operator<Status>[] moves = new Operator[]{
        up, down, left, right,};

    private void init(Status p) {
        parent = p;
        depth = (p != null) ? p.depth + 1 : 0;
    }

    private void interchange(int desplazamiento) {
        int new_hole = pos_hole + desplazamiento;
        int x = tiles[pos_hole];
        tiles[pos_hole] = tiles[new_hole];
        tiles[new_hole] = x;
        pos_hole = new_hole;
    }

    /**
     * Devuelve el estado padre.
     *
     * @return estado padre
     */
    public Status getParent() {
        return parent;
    }

    /**
     * Devuelve la profundidad del estado.
     *
     * @return profundidad
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Calcula el código hash.
     *
     * @return Código hash.
     */
    @Override
    public int hashCode() {
        int acum = 0;

        for (int i = 0; i < N; i++) {
            acum *= 687517517;
            acum += tiles[i];
            acum %= 999298081;
        }
        return acum;
    }

    /**
     * Compara un estado con otro objeto.
     *
     * @param obj Objeto a comparar.
     * @return {@code true} si el estado es igual al objeto.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Status) {
            return compareTo((Status) obj) == 0;
        }
        return false;
    }

    /**
     * Compara un estado con otro.
     * <p>
     * Necesaria para utilizar la cola de prioridad.
     *
     * @param e estado
     * @return un número negativo, cero, o positivo si este estado es menor,
     * igual, o mayour que el estado indicado.
     */
    @Override
    public int compareTo(Status e) {
        for (int i = 0; i < N; i++) {
            if (tiles[i] != e.tiles[i]) {
                return tiles[i] - e.tiles[i];
            }
        }
        return 0;
    }

    /**
     * Comprueba si el estado es el objetivo.
     *
     * @return {@code true} si el estado es el objetivo.
     */
    public boolean esObjetivo() {
        int[] objetivo = Heuristic.objetivo.stream().mapToInt(i -> i).toArray();
        return Arrays.equals(tiles, objetivo);
    }

    @Override
    public String toString() {
        StringBuffer b = new StringBuffer();
        b.append("[" + hashCode() + "]:");
        for (int i = 0; i < N; i++) {
            b.append(" " + tiles[i]);
        }
        return b.toString();
    }

    public String solution() {
        StringBuffer b = new StringBuffer();
        if (parent != null) {
            b.append(parent.solution());
            b.append("=========== [" + depth + "]");
        }
        for (int i = 0; i < N; i++) {
            b.append(i % 4 == 0 ? "\n" : " ");
            if (tiles[i] == 0) {
                b.append("  ");
            } else {
                b.append(String.format("%02d", tiles[i]));
            }
        }
        b.append("\n");
        return b.toString();
    }
}
