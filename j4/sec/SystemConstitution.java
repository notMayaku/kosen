import java.util.ArrayList;

public class SystemConstitution{
   public static void main(String args[]){
      ArrayList<String> list = new ArrayList<String>();

      list = con(4);
      for(int i = 0; i < list.size(); i++){
         System.out.println(list.get(i));
      }
   }

   public static ArrayList<String> con(int f){
      ArrayList<String> list = new ArrayList<String>();
      ArrayList<String> work = new ArrayList<String>();

      for(int i = 1; i < f; i++){
         if(i > f-i) break;
         else work = hoge(i, f-i);
         for(int j = 0; j < work.size(); j++){
            list.add(work.get(j));
         }
      }
      return list;
   }

   public static ArrayList<String> hoge(int x, int y){
      ArrayList<String> list = new ArrayList<String>();
      ArrayList<String> xList = new ArrayList<String>();
      ArrayList<String> yList = new ArrayList<String>();
      
      if(x == 1){
         xList.add("R");
      }
      else{
         xList = hoge(x-1, 1);
      }
      if(y == 1){
         yList.add("R");
      }
      else{
         yList = hoge(y-1, 1);
      }

      if(x == 1 && y == 1){
         list.add("R+R");
         list.add("R*R");
      }
      else{
         for(int xi = 0; xi < xList.size(); xi++){
            for(int yi = 0; yi < yList.size(); yi++){
               list.add("(" + xList.get(xi) + ")+(" + yList.get(yi) + ")");
               list.add("(" + xList.get(xi) + ")*(" + yList.get(yi) + ")");
            }
         }
      }
      return list;
   }
}