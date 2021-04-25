package de.dennisfabri.alphatimer.messagesstorage;

import lombok.Data;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
public class AbstractBaseEntity implements Persistable<String> {

    protected AbstractBaseEntity() {
        this(UUID.randomUUID().toString());
    }

    protected AbstractBaseEntity(String id) {
        this.id = id == null ? UUID.randomUUID().toString() : id;
    }

    @Id
    @Column(nullable = false)
    private String id;

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
    private void beforeInsert() {
        version++;
        lastModification = LocalDateTime.now();
    }
}
