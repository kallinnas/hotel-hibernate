package model;

public enum  Department {
    MAINTENANCE("MAINTENANCE"), HOUSE_KEEPING("HOUSE_KEEPING"), RECEPTION("RECEPTION");

    public final String name;

    Department(String name) {
        this.name = name;
    }
}
