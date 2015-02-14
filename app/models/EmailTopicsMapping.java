package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmailTopicsMapping {
    @Id
    public Long id;

    public Long emailId;
    public static final String emailIdC = "email_id";

    public Long topicId;
    public static final String topicIdC = "topic_id";

    public Float probability;
}
