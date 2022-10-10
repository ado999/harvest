package pl.azebrow.harvest.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<Account> accounts;

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("Role(id=%s, name=%s)", id, name);
    }

    //test only
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
