package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Comparator;

@Entity
public class TopicWordsMapping {
    @Id
    public Long id;

    @ManyToOne
    public Topic topic;

    public String word;

    public Float probability;

    public static final Comparator<TopicWordsMapping> probabilityComparator = new Comparator<TopicWordsMapping>() {
        @Override
        public int compare(TopicWordsMapping o1, TopicWordsMapping o2) {
            return -o1.probability.compareTo(o2.probability);
        }
    };

    public static Model.Finder<Integer, TopicWordsMapping> find = new Model.Finder<>(
            Integer.class, TopicWordsMapping.class
    );
}
