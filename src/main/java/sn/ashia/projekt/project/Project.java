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
import sn.ashia.projekt.projectcomponent.ProjectComponent;
import sn.ashia.projekt.projectrisk.ProjectRisk;
import sn.ashia.projekt.projectsetting.ProjectSetting;
import sn.ashia.projekt.user.User;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
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

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private ProjectSetting setting;

    @NotAudited
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> managers;

    @OneToMany(mappedBy = "project")
    @ToString.Exclude
    private List<ProjectRisk> risks;

    @OneToMany(mappedBy = "project")
    @ToString.Exclude
    private List<ProjectComponent> components;

    public void setStatus(ProjectStatus status) {
        this.status = Objects.requireNonNullElse(status, ProjectStatus.NOT_STARTED);
    }

    public void addManager(User manager) {
        if (managers == null) managers = new LinkedHashSet<>();
        managers.add(manager);
    }
}
