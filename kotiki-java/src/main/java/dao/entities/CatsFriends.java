package dao.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "catsfriends", schema = "public", catalog = "postgres")
public class CatsFriends {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "first_cat_id")
    private long firstCatId;
    @Basic
    @Column(name = "second_cat_id")
    private long secondCatId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFirstCatId() {
        return firstCatId;
    }

    public void setFirstCatId(long firstCatId) {
        this.firstCatId = firstCatId;
    }

    public long getSecondCatId() {
        return secondCatId;
    }

    public void setSecondCatId(long secondCatId) {
        this.secondCatId = secondCatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatsFriends that = (CatsFriends) o;
        return id == that.id && firstCatId == that.firstCatId && secondCatId == that.secondCatId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstCatId, secondCatId);
    }
}
