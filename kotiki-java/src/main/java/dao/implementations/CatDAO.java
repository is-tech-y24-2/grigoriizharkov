package dao.implementations;

import dao.interfaces.DAO;
import dao.entities.Cat;
import dao.tools.DAOException;
import dao.tools.HibernateSessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class CatDAO implements DAO<Cat> {
    @Override
    public List<Cat> getAll() {
        List<Cat> entities;

        Session session = HibernateSessionUtil.getSessionFactory().openSession();

        session.getTransaction().begin();
        entities = session.createQuery(
                "select cats from Cat cats", Cat.class).getResultList();
        session.getTransaction().commit();

        session.close();

        return entities;
    }

    @Override
    public void add(Cat cat) throws DAOException {
        try {
            Session session = HibernateSessionUtil.getSessionFactory().openSession();

            session.getTransaction().begin();
            session.save(cat);
            session.getTransaction().commit();

            session.close();

        } catch (HibernateException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public Cat getById(Long id) throws DAOException {
        Cat cat;
        try {
            Session session = HibernateSessionUtil.getSessionFactory().openSession();

            session.getTransaction().begin();
            cat = session.get(Cat.class, id);
            session.getTransaction().commit();

            session.close();

        } catch (HibernateException e) {
            throw new DAOException(e.getMessage(), e);
        }
        
        return cat;
    }

    @Override
    public void update(Cat cat) throws DAOException {
        try {
            Session session = HibernateSessionUtil.getSessionFactory().openSession();

            session.getTransaction().begin();
            session.update(cat);
            session.getTransaction().commit();

            session.close();

        } catch (HibernateException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Cat cat) throws DAOException {
        try {
            Session session = HibernateSessionUtil.getSessionFactory().openSession();

            session.getTransaction().begin();
            session.delete(cat);
            session.getTransaction().commit();

            session.close();

        } catch (HibernateException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }
}
