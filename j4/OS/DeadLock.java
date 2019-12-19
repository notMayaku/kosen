import java.util.concurrent.Semaphore;

public class DeadLock{
   public static final int N = 5;
   private Semaphore leftFork;
   private Semaphore rightFork;

   public DeadLock(int id, Semaphore rightFork, Semaphore leftFork)
   {
       this.leftFork  = leftFork;
       this.rightFork = rightFork;
   }

   public boolean takeLeftFork(){
      try{
         leftFork.acquire();
      } catch (InterruptedException e){
         return false;
      }
      return true;
   }

   public boolean takeRightFork(){
      try{
         rightFork.acquire();
      } catch (InterruptedException e){
         return false;
      }
      return true;
   }
   public static void main(String args[]){
      Semaphore[] fork = new Semaphore[N];
      DeadLock[] philosopher = new DeadLock[N];

      for(int i = 0; i < N; i++){
          fork[i] = new Semaphore(1, true); //フォークは1本
      }

      for (int i = 0; i < N; i++) { //哲学者iが使えるフォークを渡す
         philosopher[i] = new DeadLock(i, fork[i], fork[(i+1)%N]);
       }

      for(int i = 0; i < N; i++){
         if(philosopher[i].takeLeftFork()){
            System.out.println("Philosopher" + i + " get left fork");
         }
         else{
            System.out.println("Philosopher" + i + " don't get left fork");
         }
      }

      for(int i = 0; i < N; i++){
         if(philosopher[i].takeRightFork()){
            System.out.println("Philosopher" + i + " get right fork");
         }
         else{
            System.out.println("Philosopher" + i + " don't get right fork");
         }
      }

      System.out.println("finished");
   }
}