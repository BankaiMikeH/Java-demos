import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleEventAggregatorTest {
    private final static String PAYLOAD_MSG = "payload";
    private final static String GROUP_NAME = "group";

    @Test
    public void subscribe_WithFakeSubscriber_ReceivesMessages() {
        SimpleEventAggregator messenger = new SimpleEventAggregator();
        FakeSubscriber subscriber = new FakeSubscriber();
        messenger.subscribe(Message.class, subscriber);

        messenger.send(new Message());
        messenger.send(new Message());

        assertThat(subscriber.wasMessageReceived, is(true));
        assertThat(subscriber.timesCalled, is(2));
    }

    @Test
    public void unsubScribe_WithFakeSubscriber_DoesNotReceiveMessage() {
        SimpleEventAggregator messenger = new SimpleEventAggregator();
        FakeSubscriber subscriber = new FakeSubscriber();
        messenger.subscribe(Message.class, subscriber);
        messenger.unSubscribe(Message.class, subscriber);

        messenger.send(new Message());

        assertThat(subscriber.wasMessageReceived, is(false));
    }

    @Test
    public void unsubScribe_WithSpecificGroup_DoesNotReceiveMessage() {
        SimpleEventAggregator messenger = new SimpleEventAggregator();
        FakeSubscriber subscriber = new FakeSubscriber();
        messenger.subscribe(GROUP_NAME, Message.class, subscriber);
        messenger.unSubscribe(GROUP_NAME, Message.class, subscriber);

        messenger.send(GROUP_NAME, new Message());

        assertThat(subscriber.wasMessageReceived, is(false));
    }

    @Test
    public void subscribe_WhenSubscribingToFixedNumberOfMessages_ReceivesFixedNumberOfMessages() {
        SimpleEventAggregator messenger = new SimpleEventAggregator();
        FakeSubscriber subscriber = new FakeSubscriber();
        messenger.subscribe(Message.class, subscriber, 1);

        messenger.send(new Message());
        messenger.send(new Message());

        assertThat(subscriber.wasMessageReceived, is(true));
        assertThat(subscriber.timesCalled, is(1));
        assertThat(subscriber.payload, is(PAYLOAD_MSG));
    }

    @Test
    public void subscribe_WhenSubscribingToGroup_ReceivesMessage() {
        SimpleEventAggregator messenger = new SimpleEventAggregator();
        FakeSubscriber subscriber = new FakeSubscriber();
        messenger.subscribe(GROUP_NAME, Message.class, subscriber);

        messenger.send(GROUP_NAME, new Message());

        assertThat(subscriber.wasMessageReceived, is(true));
    }

    @Test
    public void subscribe_WhenSubscribingToDifferentGroup_DoesNotReceiveMessage() {
        SimpleEventAggregator messenger = new SimpleEventAggregator();
        FakeSubscriber subscriber = new FakeSubscriber();
        messenger.subscribe(GROUP_NAME, Message.class, subscriber, 1);

        messenger.send(new Message());

        assertThat(subscriber.wasMessageReceived, is(false));
    }

    public class FakeSubscriber implements EventSubscriber<Message>
    {
        public boolean wasMessageReceived = false;
        public int timesCalled = 0;
        public String payload;

        @Override
        public void onEvent(Message message) {
            wasMessageReceived = true;
            timesCalled++;
            payload = message.payload;
        }
    }

    public class Message {
        public String payload = PAYLOAD_MSG;
    }
}