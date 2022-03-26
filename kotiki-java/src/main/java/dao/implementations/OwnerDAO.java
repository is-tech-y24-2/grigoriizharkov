package dao.implementations;

import dao.interfaces.DAO;
import dao.entities.Owner;
import dao.tools.DAOException;
import dao.tools.HibernateSessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class OwnerDAO implements DAO<Owner> {
    @Override
    public List<Owner> getAll() {
        List<Owner> entities;
        
        Session session = HibernateSessionUtil.getSessionFactory().openSession();
        
        session.getTransaction().begin();
        entities = session.createQuery(
                "select owners from Owner owners", Owner.class).getResultList();
        session.getTransaction().commit();
        
        session.close();
        
        return entities;
    }

    @Override
    public void add(Owner owner) throws DAOException {
        try {
            Session session = HibernateSessionUtil.getSessionFactory().openSession();

            session.getTransaction().begin();
            session.save(owner);
            session.getTransaction().commit();

            session.close();

        } catch (HibernateException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public Owner getById(Long id) throws DAOException {
        Owner owner;
        try {
            Session session = HibernateSessionUtil.getSessionFactory().openSession();

            session.getTransaction().begin();
            owner = session.get(Owner.class, id);
            session.getTransaction().commit();

            session.close();

        } catch (HibernateException e) {
            throw new DAOException(e.getMessage());
        }
        
        return owner;
    }

    @Override
    public void update(Owner owner) throws DAOException {
        try {
            Session session = HibernateSessionUtil.getSessionFactory().openSession();

            session.getTransaction().begin();
            session.update(owner);
            session.getTransaction().commit();

            session.close();

        } catch (HibernateException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void delete(Owner owner) throws DAOException {
        try {
            Session session = HibernateSessionUtil.getSessionFactory().openSession();

            session.getTransaction().begin();
            session.delete(owner);
            session.getTransaction().commit();

            session.close();

        } catch (HibernateException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
