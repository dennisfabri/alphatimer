package org.lisasp.alphatimer.spring.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@NoArgsConstructor
@MappedSuperclass
@Getter
public abstract class VersionedBaseEntity extends BaseEntity {

    protected VersionedBaseEntity(String id) {
        super(id);
    }

    @Version
    @Column(nullable = false)
    private long version;

    @Column(nullable = false)
    private LocalDateTime lastModification;

    @Override
    public boolean isNew() {
        return version < 1;
    }

    @PrePersist
    @PreUpdate
    private void beforeSave() {
        version++;
        lastModification = LocalDateTime.now(ZoneOffset.UTC);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VersionedBaseEntity)) {
            return false;
        }
        VersionedBaseEntity entity = (VersionedBaseEntity)o;
        if (version != entity.getVersion()) {
            return  false;
        }
        if ((lastModification == null) != (entity.getLastModification() == null)) {
            return false;
        }
        if (lastModification != null && !lastModification.equals(entity.getLastModification())) {
            return false;
        }
        return super.equals(o);
    }
}
