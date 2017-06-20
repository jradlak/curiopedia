package pl.com.curiopedia.domain.user.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by jakub on 20.06.17.
 */
@Entity
@Table(name = "authority")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class Authority implements GrantedAuthority, Serializable {

    public final static String ROLE_ADMIN = "ROLE_ADMIN";

    public final static String ROLE_AUTHOR = "ROLE_AUTHOR";

    public final static String ROLE_GUEST = "ROLE_GUEST";

    @NotNull
    @Size(min = 0, max = 50)
    @Id
    @Column(length = 50)
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }

}
