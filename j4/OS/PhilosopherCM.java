import java.util.concurrent.Semaphore;
import java.util.ArrayList;

public class PhilosopherCM extends Thread
{
    public  static final int N = 5;
    private static final int THINKING = 0;
    private static final int HUNGRY   = 1;
    private static final int EATING   = 2;
    private static String[] binary = {"", ""}; //0:哲学者の状態 1:フォークの状態
    private static int[] state = {THINKING,THINKING,THINKING,THINKING,THINKING};
    private static ArrayList<Integer> waitList = new ArrayList<Integer>();
    private int id;
    private Semaphore rightFork;
    private Semaphore leftFork;
    private static Semaphore[] philo = new Semaphore[N];
    private static Semaphore mutex1 = new Semaphore(1, true); //相互排除用

    public PhilosopherCM(int id, Semaphore rightFork, Semaphore leftFork){
        this.id = id;
        this.leftFork  = leftFork;
        this.rightFork = rightFork;
        binary[0] = binary[0] + "0";
        binary[1] = binary[1] + "1";
    }

    private void firstSleep(){
        takeLeftFork();
        try{
            sleep(id * 100); 
        } catch (InterruptedException e){}
    }

    private void thinking(){ //思考中
        System.out.println(binary[0] + " " + binary[1] + " philosopher" + id + " is thinking");
        try{
            sleep(1000); //1s考える
        } catch (InterruptedException e){}
    }

    private void eating(){ //食事中
        state[id] = EATING;
        System.out.println(binary[0] + " " + binary[1] + " philosopher" + id + " is eating");
        try{
            sleep(5000); //3s食事する
        } catch (InterruptedException e){}
    }

    private void takeFork(int id){
        try{
            mutex1.acquire();
        } catch (InterruptedException e){}
        state[id] = HUNGRY;
        oneToBinary(id, 0);
        System.out.println(binary[0] + " " + binary[1] + " philosopher" + id + " is hungry");
        test(id);
        mutex1.release();

        waitList.add(id);
        while(state[(id+1)%N] == EATING || state[(id-1+N)%N] == EATING || waitList.get(0) != id){
            waitList.add(id);
            waitList.remove(0);
            try{
                sleep(1000); 
            } catch (InterruptedException e){}
        }
        waitList.remove(0);
        try{
            mutex1.acquire();
        } catch (InterruptedException e){}
        state[id] = EATING;
        twoToBinary(id);
        mutex1.release();
        System.out.println(id);
        takeLeftFork();
        takeRightFork();
    }

    private void putFork(int id){
        try{
            mutex1.acquire();
        } catch (InterruptedException e){}
        state[id] = THINKING;
        zeroToBinary(id, 0);
        this.test((id-1+N)%N);
        this.test((id+1)%N);
        mutex1.release();
    }

    private void test(int id){
        if(state[id] == HUNGRY && state[(id-1+N)%N] != EATING && state[(id+1)%N] != EATING){
            putLeftFork();
            putRightFork();
        }
    }

    public void takeLeftFork(){
        try{
           leftFork.acquire();
        } catch (InterruptedException e){}
        oneToBinary((id+1)%N, 1);
        System.out.println(binary[0] + " " + binary[1] + " Philosopher" + id + " take fork" + (id+1)%N + "(left)");
    }
  
    public void takeRightFork(){
       try{
          rightFork.acquire();
       } catch (InterruptedException e){}
       oneToBinary(id, 1);
       System.out.println(binary[0] + " " + binary[1] + " Philosopher" + id + " take fork" + id + "(right)");
    }
  
    public void putLeftFork(){
       leftFork.release();
       zeroToBinary((id+1)%N, 1);
       System.out.println(binary[0] + " " + binary[1] + " philosopher" + id + " put fork" + (id+1)%N + "(left)");
    }
  
    public void putRightFork(){
       rightFork.release();
       zeroToBinary(id, 1);
       System.out.println(binary[0] + " " + binary[1] + " philosopher" + id + " put fork" + id + "(right)");
    }

    private void twoToBinary(int i){
        binary[0] = binary[0].substring(0,i) + "2" + binary[0].substring(i+1);
    }

    private void oneToBinary(int i, int s){
        binary[s] = binary[s].substring(0,i) + "1" + binary[s].substring(i+1);
    }

    private void zeroToBinary(int i, int s){
        binary[s] = binary[s].substring(0,i) + "0" + binary[s].substring(i+1);
    }

    @Override
    public void run(){
        firstSleep();
        for (int i = 0; i < 3; i++){
            thinking();
            takeFork(this.id);
            eating();
            putFork(this.id);
        }
        System.out.println(id + "end");
    }

    public static void main(String[] args)
    {
        Semaphore[] fork = new Semaphore[N];
        PhilosopherCM[] philosopher = new PhilosopherCM[N];
        for(int i = 0; i < N; i++){
            fork[i] = new Semaphore(1, true); //フォークは1本
            philo[i] = new Semaphore(1);
        }
        // 哲学者を生成
        for (int i = 0; i < N; i++) { //哲学者iが使えるフォークを渡す
          philosopher[i] = new PhilosopherCM(i, fork[i], fork[(i+1)%N]);
          philosopher[i].start();
        }

        // 完了を待ち合わせる
        for (int i = 0; i < N; i++) {
          try {
            philosopher[i].join();
          } catch(InterruptedException ex) {
            return;
          }
        }
        System.out.println("end");
    }
}