/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.util.*;

/**
 *
 * @author josed
 * @param <T>
 */
public class SetNodes<T> implements Iterable<T> {

    private final HashMap<T, T> map = new HashMap<>();

    public void add(T e) {
        if (map.put(e, e) != null) {
            throw new AssertionError();
        }
    }

    public T get(T e) {
        return map.get(e);
    }

    public void remove(T e) {
        if (map.remove(e) != e) {
            throw new AssertionError();
        }
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public int size() {
        return map.size();
    }

    @Override
    public Iterator<T> iterator() {
        return map.keySet().iterator();
    }
}
