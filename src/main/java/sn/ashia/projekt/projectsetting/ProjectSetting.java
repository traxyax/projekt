package sn.ashia.projekt.projectsetting;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sn.ashia.projekt.persistance.AbstractEntity;
import sn.ashia.projekt.project.Project;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
public class ProjectSetting extends AbstractEntity {
    private ProjectCurrency currency;

    @OneToOne(mappedBy = "setting")
    private Project project;
}
