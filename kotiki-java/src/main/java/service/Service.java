package service;

import dao.colors.Color;
import dao.entities.Cat;
import dao.entities.CatsFriends;
import dao.entities.Owner;
import dao.entities.OwnersCats;
import dao.implementations.CatDAO;
import dao.implementations.CatsFriendsDAO;
import dao.implementations.OwnerDAO;
import dao.implementations.OwnersCatsDAO;
import dao.tools.DAOException;
import service.tools.ServiceException;

import java.sql.Timestamp;
import java.util.List;

public class Service {
    private CatDAO catDAO;
    private OwnerDAO ownerDAO;
    private CatsFriendsDAO catsFriendsDAO;
    private OwnersCatsDAO ownersCatsDAO;

    public Service(CatDAO catDAO, OwnerDAO ownerDAO, CatsFriendsDAO catsFriendsDAO, OwnersCatsDAO ownersCatsDAO) {
        this.catDAO = catDAO;
        this.ownerDAO = ownerDAO;
        this.catsFriendsDAO = catsFriendsDAO;
        this.ownersCatsDAO = ownersCatsDAO;
    }

    public Cat addCat(String name, Timestamp birthdate, String species, Color color) throws ServiceException {
        Cat cat = new Cat();

        cat.setName(name);
        cat.setBirthdate(birthdate);
        cat.setSpecies(species);
        cat.setColor(color);

        try {
            catDAO.add(cat);
        } catch (DAOException e) {
            throw new ServiceException("Problems with data access" + e.getMessage());
        }

        return cat;
    }

    public void removeCat(Cat cat) throws ServiceException {
        if (cat == null) {
            throw new ServiceException("Cat entity can not be null!");
        }

        List<CatsFriends> catsAndFriends = catsFriendsDAO.getAll();

        for (CatsFriends catAndFriend: catsAndFriends) {
            if (catAndFriend.getFirstCatId() == cat.getId() || catAndFriend.getSecondCatId() == cat.getId()) {
                try {
                    catsFriendsDAO.delete(catAndFriend);
                } catch (DAOException e) {
                    throw new ServiceException("Problem with data access" + e.getMessage());
                }
            }
        }

        List<OwnersCats> ownersAndCats = ownersCatsDAO.getAll();

        for (OwnersCats ownerAndCat: ownersAndCats) {
            if (ownerAndCat.getCatId() == cat.getId()) {
                try {
                    ownersCatsDAO.delete(ownerAndCat);
                } catch (DAOException e) {
                    throw new ServiceException("Problem with data access" + e.getMessage());
                }
            }
        }

        try {
            catDAO.delete(cat);
        } catch (DAOException e) {
            throw new ServiceException("Problems with data access" + e.getMessage());
        }
    }

    public Owner addOwner(String name, Timestamp birthdate) throws ServiceException {
        Owner owner = new Owner();

        owner.setName(name);
        owner.setBirthdate(birthdate);

        try {
            ownerDAO.add(owner);
        } catch (DAOException e) {
            throw new ServiceException("Problems with data access" + e.getMessage());
        }

        return owner;
    }

    public void removeOwner(Owner owner) throws ServiceException {
        if (owner == null) {
            throw new ServiceException("Owner entity can not be null!");
        }

        List<OwnersCats> ownersAndCats = ownersCatsDAO.getAll();

        for (OwnersCats ownerAndCat: ownersAndCats) {
            if (ownerAndCat.getOwnerId() == owner.getId()) {
                try {
                    ownersCatsDAO.delete(ownerAndCat);
                } catch (DAOException e) {
                    throw new ServiceException("Problem with data access" + e.getMessage());
                }
            }
        }

        try {
            ownerDAO.delete(owner);
        } catch (DAOException e) {
            throw new ServiceException("Problems with data access" + e.getMessage());
        }
    }

    public OwnersCats addCatToOwner(Cat cat, Owner owner) throws ServiceException {
        if (cat == null) {
            throw new ServiceException("Cat entity can not be null!");
        }

        if (owner == null) {
            throw new ServiceException("Owner entity can not be null!");
        }

        List<OwnersCats> ownersAndCats = ownersCatsDAO.getAll();

        for (OwnersCats ownerAndCat: ownersAndCats) {
            if (ownerAndCat.getOwnerId() == owner.getId() && ownerAndCat.getCatId() == cat.getId()) {
                throw new ServiceException("Such owner-cat pair already exist!");
            }
        }

        OwnersCats ownersCats = new OwnersCats();

        ownersCats.setOwnerId(owner.getId());
        ownersCats.setCatId(cat.getId());

        try {
            ownersCatsDAO.add(ownersCats);
        } catch (DAOException e) {
            throw new ServiceException("Problems with data access" + e.getMessage());
        }

        return ownersCats;
    }

    public void removeCatFromOwner(Cat cat, Owner owner) throws ServiceException {
        if (cat == null) {
            throw new ServiceException("Cat entity can not be null!");
        }

        if (owner == null) {
            throw new ServiceException("Owner entity can not be null!");
        }

        List<OwnersCats> ownersAndCats = ownersCatsDAO.getAll();

        for (OwnersCats ownerAndCat: ownersAndCats) {
            if (ownerAndCat.getOwnerId() == owner.getId() && ownerAndCat.getCatId() == cat.getId()) {
                try {
                    ownersCatsDAO.delete(ownerAndCat);
                } catch (DAOException e) {
                    throw new ServiceException("Problems with data access" + e.getMessage());
                }
            }
        }
    }

    public CatsFriends makeFriends(Cat firstCat, Cat secondCat) throws ServiceException {
        if (firstCat == null) {
            throw new ServiceException("Cat entity can not be null!");
        }

        if (secondCat == null) {
            throw new ServiceException("Cat entity can not be null!");
        }

        CatsFriends catsFriends = new CatsFriends();

        catsFriends.setFirstCatId(firstCat.getId());
        catsFriends.setSecondCatId(secondCat.getId());

        try {
            catsFriendsDAO.add(catsFriends);
        } catch (DAOException e) {
            throw new ServiceException("Problems with data access" + e.getMessage());
        }

        return catsFriends;
    }

    public void breakFriendship(Cat firstCat, Cat secondCat) throws ServiceException {
        if (firstCat == null) {
            throw new ServiceException("Cat entity can not be null!");
        }

        if (secondCat == null) {
            throw new ServiceException("Cat entity can not be null!");
        }

        List<CatsFriends> catsAndFriends = catsFriendsDAO.getAll();

        for (CatsFriends catAndFriend: catsAndFriends) {
            if (catAndFriend.getFirstCatId() == firstCat.getId() && catAndFriend.getSecondCatId() == secondCat.getId() ||
                    catAndFriend.getFirstCatId() == secondCat.getId() && catAndFriend.getSecondCatId() == firstCat.getId()) {
                try {
                    catsFriendsDAO.delete(catAndFriend);
                } catch (DAOException e) {
                    throw new ServiceException("Problems with data access" + e.getMessage());
                }
            }
        }
    }

    public List<Cat> getAllCats() {
        return catDAO.getAll();
    }

    public List<Owner> getAllOwners() {
        return ownerDAO.getAll();
    }

}
