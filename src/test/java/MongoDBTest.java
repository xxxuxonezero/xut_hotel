import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.xut.filter.Identity;
import com.xut.util.JWTUtils;
import io.jsonwebtoken.JwtBuilder;
import org.apache.commons.codec.digest.Md5Crypt;
import org.bson.Document;
import org.junit.Test;
import sun.security.provider.MD5;

import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

public class MongoDBTest {
    ObjectMapper om = new ObjectMapper();

    @Test
    public void demo() {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("problem");
        MongoCollection<Document> question = database.getCollection("question");
        Document doc = new Document("name", "onezero").append("options", Arrays.asList("A", "B", "C"));
        question.insertOne(doc);
    }

    @Test
    public void find() {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("problem");
        MongoCollection<Document> question = database.getCollection("question");
        FindIterable<Document> doc = question.find(Filters.eq("options", "C"));
        MongoCursor<Document> cursor = doc.cursor();
        while (cursor.hasNext()) {
            System.out.println(cursor.next().toJson());
        }
    }

    @Test
    public void jwtTest() throws JsonProcessingException {
        Identity identity = new Identity();
        identity.setUserName("onezero");
        identity.setUserId(1);
        identity.setType(1);
        String s = JWTUtils.generateJwt(identity);
        System.out.println(s);
//        String[] str = s.split("\\.");
//        s = str[1];
//        s= new String(Base64.getDecoder().decode(s.getBytes()));
        Identity identity1 = (Identity)JWTUtils.getObject(s, Identity.class);
        System.out.println(identity1.getType());
        System.out.println(s);
//        Object o = om.readValue(s, Object.class);
//        identity = (Identity) o;
//        System.out.println(identity.getType());
    }

}
