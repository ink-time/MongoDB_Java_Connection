import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;public class LeerClientJson {
    public static void main(String[] args) {

        try {
            Gson gson = new Gson();
            // Aquí ocurre la deserialización: el archivo JSON se transforma en un objeto manejable desde Java.
            JsonObject cliente = gson.fromJson(
                    new FileReader("cliente.json"), JsonObject.class
            );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
