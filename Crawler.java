import java.util.LinkedList;
import java.util.concurrent.*;
import java.awt.event.*;
 
public class Crawler {
 
 private LinkedList<String> crawler;
 private boolean done;  
 private int size;  
 private int x = 0;
 
 
 public Crawler() {
  crawler = new LinkedList<String>();
  done = false;
  size = 0;
 }
 
 public synchronized void add(String s) {
  crawler.add(s);
  size++;
  notifyAll();
 }
 
 public synchronized String remove() {
  String s;
  while (!done && size == 0) {
   try {
    wait();
   } catch (Exception e) {};
  }
  if (size > 0) {
   s = crawler.remove();
   size--;
   notifyAll();
  } else
   s = null;
  return s;
 }
 
 public synchronized void finish() {
  done = true;
  notifyAll();
 }
}
