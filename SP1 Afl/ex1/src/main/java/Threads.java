import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * <h2>Task 1</h2>
 * <p>
 * Write three methods:
 * <ol>
 * <li>Compute and print the sum of all numbers from 1 to 1 billion.</li>
 * <li>
 * Print the numbers from 1 to 5. Pause for 2 seconds between each print.
 * </li>
 * <li>
 * Print all numbers from 10 and up to {@link Integer#MAX_VALUE}. Pause for 3
 * seconds between each print.
 * </li>
 * </ol>
 * Create three threads that run each of the above methods. Start them all
 * simultaneously from your main method. Stop / kill the thread running the
 * third method after waiting 10 seconds.
 * </p>
 */
public class Threads {

    /**
     * Starts three threads that execute three methods simultaneously.
     *
     * @param args Input arguments to the main method. Unused.
     */
    public static void main(String[] args) 
    {
        
        Thread t1 = new Thread(() -> {print1To5();} );
        Thread t2 = new Thread(() -> {sumToBillion();} );
        Thread t3 = new Thread(() -> {print10ToMax();} );
        Thread t4 = new Thread(() -> 
        {
            try 
            {
                Thread.sleep(10000);
                t3.stop();
            } catch (InterruptedException ex) 
            {}
        });
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
    
    public static void sumToBillion()
    {
        long sum = 0;
        for (long i = 0; i < 1000000000; i++) 
        {
            sum += i;
        }
        System.out.println("Sum to 1 billion: " + sum);
    }

    public static void print1To5()
    {
        for (int i = 1; i <= 5; i++) 
        {
            System.out.println("1-5: " + i);
            try 
            {
                Thread.sleep(2000);
            } 
            catch (InterruptedException ex) 
            {}
        }
    }
    
    public static void print10ToMax()
    {
        for (int i = 10; i <= Integer.MAX_VALUE; i++) 
        {
            try 
            {
                System.out.println(i);
                Thread.sleep(3000);
            } 
            catch (InterruptedException ex) 
            {}
        }
    }
}
