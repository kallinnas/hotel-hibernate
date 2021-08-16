package thread;

import model.Reservation;
import service.ReservationServiceImpl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorS {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        service.submit(new MyRun());
        System.out.println(service.submit(new MyCall()).get());
        service.shutdown();
    }

    static class MyRun implements Runnable {
        @Override
        public void run() {
            System.out.println(new ReservationServiceImpl().getReservationByEmail("k@gmail.com"));
        }
    }

    static class MyCall implements Callable<Reservation> {
        @Override
        public Reservation call() {
            return new ReservationServiceImpl().getReservationByEmail("k@gmail.com");
        }
    }


}
