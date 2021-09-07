package thread;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class WaitNotify {
    private static final List<Object> list = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        Thread t1 = new Thread(new Operator());
        Thread t2 = new Thread(new Machine());
        t1.start();
        t2.start();
    }

    static class Operator implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            Scanner sc = new Scanner(System.in);
            while (true) {
                synchronized (list) {
                    list.add(sc.nextLine());
                    list.notify();
                }
                Thread.sleep(5);
            }
        }
    }

    static class Machine implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            while (list.isEmpty()) {
                synchronized (list) {
                    list.wait();
                    System.out.println(list.remove(0));
                }
            }
        }
    }

}
