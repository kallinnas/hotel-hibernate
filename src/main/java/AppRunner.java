import operator.OperatorO;

public class AppRunner {
    public static void main(String[] args) throws InterruptedException {
        Emulator emulator = new Emulator();
        emulator.dropTables();
        emulator.emulateEmployees();
        emulator.emulateRooms();
        emulator.emulateReservations();


//        EmployeeDao dao = new EmployeeDaoImpl();
//        System.out.println(dao.hasSuitableEmployee(new RequestDaoImpl().getLastCreatedRequest()));

        OperatorO.Operator().processRequests();





    }


}
