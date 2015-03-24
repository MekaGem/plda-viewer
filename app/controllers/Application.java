package controllers;

import com.avaje.ebean.Ebean;
import models.Email;
import models.EmailTopicsMapping;
import models.Topic;
import models.TopicWordsMapping;
import play.mvc.*;
import scala.collection.JavaConversions;
import views.html.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Application extends Controller {
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result viewEmail(long emailId) {
        Email email = Ebean.find(Email.class, emailId);
        if (email == null) {
            return notFound("Email with this id does not exist");
        }
        List<EmailTopicsMapping> mappings = email.emailTopicsMappings;
        mappings.sort(EmailTopicsMapping.probabilityComparator);

        List<Topic> topics = mappings.stream()
                .map(mapping -> mapping.topic)
                .collect(Collectors.toList());

        DecimalFormat format = new DecimalFormat("#0.00");
        List<String> formattedProbabilities = mappings.stream()
                .map(mapping -> format.format(mapping.probability * 100) + "%")
                .collect(Collectors.toList());

        return ok(views.html.email.render(
                email,
                JavaConversions.asScalaBuffer(mappings),
                JavaConversions.asScalaBuffer(topics),
                JavaConversions.asScalaBuffer(formattedProbabilities)));
    }

    private static final int DEFAULT_PAGE_SIZE = 20;

    public static Result viewTopic(long topicId, int pageNumber, int pageSize) {
        pageNumber = Math.max(0, pageNumber - 1);
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        Topic topic = Ebean.find(Topic.class, topicId);
        if (topic == null) {
            return notFound("Topic with this id does not exist");
        }

        List<TopicWordsMapping> mappings = topic.topicWordsMappings;
        mappings.sort(TopicWordsMapping.probabilityComparator);

        List<String> probabilities = new ArrayList<>();
        double coverage = 0;
        DecimalFormat format = new DecimalFormat("#0.00");
        for (int index = 0; index < Math.min(20, mappings.size()); ++index) {
            probabilities.add(format.format(mappings.get(index).probability * 100) + "%");
            coverage += mappings.get(index).probability * 100;
        }

        List<EmailTopicsMapping> emailMappings = topic.emailTopicsMappings;
        emailMappings.sort(EmailTopicsMapping.probabilityComparator);
        emailMappings = emailMappings.subList(pageNumber * pageSize, (pageNumber + 1) * pageSize);

        List<Email> emails = new ArrayList<>();
        List<String> emailProbabilities = new ArrayList<>();
        for (EmailTopicsMapping mapping : emailMappings) {
            emails.add(mapping.email);
            emailProbabilities.add(format.format(mapping.probability * 100) + "%");
        }

        return ok(views.html.topic.render(topic
                , JavaConversions.asScalaBuffer(mappings)
                , JavaConversions.asScalaBuffer(probabilities)
                , format.format(coverage) + "%"
                , JavaConversions.asScalaBuffer(emails)
                , JavaConversions.asScalaBuffer(emailMappings)
                , JavaConversions.asScalaBuffer(emailProbabilities)
                , pageSize * pageNumber + 1
                , pageSize));
    }

    public static Result initTopicNames() {
        List<Topic> topics = Ebean.find(Topic.class).findList();
        for (Topic topic : topics) {
            topic.name = String.valueOf(topic.id);
            Ebean.update(topic);
        }
        return ok();
    }
}
