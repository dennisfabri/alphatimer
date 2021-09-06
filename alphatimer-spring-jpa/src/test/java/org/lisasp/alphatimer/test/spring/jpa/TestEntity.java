package org.lisasp.alphatimer.test.spring.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lisasp.alphatimer.spring.jpa.VersionedBaseEntity;

import javax.persistence.Entity;

@Entity()
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestEntity extends VersionedBaseEntity {
    private String name;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VersionedBaseEntity)) {
            return false;
        }
        TestEntity entity = (TestEntity)o;
        if ((name == null) != (entity.getName() == null)) {
            return false;
        }
        if (name != null && !name.equals(entity.getName())) {
            return false;
        }
        return super.equals(o);
    }

    public String toString() {
        return String.format("TestEntity(%s, %d, %s, %s)", getId(), getVersion(), getLastModification() == null ? "<null>" : getLastModification().toString(), getName());
    }
}
