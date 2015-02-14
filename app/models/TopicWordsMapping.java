package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TopicWordsMapping {
    @Id
    public Long id;

    public Long topicId;

    public String word;

    public Float probability;
}
