import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class Usuario {
    private MongoCollection<Document> getCollection() {
        return ConexionMongoDB.getConnectionMongo().getCollection("usuarios");
    }


}
