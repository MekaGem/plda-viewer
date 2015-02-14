package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Email {
    @Id
    public Long id;

    public String name;
    public static final String nameC = "name";

    @Column(columnDefinition = "MEDIUMTEXT")
    public String content;
}
