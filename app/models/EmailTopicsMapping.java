package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Comparator;

@Entity
public class EmailTopicsMapping {
    @Id
    public Long id;

    @ManyToOne
    public Email email;

    @ManyToOne
    public Topic topic;

    public Float probability;

    public static final Comparator<EmailTopicsMapping> probabilityComparator = new Comparator<EmailTopicsMapping>() {
        @Override
        public int compare(EmailTopicsMapping o1, EmailTopicsMapping o2) {
            return (-o1.probability.compareTo(o2.probability));
        }
    };

    public static Model.Finder<Integer, EmailTopicsMapping> find = new Model.Finder<>(
            Integer.class, EmailTopicsMapping.class
    );
}
