package am.chronograph.web.exporter;

import javax.faces.FacesException;

import org.primefaces.extensions.component.exporter.ExcelExporter;
import org.primefaces.extensions.component.exporter.Exporter;
import org.primefaces.extensions.component.exporter.ExporterFactory;
import org.primefaces.extensions.component.exporter.PDFExporter;

public class ChronoExporterFactory implements ExporterFactory {

	static public enum ExporterType {
		PDF, STOCK_ITEM_PDF, XLSX, STOCK_ITEM_XLSX
	}

	public Exporter getExporterForType(String type) {
		Exporter exporter = null;
		try {
			ExporterType exporterType = ExporterType.valueOf(type.toUpperCase());

			switch (exporterType) {
			
			case PDF:
				exporter = new PDFExporter();
				break;
			case XLSX:
				exporter = new ExcelExporter();
				break;

			default: {
				exporter = new PDFExporter();
			}

			}
		} catch (IllegalArgumentException e) {
			throw new FacesException(e);
		}

		return exporter;
	}

}
