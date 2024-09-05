package InitialProyect.InitialProyect.Services;

import net.sourceforge.tess4j.TesseractException;

public interface IIdScannerService {
    Long ScanDocumentNumber(String base64) throws TesseractException;
}
