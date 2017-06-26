package pl.com.curiopedia.domain.curio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by jakub on 23.06.17.
 */
@Entity
@Table(name = "source", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@ToString
@Getter
@Setter
public class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @OneToMany(mappedBy = "source", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Curio> curios;

    private String name;

    private String description;

    @Column(name ="date")
    private LocalDate date;

    public Source(String name, String description, LocalDate date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }
}
