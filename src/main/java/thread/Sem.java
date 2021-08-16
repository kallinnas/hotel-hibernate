package thread;

import java.util.concurrent.Semaphore;

public class Sem {

    public static void main(String[] args) {
        Semaphore emp = new Semaphore(2);
        Req req1 = new Req();
        Req req2 = new Req();
        Req req3 = new Req();
        Req req4 = new Req();
        Req req5 = new Req();
        Req req6 = new Req();

        req1.emp = emp;
        req2.emp = emp;
        req3.emp = emp;
        req4.emp = emp;
        req5.emp = emp;
        req6.emp = emp;

        req1.start();
        req2.start();
        req3.start();
        req4.start();
        req5.start();
        req6.start();
    }

}

class Req extends Thread {
    Semaphore emp;

    @Override
    public void run() {
        System.out.println(this.getName() + " emp is waiting");
        try {
            emp.acquire();
            System.out.println(this.getName() + " emp is working");
            sleep(1000);
            System.out.println(this.getName() + " emp finished");
            emp.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
