import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
public class LeerAlumnoJson {
//    public static void leerJsonFile(){
static void main(String[] args) {


     try {
            Gson gson = new Gson();
            // Aquí ocurre la deserialización: el archivo JSON se transforma en un objeto manejable desde Java.
            JsonObject alumno = gson.fromJson(
                    new FileReader("json_files/alumnos.json"), JsonObject.class
            );



            String nombre = alumno.get("nombre").getAsString();
            String email = alumno.get("email").getAsString();
            int edad = alumno.get("edad").getAsInt();
            boolean activo = alumno.get("activo").getAsBoolean();
            double saldo = alumno.get("saldo").getAsDouble();
            String telefono = alumno.get("telefono").getAsString();
            String pais = alumno.get("pais").getAsString();

            // If I had an array of phones this would be necessary:
//            JsonArray telefonos = alumno.getAsJsonArray("telefonos");
//
//            for (JsonElement t : telefonos) {
//                System.out.println("- " + t.getAsString());
//            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    }

