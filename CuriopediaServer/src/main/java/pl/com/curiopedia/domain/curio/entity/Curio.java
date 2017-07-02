package pl.com.curiopedia.domain.curio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.com.curiopedia.domain.curio.dto.CurioDTO;
import pl.com.curiopedia.domain.user.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jakub on 23.06.17.
 */
@Entity
@Table(name = "curio", uniqueConstraints = @UniqueConstraint(columnNames = {"title"}))
@ToString
@Getter
@Setter
@Builder
public class Curio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @NotNull
    private String title;

    @Column(name ="creation_date")
    private LocalDateTime creationDate;

    @Column(name ="modification_date")
    private LocalDateTime modificationDate;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "curio_tag",
            joinColumns = {@JoinColumn(name = "curio_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_name", referencedColumnName = "name")})
    private Set<Tag> tags = new HashSet<>();

    private String description;

    private String content;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Source source;

    public CurioDTO toDTO() {
        return CurioDTO.builder()
                .title(title)
                .description(description)
                .content(content)
                .creationDate(creationDate.toString())
                .modificationDate(modificationDate.toString())
                .author(author.getName())
                .category(category.getName())
                .source(source.getName())
                .build();
    }

}
