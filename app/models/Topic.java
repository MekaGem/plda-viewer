package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Topic {
    @Id
    public Long id;

    public String name;

    @OneToMany(mappedBy = "topic")
    public List<EmailTopicsMapping> emailTopicsMappings;

    @OneToMany(mappedBy = "topic")
    public List<TopicWordsMapping> topicWordsMappings;

    public static Model.Finder<Integer, Topic> find = new Model.Finder<>(
        Integer.class, Topic.class
    );
}
