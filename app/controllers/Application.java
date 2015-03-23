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

        List<Topic> topics = new ArrayList<>();
        for (EmailTopicsMapping mapping : mappings) {
            topics.add(mapping.topic);
        }

        List<String> formattedProbabilities = new ArrayList<>();
        DecimalFormat format = new DecimalFormat("#0.00");
        for (EmailTopicsMapping mapping : mappings) {
            formattedProbabilities.add(format.format(mapping.probability * 100) + "%");
        }

        return ok(views.html.email.render(
                email,
                JavaConversions.asScalaBuffer(mappings),
                JavaConversions.asScalaBuffer(topics),
                JavaConversions.asScalaBuffer(formattedProbabilities)));
    }

    public static Result viewTopic(long topicId, int page) {
        final int PAGE_SIZE = 20;
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
                , PAGE_SIZE * page + 1
                , PAGE_SIZE));
    }
//
//    public static Result viewTopic(String topicName) {
//        MongoClient client = getMongoClient();
//        DB db = client.getDB("plda");
//        DBCollection topics = db.getCollection("topics");
//
//        BasicDBObject query = new BasicDBObject("name", topicName);
//        DBObject answer = null;
//        try (Cursor cursor = topics.find(query)) {
//            if (cursor.hasNext()) {
//                answer = cursor.next();
//            }
//        }
//
//        class WordProb implements Comparable<WordProb> {
//            String word;
//            double prob;
//
//            WordProb(String word, double prob) {
//                this.word = word;
//                this.prob = prob;
//            }
//
//            @Override
//            public int compareTo(WordProb o) {
//                if (prob != o.prob) return -Double.compare(prob, o.prob);
//                if (!word.equals(o.word)) return word.compareTo(o.word);
//                return 0;
//            }
//        }
//
//        List<WordProb> probs = new ArrayList<>();
//        List<String> words = new ArrayList<>();
//        List<String> wordProbs = new ArrayList<>();
//        double coverage = 0;
//        if (answer != null) {
//            double total = (Double) answer.get("total");
//            List<Object> wordsArray = (BasicBSONList) answer.get("words");
//
//            for (Object wordInfo : wordsArray) {
//                BasicBSONObject info = (BasicBSONObject) wordInfo;
//                String word = (String) info.get("word");
//                double prob = (Double) info.get("prob");
//                probs.add(new WordProb(word, prob));
//            }
//            Collections.sort(probs);
//
//            final double cf = 100.0 / total;
//            DecimalFormat probFormat = new DecimalFormat("00.00");
//            for (int index = 0; index < Math.min(100, probs.size()); ++index) {
//                words.add(String.valueOf(probs.get(index).word));
//                wordProbs.add(probFormat.format(probs.get(index).prob * cf));
//                coverage += probs.get(index).prob * cf;
//            }
//        }
//
//        return(ok(topic.render(topicName, new DecimalFormat("00").format(coverage),
//                               words.toArray(new String[words.size()]),
//                               wordProbs.toArray(new String[wordProbs.size()]))));
//    }
}
