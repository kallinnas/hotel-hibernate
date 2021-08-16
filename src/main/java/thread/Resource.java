package thread;

import lombok.Setter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Resource {

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Res res = new Res();
        res.i = 5;
        res.j = 7;

        MyTread1 myTread1 = new MyTread1();
        myTread1.setRes(res);
        myTread1.setName("one");
        MyTread2 myTread2 = new MyTread2();
        myTread2.setRes(res);

        myTread1.start();
        myTread2.start();

//        myTread1.join();
//        myTread2.join();
        System.out.println(res.i);
        System.out.println(res.j);
    }

    @Setter
    static class MyTread1 extends Thread {
        Res res;

        @Override
        public void run() {
            lock.lock();
            System.out.println("start - 1");
            res.changeI();
            System.out.println("working - 1");
            res.changeJ();
            lock.unlock();
            System.out.println("unlock - 1");
            System.out.println("end - 1");
        }
    }

    @Setter
    static class MyTread2 extends Thread {
        Res res;

        @Override
        public void run() {
            System.out.println("start - 2");
            while (true) {
                if (lock.tryLock()) {
                    System.out.println("working - 2");
                    res.changeI();
                    res.changeJ();
                    break;
                } else {
                    System.out.println("waiting - 2");
                }
            }
            System.out.println("end - 2");
        }
    }


    static class Res {
        int i;
        int j;

        // or
//        Lock lock = new ReentrantLock();

        /*synchronized*/ void changeI() {
//            lock.lock();
            int a = i;
            if (Thread.currentThread().getName().equals("one")) {
                Thread.yield();
            }
            a++;
            i = a;
//            lock.unlock();
        }

        /*synchronized*/ void changeJ() {
            int a = j;
            if (Thread.currentThread().getName().equals("one")) {
                Thread.yield(); // Transmits running process to another thread
            }
            a++;
            j = a;
//            lock.unlock();
        }
    }

}


