package dao.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ownerscats", schema = "public", catalog = "postgres")
public class OwnersCats {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "owner_id")
    private long ownerId;
    @Basic
    @Column(name = "cat_id")
    private long catId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getCatId() {
        return catId;
    }

    public void setCatId(long catId) {
        this.catId = catId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnersCats that = (OwnersCats) o;
        return id == that.id && ownerId == that.ownerId && catId == that.catId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerId, catId);
    }
}
