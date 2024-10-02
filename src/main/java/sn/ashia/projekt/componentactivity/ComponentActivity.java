package sn.ashia.projekt.componentactivity;

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
import sn.ashia.projekt.projectcomponent.ProjectComponent;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "component_activity_number_project_component_id_uk",
                columnNames = {"number", "component_id"
                })
})
public class ComponentActivity extends AbstractEntity {
    @NotNull
    private Integer number;

    @NotEmpty
    @Column(columnDefinition = "TEXT")
    private String objective;

    @Enumerated(EnumType.STRING)
    private GlobalStatus status;

    @NotNull
    @ManyToOne
    private ProjectComponent component;

    public void setStatus(GlobalStatus status) {
        this.status = Objects.requireNonNullElse(status, GlobalStatus.KO);
    }

    public Long getComponentId() {
        return component.getId();
    }
}
