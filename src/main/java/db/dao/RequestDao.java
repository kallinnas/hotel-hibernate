package db.dao;

import model.Request;

import java.util.List;

public interface RequestDao {
    void persistRequest(Request request);

    void updateRequest(Request request);

    Request getLastCreatedRequest();

    List<Request> getAllRequests();

    Request getTheOldestUndoneRequest();

    Request getRequestById(long id);

    Request getUnfinishedRequestByEmployeeId(long id);
}
