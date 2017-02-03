
package turnstileviktor;


public class Turnstile extends Thread
{
    private int localCount;
    private TurnstileClient tClient;
    
    public int getLocalCount() 
    {
        return localCount;
    }

    public void incrementLocalCount() 
    {
        localCount++;
    }
    
    @Override
    public void run()
    {
        try
        {
            while(true)
            {
                tClient = new TurnstileClient("localhost", 8080);
                localCount++;
                tClient.add1fromTurnstile();
//                tClient.registerTurnstile(this.localCount);
                Thread.sleep(1000);
            }
        }
        catch(Exception e)
        {}
    }
    
}
