package ca.codeless;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Service;

@Service
public class MongoRepo {

    private MongoCollection<Document> collection;

    public MongoRepo() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("nestDb");
        collection = database.getCollection("nestdata");
    }

    public void saveData(Document document) {
        this.collection.insertOne(document);
    }

}
