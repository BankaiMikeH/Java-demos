public class EventSubscription {
    private EventSubscriber subscriber;
    private Integer messageCountUntilAutoUnSubscribe;
    private int messageCount;

    public EventSubscription(EventSubscriber subscriber, int messageCountUntilAutoUnsubscribe) {
        this.subscriber = subscriber;
        this.messageCountUntilAutoUnSubscribe = messageCountUntilAutoUnsubscribe;
    }

    public EventSubscriber getSubscriber() {
        return subscriber;
    }

    public boolean isExpired() {
        return messageCountUntilAutoUnSubscribe > 0
            && messageCount >= messageCountUntilAutoUnSubscribe;
    }

    public void sendMessage(Object message) {
        subscriber.onEvent(message);
        messageCount++;
    }
}
