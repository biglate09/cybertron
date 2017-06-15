package thaisamut.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;


public class PdfRemoveBlankPageUtils {
	private static final Logger LOG = LoggerFactory.getLogger(PdfRemoveBlankPageUtils.class);
	 // value where we can consider that this is a blank image
    // can be much higher or lower depending of what is considered as a blank page
    public static final int BLANK_THRESHOLD = 160;

    public static ByteArrayInputStream removeBlankPdfPages(InputStream source)throws IOException, DocumentException{
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	removeBlankPdfPages(source, baos);
    	return new ByteArrayInputStream(baos.toByteArray());
    }
    public static void removeBlankPdfPages(InputStream source, OutputStream destination)
            throws IOException, DocumentException
        {
    	PdfReader r = null;
        Document document = null;
        PdfCopy writer = null;
        try {
            r = new PdfReader(source);
            // itext 5 > 
            document = new Document(r.getPageSizeWithRotation(1));
            writer = new PdfCopy(document, destination);
            document.open();
            PdfImportedPage page = null;
            if(LOG.isDebugEnabled()){
    			LOG.debug("page pdf="+r.getNumberOfPages());
    		}
            for (int i=1; i<=r.getNumberOfPages(); i++) {
                // first check, examine the resource dictionary for /Font or
                // /XObject keys.  If either are present -> not blank.
                PdfDictionary pageDict = r.getPageN(i);
                PdfDictionary resDict = (PdfDictionary) pageDict.get( PdfName.RESOURCES );
                boolean noFontsOrImages = true;
                if (resDict != null) {
                  noFontsOrImages = resDict.get( PdfName.FONT ) == null &&
                                    resDict.get( PdfName.XOBJECT ) == null;
                }
                if(LOG.isDebugEnabled()){
        			LOG.debug(i + " noFontsOrImages " + noFontsOrImages);
        		}
                if (!noFontsOrImages) {
                    byte bContent [] = r.getPageContent(i);
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    bs.write(bContent);
                    if(LOG.isDebugEnabled()){
            			LOG.debug(i + bs.size() + " > BLANK_THRESHOLD " +  (bs.size() > BLANK_THRESHOLD));
            		}
                    if (bs.size() > BLANK_THRESHOLD) {
                        page = writer.getImportedPage(r, i);
                        writer.addPage(page);
                    }
                }
            }
        }finally {
            if (document != null) document.close();
            if (writer != null) writer.close();
            if (r != null) r.close();
        }
     }

}
