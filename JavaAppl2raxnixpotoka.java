
package javaappl2raxnixpotoka;

public class JavaAppl2raxnixpotoka {
    
    static final int STEPS = 100;
   
    public static void main(String[] args) throws InterruptedException {
        
        Monitor mon = new Monitor();
        
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < STEPS; i++) {
                    System.out.println("1 - " + i);
                    if(i >= STEPS/2){
                        synchronized (mon) {                      
                            mon.step = i;
                            mon.notify();
                        }
                    }    
                    
                }
            }
        });
        
        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try { synchronized (mon) {                        
                     while(mon.step < STEPS/2) mon.wait();//здесь спим до тех пор пока выполняется условие
                    }
                } catch (InterruptedException ex){}
                for (int i = 0; i < STEPS; i++) {
                    System.out.println("2 - " + i);
                }
                 
            }
        });
        
        th1.start();
        th2.start();
        
        th1.join();
        th2.join();
    }
    
}

class Monitor {
    int step;//параметр шага до которого дошел 1-ый поток
}
