package net.microgoose.mocknet.factory;

public interface ModelTestFactory<T> extends DataTestFactory<T> {

    T createPersisted();

}
