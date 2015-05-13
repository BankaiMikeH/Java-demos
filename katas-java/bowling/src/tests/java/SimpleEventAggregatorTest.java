import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleEventAggregatorTest {
    @Test
    public void subscribe_WithFakeSubscriber_ReceivesMessages() {
        SimpleEventAggregator messenger = new SimpleEventAggregator();
        Subscriber subscriber = new Subscriber();
        messenger.subscribe(Message.class, subscriber);

        messenger.send(new Message());
        messenger.send(new Message());

        assertThat(subscriber.wasMessageReceived, is(true));
        assertThat(subscriber.timesCalled, is(2));
    }

    @Test
    public void unsubScribe_WithFakeSubscriber_DoesNotReceiveMessage() {
        SimpleEventAggregator messenger = new SimpleEventAggregator();
        Subscriber subscriber = new Subscriber();
        messenger.subscribe(Message.class, subscriber);
        messenger.unSubscribe(Message.class, subscriber);

        messenger.send(new Message());

        assertThat(subscriber.wasMessageReceived, is(false));
    }

    @Test
    public void unsubScribe_WithSpecificGroup_DoesNotReceiveMessage() {
        SimpleEventAggregator messenger = new SimpleEventAggregator();
        Subscriber subscriber = new Subscriber();
        messenger.subscribe("testGroup", Message.class, subscriber);
        messenger.unSubscribe("testGroup", Message.class, subscriber);

        messenger.send("testGroup", new Message());

        assertThat(subscriber.wasMessageReceived, is(false));
    }

    @Test
    public void subscribe_WhenSubscribingToFixedNumberOfMessages_ReceivesFixedNumberOfMessages() {
        SimpleEventAggregator messenger = new SimpleEventAggregator();
        Subscriber subscriber = new Subscriber();
        messenger.subscribe(Message.class, subscriber, 1);

        messenger.send(new Message());
        messenger.send(new Message());

        assertThat(subscriber.wasMessageReceived, is(true));
        assertThat(subscriber.timesCalled, is(1));
    }

    @Test
    public void subscribe_WhenSubscribingToGroup_ReceivesMessage() {
        SimpleEventAggregator messenger = new SimpleEventAggregator();
        Subscriber subscriber = new Subscriber();
        messenger.subscribe("testGroup", Message.class, subscriber);

        messenger.send("testGroup", new Message());

        assertThat(subscriber.wasMessageReceived, is(true));
    }

    @Test
    public void subscribe_WhenSubscribingToDifferentGroup_DoesNotReceiveMessage() {
        SimpleEventAggregator messenger = new SimpleEventAggregator();
        Subscriber subscriber = new Subscriber();
        messenger.subscribe("test", Message.class, subscriber, 1);

        messenger.send(new Message());

        assertThat(subscriber.wasMessageReceived, is(false));
    }

    public class Subscriber implements EventSubscriber<Message>
    {
        public boolean wasMessageReceived = false;
        public int timesCalled = 0;

        @Override
        public void onEvent(Message message) {
            wasMessageReceived = true;
            timesCalled++;
        }
    }

    public class Message {
        public String payload = "hello";
    }
}