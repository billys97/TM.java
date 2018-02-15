//See Markdown file for "runthrough" of code design.

import java.sql.Timestamp;

import java.io.*;
import java.util.*;
 

public class TM {

   public static void main (String args[]) {
      
      TM tm = new TM();
      tm.appMain(args);
      
   }
   
   public void appMain (String args[]) {
   
         
      if (args.length == 0) {
      
         System.out.println("Usage: ");
         System.out.println("java TM <command> <taskname> <description>");
         System.out.println("Possible commands are: start, stop, describe, summarize, size.");
         System.exit(0);
      
      }
      
      String cmd = args[0];
      String data = null;
      String description = null;
      String sizes = null;
       
      if ("begin".equals(cmd)) {
      
         Begin start = new Begin();
         data = args[1];
         start.starting(cmd, data);

      }
      
      else if ("end".equals(cmd)) {
      
         End end = new End();
         data = args[1];
         end.ending(cmd, data);
      
      }
      
      else if ("summarize".equals(cmd)) {
      
         SummarizeTask summary = new SummarizeTask();
         data = args[1];
         summary.splitting(cmd, data);
      
      }
      
      else if ("describe".equals(cmd)) {
      
         Describe info = new Describe();
         data = args[1];
         description = args[2];
         info.taskDesc(cmd, data, description);
  
      }
      
      else if (("log".equals(cmd)) && (data == null)) {
      
         Log text = new Log();
         text.file();
      
      }
      
      else if ("size".equals(cmd)) {
      
         Size size = new Size();
         data = args[1];
         sizes = args[2];
         size.sizes(cmd, data, sizes);
      
      
      }
   }  
}


class Log {

   public void file() {
   
      String logInfo = "log.txt";
   
      String line = null;
   
      try {
   
         FileReader fileReader = new FileReader(logInfo);
      
         BufferedReader bufferedReader = new BufferedReader(fileReader);
         
            while((line = bufferedReader.readLine()) != null) {
         
            System.out.println(line);
         
            }
         
         bufferedReader.close();
   
      }
   
      catch (FileNotFoundException ex) {
      
         System.out.println("Unable to find file '" + logInfo + "' ");
      
      }
      
      catch (IOException ex) {
      
         System.out.println("Error reading file '" + logInfo + "' ");
      
      }   
   
   }
   
}


class Writer {

   public static void write(String a, String b, String c) {
   
      Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());  
       
      String times = timestamp1.toString();
        
         try { 
     
            FileWriter fileWriter = new FileWriter("log.txt", true);
      
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(times + " | " + a + " | " + b + " | ");
         
               if ("describe".equals(a)) {
         
                  bufferedWriter.append(c);
         
               }
         
            bufferedWriter.newLine();
      
            bufferedWriter.close();
      
         }
      
         catch (FileNotFoundException ex) {
      
            System.out.println("Unable to find file: \"log.txt\"");
      
         }
      
         catch (IOException ex) {
      
            System.out.println("Error reading file: \"log.txt\"");
      
         }
              
   }
   
}

class Begin {

   public void starting (String a, String b) {
         
         Writer time1 = new Writer();
         time1.write(a, b, null);
   
   }      
}

class End {

   public void ending (String a, String b) {
         
         Writer time2 = new Writer();
         time2.write(a, b, null);
         
   } 

}

class Describe {

   public void taskDesc (String a, String b, String c) {
   
         Writer desc = new Writer();
         desc.write(a, b, c);

   }
   
}

class Size {

   public void sizes(String a, String b, String c) {
   
         Writer size = new Writer();
         size.write(a, b, c);
   
   }


}

class SummarizeTask {

   public void splitting (String a, String b) {
   
      String line = null;
      String logInfo = "log.txt";
      int x = 0;
      
      String[] day = new String[100];
      String[] time = new String[100];
      String[] aCmd = new String[100];
      String[] task = new String[100];
      String[] aDesc = new String[100];
      
      try {
   
         FileReader fileReader = new FileReader(logInfo);
      
         BufferedReader bufferedReader = new BufferedReader(fileReader);
         
            while((line = bufferedReader.readLine()) != null) {
         
               day[x] = line.split(" ")[0];
               time[x] = line.split("|")[1];
               aCmd[x] = line.split("|")[2];
               task[x] = line.split("|")[3];
               aDesc[x] = line.split("|")[4];
              
               if ((task[x].equals(b) && aCmd[x].equals("begin")) == true){
               
                  String temp1 = time[x];
                  String temp2 = aCmd[x];
                  String temp3 = task[x];
                  String temp4 = aDesc[x]; 
                  
                  int counter = x;
                  
                  while ((task[x].equals(b) && aCmd[counter].equals("end")) == false) {
                     
                     counter++;
                                 
                  }
                  
                  System.out.println("Task: " + task[x]);
                  System.out.println("Description: " + aDesc[x]);
                  
                  
                  long time1 = Long.parseLong(time[x]);
                  long time2 = Long.parseLong(time[counter]);
                  
                  String dif = difference(time1, time2);
                  System.out.println("Total elapsed time: " + dif);
                    
                    
               }
               
               x++;
                       
            }         
         
         bufferedReader.close();
   
      }
   
      catch (FileNotFoundException ex) {
      
         System.out.println("Unable to find file '" + logInfo + "' ");
      
      }
      
      catch (IOException ex) {
      
         System.out.println("Error reading file '" + logInfo + "' ");
      
      }
         
   }
   
   
   public String difference(long time1, long time2) {
   
      long difference = time2 - time1;
      
      long sec = difference / 1000 % 60;
      long min = difference / (1000 * 60) % 60;
      long hrs = difference / (1000 * 60 * 60) % 24;
      long day = difference / (1000 * 60 * 60 * 24);
      
      return "Days: " + day + " Hours: " + hrs + " Minutes: " + min + " Seconds: " + sec;   
   
   
   }   
}   





