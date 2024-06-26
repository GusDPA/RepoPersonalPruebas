package ar.edu.utn.frba.dds.validadorDeContrasenias;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class Validador {

    public Boolean contraseniaValida(String psw){
        return this.implementaContraseniaDebil(psw) && this.cumplePoliticaDeLonguitudComplejidadYRotacion(psw) && this.implementaCredencialesPorDefectoDeSoftware(psw);
    }

    public  Boolean implementaContraseniaDebil(String contrasenia){

        // Especifica la ruta del archivo que quieres abrir
        String rutaArchivo = "../2024-tpa-mi-no-grupo-14/src/main/java/ar/edu/utn/frba/dds/validadorDeContrasenias/10-million-password-list-top-10000.txt";

        try {
            // Crea un objeto Path con la ruta del archivo
            Path archivo = Paths.get(rutaArchivo);

            // Lee todas las líneas del archivo y las guarda en una lista
            List<String> lineas = Files.readAllLines(archivo);

            // Verifica si contraseña está en el archivo
            for (String linea : lineas) {
                if(contrasenia.equals(linea)){
                    return false; // La contraseña es invalida
                }
            }

            return true; // La contraseña es valida

        } catch (IOException e) {
            // Maneja la excepción si ocurre algún problema al leer el archivo
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return true;
    }
    public Boolean cumplePoliticaDeLonguitudComplejidadYRotacion(String contra) {
        return this.largoContraseniaValido(contra) && this.complejidadContraseniValida(contra) && this.rotacionContrasenia(contra);

    }
    public Boolean largoContraseniaValido(String psw){
        Boolean longuitudValida;
        if(psw.length()<8 || psw.length() >64){
            longuitudValida=false;
        }else {
            longuitudValida=true;
        }
    return longuitudValida;
    }

    public Boolean complejidadContraseniValida(String psw){
        Pattern SPECIAL_CHAR = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]");
        Pattern DIGIT = Pattern.compile("[0-9]");
        Pattern UPPER_CASE = Pattern.compile("[A-Z]");
        Pattern LOWER_CASE = Pattern.compile("[a-z]");

        /* verificaciones de que la contraseña contenga al menos una minúscula, mayúscula, un número, un caracter espercial, no sea null o espacios en blanco
         ni tenga caracteres secuenciales */

        if (!LOWER_CASE.matcher(psw).find()) {
            return false;
        }
        if (!UPPER_CASE.matcher(psw).find()) {
            return false;
        }
        if (psw == null || psw.isBlank()) {
            return false;
        }
        if (!DIGIT.matcher(psw).find()) {
            return false;
        }
        if (!SPECIAL_CHAR.matcher(psw).find()) {
            return false;
        }
        if (psw == null || psw.isBlank()) {
            return false;
        }
        if(this.contieneCaracteresSecuenciales(psw)){
            return false;
        }
        return true;
    }

    public Boolean contieneCaracteresSecuenciales(String psw) {
            int secuenciaMaximaPermitida = 3;  // Longitud máxima de la secuencia permitida
            int secuenciaActual = 1;
            for (int i = 1; i < psw.length(); i++) {
                if (psw.charAt(i) == psw.charAt(i - 1) + 1) {
                    secuenciaActual++;
                    if (secuenciaActual > secuenciaMaximaPermitida) {
                        return true;
                    }
                } else {
                    secuenciaActual = 1;
                }
            }
            return false;
    }
    //TODO
    public Boolean rotacionContrasenia(String psw){
        return true;
    }

    public Boolean implementaCredencialesPorDefectoDeSoftware(String psw){
        final Set<String> BLACKLISTED_PASSWORDS = new HashSet<>(
                Arrays.asList("Admin", "root", "administrator", "admin.123", "system", "localroot")
        );
        if (BLACKLISTED_PASSWORDS.contains(psw.toLowerCase())) {
            return false;
        }
        return true;
    }
}