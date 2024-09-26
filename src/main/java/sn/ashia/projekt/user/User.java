package sn.ashia.projekt.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sn.ashia.projekt.persistance.AbstractEntity;
import sn.ashia.projekt.project.Project;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User extends AbstractEntity {
    @Email
    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotEmpty
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "managers")
    @ToString.Exclude
    private Set<Project> projects = new LinkedHashSet<>();

    public User(String email, Set<UserRole> roles) {
        this.email = email;
        this.roles = roles;
    }

    public String getName() {
        String name = email.split("@")[0];
        name = String.join(" ", name.split("\\."));
        name = name.toUpperCase();
        return name;
    }
}
