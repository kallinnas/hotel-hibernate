package db.dao;

import model.Request;

public interface RequestDao {
    void persistRequest(Request request);

    void updateRequest(Request request);
}
