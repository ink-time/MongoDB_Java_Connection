import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

public class Alumno {

    private MongoCollection<Document> getCollection() {
        return ConexionMongoDB.getConnectionMongo().getCollection("Alumnos");
    }

    private MongoCollection<Document> getUsuariosCollection() {
        return ConexionMongoDB.getConnectionMongo().getCollection("usuarios");
    }
    public void insertUsuariosToAlumnos() {
        MongoCursor<Document> cursor = getUsuariosCollection().find().iterator();
        String nombre = "";
        String email = "";
        int edad = 0;
        boolean activo = true;
        Double saldo = 0.00;
        String telefono = "";
        String pais = "";
        while(cursor.hasNext()){
            Document doc = cursor.next();
            nombre = doc.getString("nombre");
            System.out.println(doc.getString("nombre"));
            email = doc.getString("email");
            edad = doc.getInteger("edad");
            activo = doc.getBoolean("activo");
//            if(())
            System.out.println(doc.getDouble("saldo").getClass().getSimpleName());
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            saldo = doc.getDouble("saldo");
            telefono = doc.getString("telefono");
            pais = doc.getString("pais");
            if (!findByName(nombre)){


            Document insertDoc = new Document("nombre", nombre)
                    .append("email", email)
                    .append("edad", edad)
                    .append("activo", activo)
                    .append("saldo", saldo)
                    .append("telefono", telefono)
                    .append("pais", pais);

            getCollection().insertOne(insertDoc);

            System.out.println("Alumno insertado: " + nombre);
            System.out.println();
            System.out.println();
            }
        }

        listarAlumnos();
    }
    // 1. LISTAR ALUMNOS
    public void listarAlumnos() {
        MongoCursor<Document> cursorUsers = getUsuariosCollection().find().iterator();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        System.out.println("Listado de alumnos:");
        while (cursor.hasNext()) {
            Document doc = cursor.next();
//            boolean condition = false;
//            while(cursorUsers.hasNext() && !condition){
//                if(cursorUsers.next().getString("nombre") != null){
//                    if(cursorUsers.next().getString("nombre").equals(doc.getString("nombre"))){
//                        condition = true;
//                        break;
//
//                    }
//                }
//
//            }
            if(doc.containsKey("telefono")&&doc.containsKey("pais")){
                System.out.println(
                        doc.getString("nombre") + " " +
                                doc.getString("email") + " " +
                                doc.getInteger("edad") + " " +
                                doc.getBoolean("activo") + " " +
                                doc.getDouble("saldo") + " " +
                                doc.getString("telefono") + " " +
                                doc.getString("pais")
                );
            }else{
                System.out.println(
                        doc.getString("nombre") + " " +
                                doc.getString("apellido1") + " " +
                                doc.getString("apellido2") + " " +
                                doc.getInteger("edad") + " "
                );
            }
            System.out.println();

        }
    }

    // 2. BUSCAR POR NOMBRE
    public boolean findByName(String nombre) {
        Document alumno = getCollection()
                .find(Filters.eq("nombre", nombre))
                .first();

        if (alumno != null) {
            System.out.println("El alumno ya existe: " + nombre);
            return true;
        }

        System.out.println("No existe el alumno: " + nombre);
        return false;
    }

    // 3. INSERTAR ALUMNO
    public void insertAlumno(String nombre, String apellido1, String apellido2, int edad) {

        if (findByName(nombre)) return;

        Document doc = new Document("nombre", nombre)
                .append("apellido1", apellido1)
                .append("apellido2", apellido2)
                .append("edad", edad);

        getCollection().insertOne(doc);

        System.out.println("Alumno insertado: " + nombre);
        listarAlumnos();
    }

    // 4. BORRAR ALUMNO POR NOMBRE
    public void deleteByName(String nombre) {

        long count = getCollection()
                .deleteOne(Filters.eq("nombre", nombre))
                .getDeletedCount();

        if (count > 0) {
            System.out.println("Alumno eliminado: " + nombre);
        } else {
            System.out.println("No existe el alumno: " + nombre);
        }

        listarAlumnos();
    }

    // 5. ACTUALIZAR ALUMNO
    public void actualizarAlumno(String nombreActual, String nombreNuevo, String apellido1Nuevo, String apellido2Nuevo, int edadNueva) {

        long updated = getCollection()
                .updateOne(
                        Filters.eq("nombre", nombreActual),
                        Updates.combine(
                                Updates.set("nombre", nombreNuevo),
                                Updates.set("apellido1", apellido1Nuevo),
                                Updates.set("apellido2", apellido2Nuevo),
                                Updates.set("edad", edadNueva)
                        )
                ).getModifiedCount();

        if (updated > 0) {
            System.out.println("Alumno actualizado: " + nombreActual + " → " + nombreNuevo);
        } else {
            System.out.println("No existe el alumno: " + nombreActual);
        }

        listarAlumnos();
    }
}