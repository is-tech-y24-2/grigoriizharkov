package dao.implementations;

import dao.entities.CatsFriends;
import dao.interfaces.DAO;
import dao.tools.DAOException;
import dao.tools.HibernateSessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class CatsFriendsDAO implements DAO<CatsFriends> {
    @Override
    public List<CatsFriends> getAll() {
        List<CatsFriends> entities;

        Session session = HibernateSessionUtil.getSessionFactory().openSession();

        session.getTransaction().begin();
        entities = session.createQuery(
                "select catsfrineds from CatsFriends catsfrineds", CatsFriends.class).getResultList();
        session.getTransaction().commit();

        session.close();

        return entities;
    }

    @Override
    public void add(CatsFriends catsFriends) throws DAOException {
        try {
            Session session = HibernateSessionUtil.getSessionFactory().openSession();

            session.getTransaction().begin();
            session.save(catsFriends);
            session.getTransaction().commit();

            session.close();

        } catch (HibernateException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public CatsFriends getById(Long id) throws DAOException {
        CatsFriends catsFriends;
        try {
            Session session = HibernateSessionUtil.getSessionFactory().openSession();

            session.getTransaction().begin();
            catsFriends = session.get(CatsFriends.class, id);
            session.getTransaction().commit();

            session.close();

        } catch (HibernateException e) {
            throw new DAOException(e.getMessage());
        }

        return catsFriends;
    }

    @Override
    public void update(CatsFriends catsFriends) throws DAOException {
        try {
            Session session = HibernateSessionUtil.getSessionFactory().openSession();

            session.getTransaction().begin();
            session.update(catsFriends);
            session.getTransaction().commit();

            session.close();

        } catch (HibernateException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(CatsFriends catsFriends) throws DAOException {
        try {
            Session session = HibernateSessionUtil.getSessionFactory().openSession();

            session.getTransaction().begin();
            session.delete(catsFriends);
            session.getTransaction().commit();

            session.close();

        } catch (HibernateException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }
}
