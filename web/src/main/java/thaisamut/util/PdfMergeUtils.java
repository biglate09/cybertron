
package thaisamut.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.pdfbox.util.PDFMergerUtility;

/**
 * @author kittipong.su
 */
public class PdfMergeUtils {
	
	public static ByteArrayInputStream merge(InputStream ...is) throws Exception
	{
		ByteArrayOutputStream bos = null;
		
		try 
		{
			PDFMergerUtility mg = new PDFMergerUtility();
			
			for(InputStream o:is)
			{
				mg.addSource(o);
			}
			
			bos = new ByteArrayOutputStream();
			mg.setDestinationStream(bos);
	    	mg.mergeDocuments();
	    	
			return new ByteArrayInputStream(bos.toByteArray());
		} finally {
			if(null != bos) bos.close();
		}
	}
}
