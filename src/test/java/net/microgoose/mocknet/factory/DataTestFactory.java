package net.microgoose.mocknet.factory;

public interface DataTestFactory<T> {

    T createNew();

    T createValid();

}
