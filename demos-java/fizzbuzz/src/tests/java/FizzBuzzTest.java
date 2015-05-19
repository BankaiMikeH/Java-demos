import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FizzBuzzTest {

    @Test
    public void process_When1_Returns1() {
        FizzBuzz fizzBuzz = new FizzBuzz();
        String expected = "1";

        String actual = fizzBuzz.process(1);

        assertThat(actual, is(expected));
    }
}