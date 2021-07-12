package model;

public enum RequestType {
    TURN_DOWN_SERVICE("TURN_DOWN_SERVICE", Department.HOUSE_KEEPING, 15,15000),
    CLEAN_UP("CLEAN_UP", Department.HOUSE_KEEPING, 0, 60000),
    CHECK_OUT("CHECK_OUT", Department.RECEPTION, 0, 5000),
    CHECK_IN("CHECK_IN",Department.RECEPTION,0, 10000),
    FIX_UP("FIX_UP", Department.MAINTENANCE, 0, 30000),
    ROOM_SERVICE("ROOM_SERVICE",Department.HOUSE_KEEPING, 50, 10000);

    public final String name;
    public final Department department;
    public final double price;
    public final int timeLimit;

    // modifier 'private' is redundant for enum constructor
    RequestType(String name, Department department, double price, int timeLimit) {
        this.name = name;
        this.department = department;
        this.price = price;
        this.timeLimit = timeLimit;
    }
}
