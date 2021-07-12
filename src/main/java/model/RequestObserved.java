package model;

import java.util.Observer;

public interface RequestObserved {
    public void addObserver(Observer observer);

    public void removeObserver(Observer observer);

    public void findExecutor();
}
