
import java.util.*;
import java.io.*;
import java.text.*;
 
public class Spider {
 
  private Crawler work;
  static int i = 0;
 
 private class Worker implements Runnable {
 
  public Crawler q;
 
  public Worker(Crawler work) {
   q = work;
  }
 
  public void run() {
   String name;
   while ((name = q.remove()) != null) {
    File f = new File(name);
    String input[] = f.list();
    if (input == null)
     continue;
    for (String entry : input) {
     if (entry.compareTo(".") == 0)
      continue;
     if (entry.compareTo("..") == 0)
      continue;
     String fn = name + "\\" + entry;
     System.out.println(fn);
    }
   }
  }
 }
 
 public Spider() {
  work = new Crawler();
 }
 
 public Worker createWorker() {
  return new Worker(work);
 }
 
 
 public void processDirectory(String dir) {
   try{
   File file = new File(dir);
   if (file.isDirectory()) {
    String entries[] = file.list();
    if (entries != null)
     work.add(dir);
 
    for (String entry : entries) {
     String subdir;
     if (entry.compareTo(".") == 0)
      continue;
     if (entry.compareTo("..") == 0)
      continue;
     if (dir.endsWith("\\"))
      subdir = dir+entry;
     else
      subdir = dir+"\\"+entry;
     processDirectory(subdir);
    }
   }}catch(Exception e){}
 }
 
 public static void main(String Args[]) {
 
  Spider fc = new Spider();
 
 
  int N = 5;
  ArrayList<Thread> thread = new ArrayList<Thread>(N);
  for (int i = 0; i < N; i++) {
   Thread t = new Thread(fc.createWorker());
   thread.add(t);
   t.start();
  }
 

  fc.processDirectory("C:/Users/Nawal/Desktop");
 

 
  fc.work.finish();
 
  for (int i = 0; i < N; i++){
   try {
    thread.get(i).join();
   } catch (Exception e) {};
  }
 }
}
