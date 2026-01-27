import com.google.gson.*;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

public class LeerAlumnoJson {
    private static MongoCollection<Document> getCollection() {
        return ConexionMongoDB.getConnectionMongo().getCollection("Alumnos");
    }
    public static void leer_insertarJsonFile(String fileName){
//  To test locally:
//  static void main(String[] args) {


     try {
            Gson gson = new Gson();
            // Aquí ocurre la deserialización: el archivo JSON se transforma en un objeto manejable desde Java.
            JsonArray alumnoJson = gson.fromJson(
                    new FileReader(fileName), JsonArray.class
            );

            // I create the variables outside the for loop so they do no get created every iteration.
            JsonObject insertObject = new JsonObject();
            String nombre = "";
            String email = "";
            int edad = 0;
            boolean activo = false;
            double saldo = 0;
            String telefono = "";
            String pais = "";
         for (int i = 0; i < alumnoJson.size(); i++) {

                insertObject = alumnoJson.get(i).getAsJsonObject();
                nombre = insertObject.get("nombre").getAsString();
                email = insertObject.get("email").getAsString();
                edad = insertObject.get("edad").getAsInt();
                activo = insertObject.get("activo").getAsBoolean();
                saldo = insertObject.get("saldo").getAsDouble();
                telefono = insertObject.get("telefono").getAsString();
                pais = insertObject.get("pais").getAsString();


                Document insertDoc = new Document("nombre", nombre)
                        .append("email", email)
                        .append("edad", edad)
                        .append("activo", activo)
                        .append("saldo", saldo)
                        .append("telefono", telefono)
                        .append("pais", pais);

//             Logging:
             System.out.println();
//             System.out.println(insertDoc);
//             System.out.println();
             // I need to create an alumno object because this way I can access the method to find by name. I could also create it here of course, but I dont think thats a good idea.
             Alumno al = new Alumno();
             if(!al.findByName(nombre)){
                 // This condition is not perfect, ideally we check for a unique field, like email? However, I am using the already created method.
                 // I create a new getCollection because the method in Alumno is private
                 getCollection().insertOne(insertDoc);
                 System.out.println("Alumno insertado correctamente!");
             }else{
                 System.out.println("El alumno introducido ya existía en la base de datos");
             }


//             OR:
//             Alumno al = new Alumno();
//             al.insertAlumno(nombre, email, edad, activo, saldo, telefono, pais);
            }



            // If I had an array of phones this would be necessary:
//            JsonArray telefonos = alumnoJson.getAsJsonArray("telefonos");
//
//            for (JsonElement t : telefonos) {
//                System.out.println("- " + t.getAsString());
//            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    }

