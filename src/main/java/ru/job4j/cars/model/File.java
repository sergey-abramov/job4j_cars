package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue()
    @EqualsAndHashCode.Include
    private int id;

    private String name;

    private String path;
    @Column(name = "post_id")
    private int postId;
}
