package InitialProyect.InitialProyect.Services.Imp;

import InitialProyect.InitialProyect.Services.IIdScannerService;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IdScannerService implements IIdScannerService {

    private static final String TESSDATA_DIR = System.getProperty("java.io.tmpdir") + "/tessdata";
    private static final String LANGUAGE = "spa";

    @Override
    public Long ScanDocumentNumber(String base64) throws TesseractException {
        try {
            // Extraer los datos del idioma antes de usar Tesseract
            extractTrainedData();
        } catch (IOException e) {
            throw new RuntimeException("No se pudo extraer el archivo de datos del idioma", e);
        }

        ITesseract tesseract = new Tesseract();
        tesseract.setLanguage(LANGUAGE);
        tesseract.setDatapath(TESSDATA_DIR);
        BufferedImage image = decodeBase64ToImage(base64);

        String imageProcessed = tesseract.doOCR(image);

        return DocumentNumberExtract(imageProcessed);
    }

    private static BufferedImage decodeBase64ToImage(String base64Image) {
        try {
            // Elimina el prefijo "data:image/jpeg;base64," si está presente
            if (base64Image.contains(",")) {
                base64Image = base64Image.split(",")[1];
            }

            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            return ImageIO.read(bis);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static long DocumentNumberExtract(String texto) {
        String regex = "\\b\\d{2,3}(?:\\.\\d{3}){2,3}\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(texto);

        if (matcher.find()) {
            String documentNumberStr = matcher.group();
            documentNumberStr = documentNumberStr.replace(".", "");

            try {
                return Long.parseLong(documentNumberStr);
            } catch (NumberFormatException e) {
                return -1;
            }
        } else {
            return -1;
        }
    }

    private static void extractTrainedData() throws IOException {
        // Crear el directorio temporal para los datos de Tesseract
        File tessDataDir = new File(TESSDATA_DIR);
        if (!tessDataDir.exists()) {
            tessDataDir.mkdirs();
        }

        // Extraer el archivo de datos del idioma desde el classpath
        InputStream inputStream = IdScannerService.class.getResourceAsStream("/LibraryDependency/" + LANGUAGE + ".traineddata");
        if (inputStream == null) {
            throw new IOException("Archivo de datos del idioma no encontrado en el classpath");
        }

        File outputFile = new File(tessDataDir, LANGUAGE + ".traineddata");
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        }

        // Verifica si el archivo se ha copiado correctamente
        if (!outputFile.exists()) {
            throw new IOException("El archivo de datos del idioma no se ha copiado correctamente");
        }

        System.out.println("Archivo de datos del idioma copiado a: " + outputFile.getAbsolutePath());
    }
}
