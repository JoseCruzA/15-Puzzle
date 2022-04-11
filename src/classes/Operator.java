/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author josed
 * @param <T>
 */
public interface Operator<T> {
    /**
     * Aplica el operador a un objeto.
     * @param t
     * @return resultado del operador.
     */
    T run(T t);
}
