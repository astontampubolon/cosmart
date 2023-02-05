package com.example.springapp.serviceinterface;

public interface Action<T, V> {

    public T process(V request) throws Exception;
}
