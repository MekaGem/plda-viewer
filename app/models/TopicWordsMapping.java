package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TopicWordsMapping {
    @Id
    public Long id;

    public Long topicId;
    public static final String topicIdC = "topic_id";

    public String word;

    public Float probability;
    public static final String probabilityC = "probability";
}
