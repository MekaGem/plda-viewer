package controllers;

import com.avaje.ebean.Ebean;
import models.Email;
import models.EmailTopicsMapping;
import models.Topic;
import play.mvc.*;
import views.html.*;

import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result viewEmail(String filename) {
        Email email = Ebean.find(Email.class).where().eq(Email.nameC, filename).findUnique();
        List<EmailTopicsMapping> mapping = Ebean.find(EmailTopicsMapping.class)
                .where().eq(EmailTopicsMapping.emailIdC, email.id).orderBy(EmailTopicsMapping.topicIdC).findList();

        List<Topic> topics = new ArrayList<>();
        for (EmailTopicsMapping map : mapping) {
            topics.add(Ebean.find(Topic.class, map.topicId));
        }
        System.err.println(topics.size());
        return ok(email.content);

//        class TopicProb implements Comparable<TopicProb> {
//            int topic;
//            double prob;
//
//            TopicProb(int topic, double prob) {
//                this.topic = topic;
//                this.prob = prob;
//            }
//
//            @Override
//            public int compareTo(TopicProb o) {
//                if (prob != o.prob) return -Double.compare(prob, o.prob);
//                if (topic != o.topic) return Integer.compare(topic, o.topic);
//                return 0;
//            }
//        }
//        List<TopicProb> probs = new ArrayList<>();
//        List<String> topicNames = new ArrayList<>();
//        List<String> topicProbs = new ArrayList<>();
//
//        String content = "NO EMAIL WITH NAME: " + filename;
//        if (answer != null) {
//            content = (String) answer.get("content");
//
//            BasicBSONList topics = (BasicBSONList) answer.get("topics");
//            int topicIndex = 0;
//            double sum = 0;
//            for (Object object : topics) {
//                double topicProb = (Double) object;
//                sum += topicProb;
//                probs.add(new TopicProb(topicIndex, topicProb));
//                topicIndex++;
//            }
//            Collections.sort(probs);
//
//            final double cf = 100.0 / sum;
//            DecimalFormat probFormat = new DecimalFormat("00.00");
//            for (int index = 0; index < Math.min(3, probs.size()); ++index) {
//                topicNames.add(String.valueOf(probs.get(index).topic));
//                topicProbs.add(probFormat.format(probs.get(index).prob * cf));
//            }
//        }
//
//        return ok(email.render(topicNames.toArray(new String[topicNames.size()]),
//                               topicProbs.toArray(new String[topicProbs.size()]),
//                               content));
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

    public static Result bstest() {
        Email email = Ebean.find(Email.class).where().like("name", "%me%").findUnique();
        System.err.println(email.name);
        return(ok(bstest.render()));
    }
}
