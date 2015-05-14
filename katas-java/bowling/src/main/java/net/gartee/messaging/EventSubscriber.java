package net.gartee.messaging;

public interface EventSubscriber<T> {
    void onEvent(final T message);
}