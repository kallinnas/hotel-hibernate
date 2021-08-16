package db;

public class HQLSchema {

    private static final String TABLE_ROOM = "Room";
    private static final String TABLE_GUEST = "Guest";
    private static final String TABLE_PERSON = "Person";
    private static final String TABLE_REQUEST = "Request";
    private static final String TABLE_EMPLOYEE = "Employee";
    private static final String TABLE_RESERVATION = "Reservation";

    private static final String COL_ID = "id";
    private static final String COL_ROLE = "role";
    private static final String COL_TYPE = "type";
    private static final String COL_EMAIL = "email";
    private static final String COL_START = "start";
    private static final String COL_STATUS = "status";
    private static final String COL_ACCOUNT = "account";
    private static final String COL_LAST_NAME = "last_name";
    private static final String COL_CLIENT_ID = "client_id";
    private static final String COL_FIRST_NAME = "first_name";
    private static final String COL_IS_WORKING = "isWorking";
    private static final String COL_DEPARTMENT = "department";
    private static final String COL_EMPLOYEE_ID = "employee_id";
    private static final String COL_RESERVATION_ID = "reservation_id";
    private static final String COL_COMPLETION_TIME = "completion_time";

    /* REQUESTS */
    public static final String SELECT_ALL_REQUESTS = "FROM " + TABLE_REQUEST;

    public static final String SELECT_REQUEST_BY_ID = "FROM " + TABLE_REQUEST +
            "r LEFT JOIN FETCH r." +
            " WHERE " + COL_ID + "= :id";

    public static final String SELECT_LAST_INSERTED_REQUEST = "FROM " + TABLE_REQUEST +
            " WHERE " + COL_ID +
            " = (SELECT MAX(" + COL_ID +
            ") FROM " + TABLE_REQUEST + ")";

    public static final String SELECT_ALL_UNDONE_REQUEST = "FROM " + TABLE_REQUEST +
            " req WHERE " + COL_EMPLOYEE_ID +
            " IS NULL ORDER BY req." + COL_START;

    public static final String SELECT_UNFINISHED_REQUEST = "FROM " + TABLE_REQUEST +
            " WHERE " + COL_ID +
            "= :id AND " + COL_COMPLETION_TIME +
            " IS NULL";


    /* PERSON */
    public static final String SELECT_PERSON_BY_EMPLOYEE_ID = "FROM " + TABLE_PERSON +
            " WHERE " + COL_ROLE +
            "= :role AND " + COL_CLIENT_ID +
            "= :client_id";

    public static final String SELECT_PERSON_BY_GUEST_ID = "FROM " + TABLE_PERSON +
            " WHERE " + COL_ROLE +
            "= :role AND " + COL_CLIENT_ID +
            "= :client_id";

    public static final String SELECT_PERSON_BY_EMAIL = "FROM " + TABLE_PERSON +
            " WHERE " + COL_EMAIL + " = :email";

    /* EMPLOYEE */
    public static final String HAS_SUITABLE_EMPLOYEE = "FROM " + TABLE_EMPLOYEE +
            " WHERE " + COL_DEPARTMENT +
            "= :department AND " + COL_IS_WORKING + "=0";

    public static final String SELECT_ALL_EMPLOYEES = "FROM " + TABLE_EMPLOYEE + " emp";

    public static final String SELECT_FREE_EMPLOYEE = "FROM " + TABLE_EMPLOYEE +
            " WHERE " + COL_IS_WORKING + "=0";

    /* ROOM */
    public static final String SELECT_VC_ROOMS = "FROM " + TABLE_ROOM +
            " WHERE " + COL_STATUS + "= :vc";


}
