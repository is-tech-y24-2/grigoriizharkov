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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ServiceTest {

    @Mock
    private CatDAO catDAO;
    @Mock
    private OwnerDAO ownerDAO;
    @Mock
    private CatsFriendsDAO catsFriendsDAO;
    @Mock
    private OwnersCatsDAO ownersCatsDAO;

    private Service service;

    public ServiceTest() {
        MockitoAnnotations.openMocks(this);
        this.service = new Service(catDAO, ownerDAO, catsFriendsDAO, ownersCatsDAO);
    }

    @Test
    void addCat_Should_Return_True() throws Exception {
        Cat cat = new Cat();

        cat.setName("default");
        cat.setBirthdate(Timestamp.valueOf("2012-12-12 00:00:00.000000000"));
        cat.setSpecies("default");
        cat.setColor(Color.White);

        given(catDAO.getById(1L)).willReturn(cat);

        Cat returnedCat = service.addCat("default", Timestamp.valueOf("2012-12-12 00:00:00.000000000"),
                "default", Color.White);

        assertEquals(catDAO.getById(1L), returnedCat);
    }

    @Test
    void addOwner_Should_Return_True() throws Exception {
        Owner owner = new Owner();

        owner.setName("default");
        owner.setBirthdate(Timestamp.valueOf("2012-12-12 00:00:00.000000000"));

        given(ownerDAO.getById(1L)).willReturn(owner);

        Owner returnedOwner = service.addOwner("default", Timestamp.valueOf("2012-12-12 00:00:00.000000000"));

        assertEquals(ownerDAO.getById(1L), returnedOwner);
    }

    @Test
    void addCatToOwner_Should_Return_True() throws Exception {
        OwnersCats ownersCats = new OwnersCats();

        ownersCats.setCatId(1L);
        ownersCats.setOwnerId(1L);

        given(ownersCatsDAO.getById(1L)).willReturn(ownersCats);

        Cat cat = new Cat();

        cat.setId(1L);
        cat.setName("default");
        cat.setBirthdate(Timestamp.valueOf("2012-12-12 00:00:00.000000000"));
        cat.setSpecies("default");
        cat.setColor(Color.White);

        Owner owner = new Owner();

        owner.setId(1L);
        owner.setName("default");
        owner.setBirthdate(Timestamp.valueOf("2012-12-12 00:00:00.000000000"));

        OwnersCats returnedOwnersCats = service.addCatToOwner(cat, owner);

        assertEquals(ownersCatsDAO.getById(1L).getCatId(), returnedOwnersCats.getCatId());
        assertEquals(ownersCatsDAO.getById(1L).getOwnerId(), returnedOwnersCats.getOwnerId());

    }

    @Test
    void makeFriends_Should_Return_True() throws Exception {
        CatsFriends catsFriends = new CatsFriends();

        catsFriends.setFirstCatId(1L);
        catsFriends.setSecondCatId(2L);

        given(catsFriendsDAO.getById(1L)).willReturn(catsFriends);

        Cat cat = new Cat();

        cat.setId(1L);
        cat.setName("default");
        cat.setBirthdate(Timestamp.valueOf("2012-12-12 00:00:00.000000000"));
        cat.setSpecies("default");
        cat.setColor(Color.White);

        Cat cat1 = new Cat();

        cat1.setId(2L);
        cat1.setName("default");
        cat1.setBirthdate(Timestamp.valueOf("2012-12-12 00:00:00.000000000"));
        cat1.setSpecies("default");
        cat1.setColor(Color.White);

        CatsFriends returnedCatsFriends = service.makeFriends(cat, cat1);

        assertEquals(catsFriendsDAO.getById(1L).getFirstCatId(), returnedCatsFriends.getFirstCatId());
        assertEquals(catsFriendsDAO.getById(1L).getSecondCatId(), returnedCatsFriends.getSecondCatId());
    }

    @Test
    void getAllCats_Should_Return_True() {
        given(catDAO.getAll()).willReturn(new ArrayList<>());

        List<Cat> returnedList = service.getAllCats();

        assertEquals(catDAO.getAll(), returnedList);
    }

    @Test
    void getAllOwners_Should_Return_True() {
        given(ownerDAO.getAll()).willReturn(new ArrayList<>());

        List<Owner> returnedList = service.getAllOwners();

        assertEquals(ownerDAO.getAll(), returnedList);
    }
}