import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.ThreadLocalRandom;

public class FindPi{

    public static AtomicLong hits = new AtomicLong();
    public static ArrayList<Thread> thread_tracker = new ArrayList<Thread>();
    public static ArrayList<Integer> thread_iters = new ArrayList<Integer>();

    public static void main(String[] args){

    if(args.length < 2){
      System.out.println("Exiting, not enough args");
      System.exit(-1);
    }
    Long thread_amt = Long.parseLong(args[0]);
    Long iterations = Long.parseLong(args[1]);
    // assuming evenly divisible values are entered
    Long equal_shares = iterations/thread_amt;

    while(thread_amt>0){
      thread_tracker.add(new Thread(()->{
        for(int i = 0; i < equal_shares;i++){
          Double x = ThreadLocalRandom.current().nextDouble(1);
          Double y = ThreadLocalRandom.current().nextDouble(1);
          if(Math.pow(x,2)+Math.pow(y,2) < 1){
            hits.incrementAndGet();
          }
        }
      }));
      thread_amt--;
    }
    try{
      for(Thread t:thread_tracker){
        t.start();
      }
      for(Thread t:thread_tracker){
        t.join();
      }
    } catch(Exception e){
      System.err.println(e);
    }
    System.out.println("Total: "+iterations);
    System.out.println("Inside: "+hits);
    double ratio = (double)hits.get()/iterations;
    System.out.println("Ratio: "+ratio);
    System.out.println("Pi: "+ratio*4.0);

  }
}
