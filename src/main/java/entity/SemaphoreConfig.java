package entity;

import db.dao.EmployeeDao;
import db.dao.EmployeeDaoImpl;
import lombok.Getter;

import java.util.concurrent.Semaphore;

public class SemaphoreConfig {
    private static SemaphoreConfig instance = null;

    @Getter
    private Semaphore reception;
    private Semaphore maintenance;
    private Semaphore housekeeping;

    public static SemaphoreConfig getInstance() {
        if (instance == null) return instance = new SemaphoreConfig();
        else return instance;
    }

    public SemaphoreConfig() {
        EmployeeDao dao = new EmployeeDaoImpl();
        this.reception = new Semaphore(dao.getAmountOfReceptionists());
//        this.maintenance = maintenance;
//        this.housekeeping = housekeeping;
    }
}
