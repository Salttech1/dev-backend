package kraheja.commons.service.impl;

import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.Invoiceheader;
import kraheja.adminexp.billing.dataentry.invoiceCreation.repository.InvoiceheaderRepository;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.service.QrCodeService;
import kraheja.commons.utils.CommonConstraints;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.Invoiceheader;
import kraheja.adminexp.billing.dataentry.invoiceCreation.repository.InvoiceheaderRepository;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.service.QrCodeService;
import kraheja.commons.utils.CommonConstraints;

@Service
public class QrCodeServiceImpl implements QrCodeService {

	@Autowired
	InvoiceheaderRepository invoiceheaderRepository;

	@Override
	public ResponseEntity<?> generateQrCode(String invoiceNo) {
		try {
			// Create a PDF document with a single page
			try (PDDocument document = new PDDocument()) {
				PDPage page = new PDPage();
				document.addPage(page);

				Invoiceheader invoiceheader = invoiceheaderRepository
						.findByInvoiceheaderCK_InvhInvoiceno(invoiceNo.trim());

				if (Objects.isNull(invoiceheader)) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Invoice Number does not exist").build());
				}

				String qrString = invoiceheader.getInvhQrcode();

				if (StringUtils.isEmpty(qrString)) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("QR Code text must not be empty.").build());
				}

				// Create QR Code writer
				QRCodeWriter qrCodeWriter = new QRCodeWriter();

				// Set height and width as a percentage of the page size
				float heightPercentage = 100f;
				float widthPercentage = 100f;

				// Calculate actual height and width based on percentage
				float height = page.getMediaBox().getHeight() * (heightPercentage / 100f);
				float width = page.getMediaBox().getWidth() * (widthPercentage / 100f);

				BufferedImage image = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_RGB);
				int white = 255 << 16 | 255 << 8 | 255;
				int black = 0;
				BitMatrix bitMatrix = qrCodeWriter.encode(qrString, BarcodeFormat.QR_CODE, (int) width, (int) height);

				// Fill image with QR Code data
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						image.setRGB(x, y, bitMatrix.get(x, y) ? black : white);
					}
				}

				// Convert BufferedImage to PDImageXObject
				PDImageXObject pdImage = LosslessFactory.createFromImage(document, image);

				try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
					contentStream.drawImage(pdImage, 0, 0, width, height);
				}

				// Save the PDF content to a ByteArrayOutputStream
				ByteArrayOutputStream pdfBaos = new ByteArrayOutputStream();
				document.save(pdfBaos);

				return getResponseStream(pdfBaos, "TempFile.pdf", MediaType.APPLICATION_PDF_VALUE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message("QR Code PDF creation unsuccessful.").build());
		}
	}

	private ResponseEntity<?> getResponseStream(ByteArrayOutputStream data, String filename, String contentType) {
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						CommonConstraints.INSTANCE.ATTACHMENT_FILENAME_STRING.concat(filename))
				.header(HttpHeaders.CONTENT_TYPE, contentType)
				.body(new InputStreamResource(new ByteArrayInputStream(data.toByteArray())));
	}
}
