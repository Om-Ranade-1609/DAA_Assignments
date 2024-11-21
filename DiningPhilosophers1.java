
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class DiningPhilosophers1 {
    static class Philosopher extends Thread{
        int id;
        Lock leftFork;
        Lock rightFork;

        public Philosopher(int id, Lock leftFork, Lock rightFork){
            this.id = id;
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        @Override
        public void run(){
            try{

                leftFork.lock();
                System.out.println("Philosopher " + id + " picked up left fork");
                Thread.sleep(1000);

                rightFork.lock();
                System.out.println("Philosopher " + id + " picked up right fork");
                Thread.sleep(1000);

                rightFork.unlock();
                System.out.println("Philosopher " + id + "put down right fork ");

                leftFork.unlock();
                System.out.println("Philosopher " + id + "put down the left fork");

            }catch(Exception e){
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        int Num_Philosophers = 5;
        Lock[]forks = new ReentrantLock[Num_Philosophers];

        for(int i = 0 ; i<Num_Philosophers ; i++){
            forks[i] = new ReentrantLock();
        }

        Philosopher[]philosophers = new Philosopher[Num_Philosophers];

        for(int i = 0; i<Num_Philosophers; i++){
            Lock leftFork = forks[i];
            Lock rightFork = forks[(i+1)%Num_Philosophers];

            if(i==Num_Philosophers-1){
                philosophers[i] = new Philosopher(i, rightFork, leftFork);
            }else{
                philosophers[i] = new Philosopher(i, leftFork, rightFork);
            }

            philosophers[i].start();
        }

        for(Philosopher philosopher : philosophers){
            try{
                philosopher.join();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        System.out.println("\nAll Philosophers have eaten exactly once!");
    }
}
