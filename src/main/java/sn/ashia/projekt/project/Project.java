package sn.ashia.projekt.project;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sn.ashia.projekt.persistance.AbstractEntity;
import sn.ashia.projekt.user.User;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
public class Project extends AbstractEntity {
    @NotBlank
    private String title;

    @NotNull
    private Integer durationMonths;

    private String genderMarker;

    private String priorityTheme;

    private String mainPartner;

    private Double amountFef;

    private Double amountCofinancing;

    private String type;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status = ProjectStatus.NOT_STARTED;

    @NotAudited
    @ManyToMany
    @ToString.Exclude
    private Set<User> managers = new LinkedHashSet<>();

    public void addManager(User manager) {
        managers.add(manager);
    }
}
