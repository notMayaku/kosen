//参考　http://msugai.fc2web.com/java/thread/diningPh.html

import java.util.ArrayList;

public class PhilosopherMT{
   public static void main(String[] args){
      // 共有オブジェクト
      Fork fork = new Fork();
      // 哲学者
      Philosopher[] phils = new Philosopher[5];

      // インスタンス化
      for (int i=0; i<5; i++) {
         phils[i] = new Philosopher(i);
      }

      // 哲学者のプロパティ
      //                     eating, thinking, shared object
      phils[0].setProperties(2000,   1000,     fork);
      phils[1].setProperties(1900,   1100,     fork);
      phils[2].setProperties(1800,   1200,     fork);
      phils[3].setProperties(1700,   1300,     fork);
      phils[4].setProperties(1600,   1400,     fork);

      // スレッド
      Thread[] thres = new Thread[5];
      for (int i=0; i<5; i++) {
         // 哲学者をスレッドに委譲
         thres[i] = new Thread(phils[i]);
      }

      // スレッドの開始
      for (int i=0; i<5; i++) {
         thres[i].start();
      }
   }
}

// 共有オブジェクト
class Fork {
   final int N = 5;
   final int EATING = 0;
   final int HUNGRY = 1;
   final int THINKING = 2;
	// 哲学者の状態
   private int state[] = {2,2,2,2,2};
	// いずれかの哲学者の手にとられているかどうか
   private Condition self[] = new Condition[N];
	
	// コンストラクタ
	Fork() {
      for(int i = 0; i < N; i++){
         self[i] = new Condition();
      }
	}
   
   private void test(int id){
      if(state[(id+1)%N] != EATING && state[(id+N-1)%N] != EATING && state[id] == HUNGRY){
         state[id] = EATING;
         self[id].signal(id);
      }
   }

	// 手にとられる
	public void pick(int id) {
      state[id] = HUNGRY;
      test(id);
      if(state[id] != EATING){
         self[id].wait(id);
      }
      while(self[id].queue()){
         try{
            Thread.sleep(300); 
        } catch (InterruptedException e){}
      }
      System.out.println(id + " pick fork");
	}
	
	// 手から離される
	public void down(int id) {
      state[id] = THINKING;
      System.out.println(id + " down fork");
      test((id+N-1)%N);
      test((id+1)%N);
	}
}

class Condition{
   private ArrayList<Integer> waitList = new ArrayList<>();

   public void wait(int id){
      waitList.add(id);
   }

   public void signal(int id){
      if(queue()){
         waitList.remove(0);
      }
   }

   public boolean queue(){
      if(waitList.size() > 0){
         return true;
      }
      else{
         return false;
      }
   }
}

class Philosopher implements Runnable {
	int id;       	// 哲学者の識別番号
	int eatTime;	// 食事時間
	int thinkTime;	// 思索時間
	Fork fork = new Fork();	// 共有オブジェクト
	
	// コンストラクタ
	Philosopher(int i) {
      id = i;
	}
	
	// 哲学者のプロパティのセット
	public void setProperties(int eating, int thinking, Fork obj) {
		eatTime = eating;
		thinkTime = thinking;
		fork = obj;
	}
	
	// 空腹を感じるとフォークを手に取る
	public void eat() {  
      System.out.println(id + " is eating.");
		try {
			// 食事中
			Thread.sleep(eatTime);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
	
	// 思索
	public void think() {
      System.out.println(id + " is thinking.");
		try {
			// 思索中
			Thread.sleep(thinkTime);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
	
	// スレッドの run() メソッド
	public void run() {
      int i = 0;
		while (i < 3) {
         think();
         fork.pick(id);
         eat();
		   fork.down(id);
         i++;
      }
      System.out.println(id + " is finish");
	}
}