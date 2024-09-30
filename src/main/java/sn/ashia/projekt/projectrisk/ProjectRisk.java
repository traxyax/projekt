package sn.ashia.projekt.projectrisk;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sn.ashia.projekt.enumeration.GlobalStatus;
import sn.ashia.projekt.persistance.AbstractEntity;
import sn.ashia.projekt.project.Project;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
public class ProjectRisk extends AbstractEntity {
    @NotEmpty
    @Column(columnDefinition = "TEXT")
    private String cause;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String measure;

    private Integer impact;

    private Integer probability;

    @Enumerated(EnumType.STRING)
    private GlobalStatus status;

    @NotNull
    @ManyToOne
    private Project project;

    public void setStatus(GlobalStatus status) {
        this.status = Objects.requireNonNullElse(status, GlobalStatus.KO);
    }

    public Long getProjectId() {
        return project.getId();
    }

    public Integer getCriticality() {
        return null;
    }
}
