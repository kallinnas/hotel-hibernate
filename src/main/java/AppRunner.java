import operator.OperatorO;

public class AppRunner {
    public static void main(String[] args) {
        Emulator emulator = new Emulator();
        emulator.dropTables();
        emulator.emulateEmployees();
        emulator.emulateRooms();
        emulator.emulateReservations();

        System.out.println("***************************************");
        OperatorO.Operator().distributionAndRequestProcessing();



    }


}
