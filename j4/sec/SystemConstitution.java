import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SystemConstitution{
   public static void main(String args[]){
      InputStreamReader isr = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(isr);
      int num = 0;
      String str;

      while(num == 0){
         str = null;
         System.out.print("台数 -> ");
         try{
            str = br.readLine();
            try{
               num = Integer.parseInt(str);
            }
            catch(NumberFormatException e){
               num = 0;
            }
            if(num == 0){
               System.out.println("入力エラーです。もう一度入力してください");
            }
         }
         catch(IOException e){
            e.printStackTrace();
            num = 1;
            break;
         }
      }

      if(num > 1){
         ArrayList<String> list = new ArrayList<String>();
         list = con(num);
         for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
         }
      }
      else{
         System.out.println("R");
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
      ArrayList<String> work = new ArrayList<String>();
      ArrayList<String> xList = new ArrayList<String>();
      ArrayList<String> yList = new ArrayList<String>();
      
      if(x == 1){
         xList.add("R");
      }
      else{
         for(int i = 1; i < x; i++){
            if(i > x-i) break;
            else work = hoge(i, x-i);
            for(int j = 0; j < work.size(); j++){
               xList.add(work.get(j));
            }
         }
      }
      if(y == 1){
         yList.add("R");
      }
      else{
         for(int i = 1; i < y; i++){
            if(i > y-i) break;
            else work = hoge(i, y-i);
            for(int j = 0; j < work.size(); j++){
               yList.add(work.get(j));
            }
         }
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