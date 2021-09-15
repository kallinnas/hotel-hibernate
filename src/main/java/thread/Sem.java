package thread;

import java.util.concurrent.Semaphore;

public class Sem {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        Empl empl1 = new Empl();
        Empl empl2 = new Empl();
        Empl empl3 = new Empl();
        Empl empl4 = new Empl();
        Empl empl5 = new Empl();
        Empl empl6 = new Empl();

        empl1.semaphore = semaphore;
        empl2.semaphore = semaphore;
        empl3.semaphore = semaphore;
        empl4.semaphore = semaphore;
        empl5.semaphore = semaphore;
        empl6.semaphore = semaphore;

        empl1.start();
        empl2.start();
        empl3.start();
        empl4.start();
        empl5.start();
        empl6.start();
    }

}

class Empl extends Thread {
    Semaphore semaphore;

    @Override
    public void run() {
        System.out.println(this.getName() + " emp is waiting");
        try {
            semaphore.acquire();
            System.out.println(this.getName() + " emp is working");
            sleep(1000);
            System.out.println(this.getName() + " emp finished");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
