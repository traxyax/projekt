package sn.ashia.projekt.projectcomponent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
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
public class ProjectComponent extends AbstractEntity {
    @NotNull
    private Integer number;

    @NotEmpty
    @Column(columnDefinition = "TEXT")
    private String objective;

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
}
