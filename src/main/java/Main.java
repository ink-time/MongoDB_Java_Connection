public class Main {
    static void main() {
        try {
            ConexionMongoDB.getConnectionMongo();
            System.out.println("Conexión correcta a MongoDB.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        Alumno alumno = new Alumno();

        // Llama al metodo para ver los alumnos
        alumno.listarAlumnos();
        // Insertar Alumno
        alumno.insertAlumno("ana", "Vázquez", "Guerrero", 25);
        alumno.insertUsuariosToAlumnos();
        LeerAlumnoJson.leer_insertarJsonFile("json_files/alumnos.json");
        // Actualizar Alumno
//        alumno.actualizarAlumno("Mónica", "Mónica", "Blásquez", "Güera", 20);
//        // Borrar Alumno
//        alumno.deleteByName("ana");
    }
}
