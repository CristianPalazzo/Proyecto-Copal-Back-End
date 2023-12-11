package utn.t2.s1.gestionsocios.servicios;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import utn.t2.s1.gestionsocios.repositorios.DepartamentoRepo;
import utn.t2.s1.gestionsocios.repositorios.SocioRepo;
import utn.t2.s1.gestionsocios.repositorios.SubDepartamentoRepo;

import java.io.*;
import java.util.UUID;


@Service
public class LogoServicio {

    @Autowired
    private DepartamentoRepo departamentoRepo;

    @Autowired
    private SubDepartamentoRepo subDepartamentoRepo;
    @Autowired
    private SocioRepo socioRepo;


    public void showLogo(String imageName, HttpServletResponse response) throws IOException {

        // Obtén la ruta completa del archivo de la carpeta "uploads"
        String uploadsDirectory = "uploads/";
        String filePath = uploadsDirectory + imageName;
        // Abre el archivo de imagen
        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);
        // Configura la respuesta HTTP para la imagen
        response.setContentType("image/jpeg"); // Cambia el tipo MIME según el tipo de imagen
        response.setContentLength((int) file.length());
        // Copia el contenido del archivo al flujo de salida de la respuesta
        OutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outputStream.close();
        //http://localhost:8080/images/miimagen.jpg

    };


    public String save(MultipartFile logo) throws IOException {

        String imagePath = "./uploads/";

        // Guardar la imagen en el directorio
        String fileName = UUID.randomUUID().toString() + ".jpg"; // Generar un nombre único para el archivo
        File file = new File(imagePath + fileName); // Crear un archivo con el nombre y la ruta
        FileOutputStream fos = new FileOutputStream(file); // Crear un flujo de salida para escribir el archivo
        fos.write(logo.getBytes()); // Escribir el contenido de la imagen en el archivo
        fos.close(); // Cerrar el flujo de salida

        // Crear la URL de la imagen
        String url = "/logo/" + fileName;

        return url;
    };


    public void deletePorSocio(long id){

        socioRepo.findById(id).ifPresent(socio -> {

            String url = socio.getLogo();


            if(socio.getLogo() == null){
                return;
            }

//            url = socio.getLogo();

            // Encontrar el índice de "logo/"
            int i = url.indexOf("logo/");
            // Extraer la subcadena desde el índice i + 5 (para saltar el "logo/")
            String sub = url.substring(i + 5);


            File file = new File("./uploads/" + sub);
            file.delete();

        });

    };

    public void deletePorDepartamento(long id){

        departamentoRepo.findById(id).ifPresent(departamento -> {

            if(departamento.getLogo() == null){
                return;
            }

            String url = departamento.getLogo();

            // Encontrar el índice de "logo/"
            int i = url.indexOf("logo/");
            // Extraer la subcadena desde el índice i + 5 (para saltar el "logo/")
            String sub = url.substring(i + 5);

            File file = new File("./uploads/" + sub);
            file.delete();

        });

    };


    public void deletePorSubDepartamento(long id){

        subDepartamentoRepo.findById(id).ifPresent(subDepartamento -> {

            if(subDepartamento.getLogo() == null){
                return;
            }

            String url = subDepartamento.getLogo();

            // Encontrar el índice de "logo/"
            int i = url.indexOf("logo/");
            // Extraer la subcadena desde el índice i + 5 (para saltar el "logo/")
            String sub = url.substring(i + 5);

            File file = new File("./uploads/" + sub);
            file.delete();

        });

    };


}
