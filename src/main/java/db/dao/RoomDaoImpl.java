package db.dao;

import db.HQLSchema;
import db.HibernateUtils;
import model.Room;
import model.RoomStatus;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class RoomDaoImpl implements RoomDao {

    private Room room;
    private Query query;
    private Session session;
    private Transaction transaction;


    @Override
    public List<Room> getVCRoom() {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            List list = session.createQuery(HQLSchema.SELECT_VC_ROOMS)
                    .setParameter("vc", RoomStatus.VACANT_AND_CLEAN)
                    .list();
            return new ArrayList<Room>(list);
        } finally {
            transaction.commit();
            session.close();
        }
    }

    @Override
    public Room persistRoom(int id, int capacity, double price) {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            room = new Room();
            room.setId(id);
            room.setCapacity(capacity);
            room.setPrice(price);
            room.setStatus(RoomStatus.VACANT_AND_CLEAN);
            session.persist(room);
        }finally {
            transaction.commit();
            session.close();
        }
        return room;
    }

    @Override
    public void update(Room room) {
        session = HibernateUtils.getHibernateSession();
        try{
            transaction = session.beginTransaction();
            session.update(room);
        }finally {
            transaction.commit();
            session.close();
        }
    }
}
