package service;

import model.Request;

import java.util.Queue;

public interface RequestService {
    Queue<Request> getAllRequests();

    void updateRequest(Request request);

    Request getTheOldestUndoneRequest();

    Request getRequestById(long id);

    Request getUnfinishedRequestByEmployeeId(long id);
}
