package service;

import db.dao.RequestDao;
import db.dao.RequestDaoImpl;
import model.Request;

import java.util.LinkedList;
import java.util.Queue;

public class RequestServiceImpl implements RequestService {

    private RequestDao requestDao = new RequestDaoImpl();

    @Override
    public Queue<Request> getAllRequests() {
        return new LinkedList<>(requestDao.getAllRequests());
    }

    @Override
    public void updateRequest(Request request) {
        requestDao.updateRequest(request);
    }

    @Override
    public Request getTheOldestUndoneRequest() {
        return requestDao.getTheOldestUndoneRequest();
    }

    @Override
    public Request getRequestById(long id) {
        return requestDao.getRequestById(id);
    }

    @Override
    public Request getUnfinishedRequestByEmployeeId(long id) {
        return requestDao.getUnfinishedRequestByEmployeeId(id);
    }


}
