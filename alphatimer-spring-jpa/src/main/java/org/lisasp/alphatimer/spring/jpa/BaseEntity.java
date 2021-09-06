package org.lisasp.alphatimer.spring.jpa;

import lombok.Getter;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class BaseEntity implements Persistable<String> {

    protected BaseEntity() {
        this(null);
    }

    protected BaseEntity(String id) {
        this.id = id == null ? UUID.randomUUID().toString() : id;
    }

    @Id
    @Column(nullable = false)
    private String id;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VersionedBaseEntity)) {
            return false;
        }
        BaseEntity entity = (BaseEntity)o;
        if ((id == null) != (entity.getId() == null)) {
            return false;
        }
        if (id != null && !id.equals(entity.getId())) {
            return false;
        }
        return true;
    }
}
