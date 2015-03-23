package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Email {
    @Id
    public Long id;

    public String name;

    @Column(columnDefinition = "MEDIUMTEXT")
    public String content;

    @OneToMany(mappedBy = "email")
    public List<EmailTopicsMapping> emailTopicsMappings;

    public static Model.Finder<Integer, Email> find = new Model.Finder<>(
            Integer.class, Email.class
    );
}
