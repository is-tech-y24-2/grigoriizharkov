package dao.implementations;

import dao.entities.OwnersCats;
import dao.interfaces.DAO;
import dao.tools.DAOException;
import dao.tools.HibernateSessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class OwnersCatsDAO implements DAO<OwnersCats> {
    @Override
    public List<OwnersCats> getAll() {
        List<OwnersCats> entities;

        Session session = HibernateSessionUtil.getSessionFactory().openSession();

        session.getTransaction().begin();
        entities = session.createQuery(
                "select ownerscats from OwnersCats ownerscats", OwnersCats.class).getResultList();
        session.getTransaction().commit();

        session.close();

        return entities;
    }

    @Override
    public void add(OwnersCats ownersCats) throws DAOException {
        try {
            Session session = HibernateSessionUtil.getSessionFactory().openSession();

            session.getTransaction().begin();
            session.save(ownersCats);
            session.getTransaction().commit();

            session.close();

        } catch (HibernateException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public OwnersCats getById(Long id) throws DAOException {
        OwnersCats ownersCats;
        try {
            Session session = HibernateSessionUtil.getSessionFactory().openSession();

            session.getTransaction().begin();
            ownersCats = session.get(OwnersCats.class, id);
            session.getTransaction().commit();

            session.close();

        } catch (HibernateException e) {
            throw new DAOException(e.getMessage());
        }

        return ownersCats;
    }

    @Override
    public void update(OwnersCats ownersCats) throws DAOException {
        try {
            Session session = HibernateSessionUtil.getSessionFactory().openSession();

            session.getTransaction().begin();
            session.update(ownersCats);
            session.getTransaction().commit();

            session.close();

        } catch (HibernateException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(OwnersCats ownersCats) throws DAOException {
        try {
            Session session = HibernateSessionUtil.getSessionFactory().openSession();

            session.getTransaction().begin();
            session.delete(ownersCats);
            session.getTransaction().commit();

            session.close();

        } catch (HibernateException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }
}
