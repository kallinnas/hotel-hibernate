package model;

public enum RoomStatus {
    VACANT_AND_CLEAN("vc"), VACANT_AND_DIRTY("vd"), OCCUPIED("oc");

    public final String name;

    RoomStatus(String name) {
        this.name = name;
    }
}
