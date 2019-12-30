//�Q�l�@http://msugai.fc2web.com/java/thread/diningPh.html

import java.util.ArrayList;

public class PhilosopherMT{
   public static void main(String[] args){
      // ���L�I�u�W�F�N�g
      Fork fork = new Fork();
      // �N�w��
      Philosopher[] phils = new Philosopher[5];

      // �C���X�^���X��
      for (int i=0; i<5; i++) {
         phils[i] = new Philosopher(i);
      }

      // �N�w�҂̃v���p�e�B
      //                     eating, thinking, shared object
      phils[0].setProperties(2000,   1000,     fork);
      phils[1].setProperties(1900,   1100,     fork);
      phils[2].setProperties(1800,   1200,     fork);
      phils[3].setProperties(1700,   1300,     fork);
      phils[4].setProperties(1600,   1400,     fork);

      // �X���b�h
      Thread[] thres = new Thread[5];
      for (int i=0; i<5; i++) {
         // �N�w�҂��X���b�h�ɈϏ�
         thres[i] = new Thread(phils[i]);
      }

      // �X���b�h�̊J�n
      for (int i=0; i<5; i++) {
         thres[i].start();
      }
   }
}

// ���L�I�u�W�F�N�g
class Fork {
   final int N = 5;
   final int EATING = 0;
   final int HUNGRY = 1;
   final int THINKING = 2;
	// �N�w�҂̏��
   private int state[] = {2,2,2,2,2};
	// �����ꂩ�̓N�w�҂̎�ɂƂ��Ă��邩�ǂ���
   private Condition self[] = new Condition[N];
	
	// �R���X�g���N�^
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

	// ��ɂƂ���
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
	
	// �肩�痣�����
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
	int id;       	// �N�w�҂̎��ʔԍ�
	int eatTime;	// �H������
	int thinkTime;	// �v������
	Fork fork = new Fork();	// ���L�I�u�W�F�N�g
	
	// �R���X�g���N�^
	Philosopher(int i) {
      id = i;
	}
	
	// �N�w�҂̃v���p�e�B�̃Z�b�g
	public void setProperties(int eating, int thinking, Fork obj) {
		eatTime = eating;
		thinkTime = thinking;
		fork = obj;
	}
	
	// �󕠂�������ƃt�H�[�N����Ɏ��
	public void eat() {  
      System.out.println(id + " is eating.");
		try {
			// �H����
			Thread.sleep(eatTime);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
	
	// �v��
	public void think() {
      System.out.println(id + " is thinking.");
		try {
			// �v����
			Thread.sleep(thinkTime);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
	
	// �X���b�h�� run() ���\�b�h
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