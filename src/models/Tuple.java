package models;

public class Tuple <K, T>{
    public K key;
    public T value;

    public Tuple(K key, T value) {
        this.key = key;
        this.value = value;
    }
}
