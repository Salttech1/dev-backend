package kraheja.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataConsolidateFunction;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotField;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ExcelUtils {

	INSTANCE;

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

//	public List<DataFromUser> getUsersFromResultSet(ResultSet resultSet) throws SQLException {
//	    List<DataFromUser> users = new ArrayList<>();
//	    while (resultSet.next()) {
//	        users.add(mapRowToUser(resultSet));
//	    }
//	    return users;
//	}

	public File exportPivoteTable(String sqlQuery, String dbURl, String dbUsername, String dbPassword) {
		try (Connection connection = DriverManager.getConnection(dbURl, dbUsername, dbPassword)) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlQuery);

			LOGGER.info("result :: {}", result);

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Sheet 1");

			writeHeaderLineForPivote(result, sheet);
			writeDataLinesForPivote(result, workbook, sheet);

			File excelFile = File.createTempFile("KRAHJEA1", CommonConstraints.INSTANCE.XLSX_EXTENSION);
			String FileName = excelFile.getName();

			LOGGER.info("TempFile :: {}", excelFile);

			FileOutputStream outputStream = new FileOutputStream(excelFile);
			workbook.write(outputStream);

			workbook.close();
			statement.close();
			outputStream.close();

			// create pivote table
			// FileInputStream input_document=new FileInputStream(new
			// File("C:/Users/sandesh.c/AppData/Local/Temp/Sandesh_test.xlsx" ));
			File filePathsource = new File("C:/Users/sandesh.c/AppData/Local/Temp/Sandesh_test.xlsx");
			FileInputStream input_document = new FileInputStream(filePathsource);

			XSSFWorkbook workbook1 = (XSSFWorkbook) WorkbookFactory.create(input_document);
			XSSFSheet sheet1 = workbook1.getSheetAt(0);
			AreaReference source = new AreaReference("A1:L2712", SpreadsheetVersion.EXCEL2007);
			CellReference position = new CellReference("N1");
			// XSSFPivotTable pivotTable = sheet1.createPivotTable(source, position);

			// added to create sheet for pivote table
			XSSFSheet sheet2 = workbook1.createSheet("pivote Table");

			AreaReference source1 = new AreaReference(new CellReference(sheet1.getSheetName(), 0, 0, true, true),
					new CellReference(sheet1.getSheetName(), 10, 1, true, true), SpreadsheetVersion.EXCEL2007);

			CellReference position1 = new CellReference(sheet2.getSheetName(), 0, 0, true, true);

			XSSFPivotTable pivotTable = sheet2.createPivotTable(source, position1, sheet1);
			
			pivotTable.addReportFilter(0);

			// pivotTable.addRowLabel(1);
			pivotTable.addRowLabel(2);
			pivotTable.addRowLabel(3);
			pivotTable.addRowLabel(4);
			pivotTable.addRowLabel(6);
			pivotTable.addRowLabel(8);
			pivotTable.addRowLabel(9);
			pivotTable.addRowLabel(10);

			pivotTable.addColLabel(5);
			pivotTable.addDataColumn(7, true);
			pivotTable.addColumnLabel(DataConsolidateFunction.SUM, 7);
			
			//pivotTable.getCTPivotTableDefinition().getPivotTableStyleInfo().setName("PivotStyleDark7");
			pivotTable.getCTPivotTableDefinition().setCompact(false);
			pivotTable.getCTPivotTableDefinition().setCompactData(false);
			pivotTable.getCTPivotTableDefinition().setGridDropZones(true);
			
			pivotTable.getCTPivotTableDefinition().setOutline(false);
			
			Integer i1=0;
			
			pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(0).setDefaultSubtotal(false);
			pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(1).setDefaultSubtotal(false);
			pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(2).setDefaultSubtotal(false);
			pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(3).setDefaultSubtotal(false);
			pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(4).setDefaultSubtotal(false);
			pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(5).setDefaultSubtotal(false);
			pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(6).setDefaultSubtotal(false);
			pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(7).setDefaultSubtotal(false);
			pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(8).setDefaultSubtotal(false);
			pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(9).setDefaultSubtotal(false);
			pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(10).setDefaultSubtotal(false);
			pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(11).setDefaultSubtotal(false);
			
			for (org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotField pivotField : pivotTable
					.getCTPivotTableDefinition().getPivotFields().getPivotFieldList()) {
				
				pivotField.setCompact(false);
				pivotField.setOutline(false);
//				boolean test = false;
//				test = pivotField.getDefaultSubtotal();
//				if (test == true) {
//					//pivotField.unsetDefaultSubtotal();
//					pivotField.setDefaultSubtotal(false);
//					pivotField.setSumSubtotal(false);
//					pivotField.setCountASubtotal(false);
//					pivotField.setAvgSubtotal(false);
//					pivotField.setMaxSubtotal(false);
//					pivotField.setMinSubtotal(false);
//					pivotField.setProductSubtotal(false);
//					pivotField.setCountSubtotal(false);
//					pivotField.setStdDevSubtotal(false);
//					pivotField.setStdDevPSubtotal(false);
//					pivotField.setVarSubtotal(false);
//					pivotField.setVarPSubtotal(false);
//				}
				
			}
			
			// Disable subtotals for the field with index 1 (Name)

//			for (int i = 0; i < pivotTable.getCTPivotTableDefinition().getPivotFields().getCount(); i++) {
//				CTPivotField fieldForNoSubtotals = pivotTable.getCTPivotTableDefinition().getPivotFields()
//						.getPivotFieldArray(i);
//
//				CTPivotField fld = pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldList().get(i);
//
//			}

			File excelFile1 = File.createTempFile("KRAHJEA1_Pivote", CommonConstraints.INSTANCE.XLSX_EXTENSION);
			String PivotefileName = excelFile1.getName();

			FileOutputStream output_file = new FileOutputStream(
					new File("C:/Users/sandesh.c/AppData/Local/Temp/SandeshPivote_test.xlsx"));
			workbook1.write(output_file);
			input_document.close();
			output_file.close();

			// end

			return excelFile1;

		} catch (SQLException e) {
			System.out.println("Datababse error:");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File IO error:");
			e.printStackTrace();
		}
		return null;

	}

	
	public File exportWithHeaderData(String sqlQuery, String dbURl, String dbUsername, String dbPassword,String headerData[],Integer headerColNum) {
		try (Connection connection = DriverManager.getConnection(dbURl, dbUsername, dbPassword)) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlQuery);

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Sheet 1");

			//writeHeaderLine(result, sheet);
			//writeDataLines(result, workbook, sheet);
			
			writeDataLinesWithHeader(result, workbook, sheet,headerData,headerColNum);

			File excelFile = File.createTempFile("KRAHJEA", CommonConstraints.INSTANCE.XLS_EXTENSION);
			LOGGER.info("TempFile :: {}", excelFile);

			FileOutputStream outputStream = new FileOutputStream(excelFile);
			workbook.write(outputStream);
			
			workbook.close();
			statement.close();

			return excelFile;

		} catch (SQLException e) {
			System.out.println("Datababse error:");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File IO error:");
			e.printStackTrace();
		}
		return null;

	}
	public File export(String sqlQuery, String dbURl, String dbUsername, String dbPassword) {
		try (Connection connection = DriverManager.getConnection(dbURl, dbUsername, dbPassword)) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlQuery);

//			if(!result.isBeforeFirst()) 
//				return null;

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Sheet 1");

			writeHeaderLine(result, sheet);
			writeDataLines(result, workbook, sheet);

			File excelFile = File.createTempFile("KRAHJEA", CommonConstraints.INSTANCE.XLS_EXTENSION);
			LOGGER.info("TempFile :: {}", excelFile);

			FileOutputStream outputStream = new FileOutputStream(excelFile);
			workbook.write(outputStream);
			
			workbook.close();
			statement.close();

			return excelFile;

		} catch (SQLException e) {
			System.out.println("Datababse error:");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File IO error:");
			e.printStackTrace();
		}
		return null;
	}

	private void writeHeaderLine(ResultSet result, HSSFSheet sheet) throws SQLException {
		// write header line containing column names
		ResultSetMetaData metaData = result.getMetaData();
		int numberOfColumns = metaData.getColumnCount();

		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
		    Row row = sheet.getRow(i);
		    if (row != null) {
		        sheet.removeRow(row);
		    }
		}
		
		//Row headerRow = sheet.createRow(5);
		Row headerRow = sheet.createRow(0);

		// exclude the first column which is the ID field
		for (int i = 1; i <= numberOfColumns; i++) {
			String columnName = metaData.getColumnName(i);
			System.out.println(columnName);
			Cell headerCell = headerRow.createCell(i - 1);
			headerCell.setCellValue(columnName);
		}
	}

	private void writeHeaderLineForPivote(ResultSet result, XSSFSheet sheet) throws SQLException {
		// write header line containing column names
		ResultSetMetaData metaData = result.getMetaData();
		int numberOfColumns = metaData.getColumnCount();

		Row headerRow = sheet.createRow(0);

		// exclude the first column which is the ID field
		for (int i = 1; i <= numberOfColumns; i++) {
			String columnName = metaData.getColumnName(i);
			System.out.println(columnName);
			Cell headerCell = headerRow.createCell(i - 1);
			headerCell.setCellValue(columnName);
		}
	}

	private void writeDataLinesWithHeader(ResultSet result, HSSFWorkbook workbook, HSSFSheet sheet,String[] headerArray,Integer headerColNum ) throws SQLException {
		// Provide a default value for headerColNum
	   // writeDataLinesWithHeader(result, workbook, sheet, headerArray, /* default value */ 0); 
	    
		ResultSetMetaData metaData = result.getMetaData();
		int numberOfColumns = metaData.getColumnCount();
		CellStyle style = workbook.createCellStyle();
		DataFormat dataFormat = workbook.createDataFormat();
		int rowCount;
				
		if(headerArray.length>0)
		{
			for(int RowCount=1;RowCount<headerArray.length; RowCount++)
			{
				Row row = sheet.createRow(RowCount);
				Cell cell = row.createCell(headerColNum);
				cell.setCellValue(headerArray[RowCount]);
				
			}
			rowCount = headerArray.length +2;
		}
		
		else
		{
			rowCount = 1;	
		}
		
		// exclude the first column which is the ID field
		Row headerRow = sheet.createRow(rowCount);
		
		for (int i = 1; i <= numberOfColumns; i++) {
			String columnName = metaData.getColumnName(i);
			System.out.println(columnName);
			Cell headerCell = headerRow.createCell(i - 1);
			headerCell.setCellValue(columnName);
		}
		
		rowCount=rowCount+1;
		
		while (result.next()) {
			Row row = sheet.createRow(rowCount++);

			for (int i = 1; i <= numberOfColumns; i++) {
				Object valueObject = result.getObject(i);

				Cell cell = row.createCell(i - 1);

				if (valueObject instanceof Boolean)
					cell.setCellValue((Boolean) valueObject);
				else if (valueObject instanceof Double)
					cell.setCellValue((double) valueObject);
				else if (valueObject instanceof Float)
					cell.setCellValue((float) valueObject);
				else if (valueObject instanceof BigDecimal)
					cell.setCellValue(((BigDecimal) valueObject).doubleValue());
				else if (valueObject instanceof Date) {
					cell.setCellValue((Date) valueObject);
					formatDateCell(workbook, cell);
				} else {
					style.setDataFormat(dataFormat.getFormat("@"));
					cell.setCellStyle(style);
					cell.setCellValue((String) valueObject);
				}
			}
		}
	}
	
	private void writeDataLines(ResultSet result, HSSFWorkbook workbook, HSSFSheet sheet) throws SQLException {
		ResultSetMetaData metaData = result.getMetaData();
		int numberOfColumns = metaData.getColumnCount();
		CellStyle style = workbook.createCellStyle();
		DataFormat dataFormat = workbook.createDataFormat();
		//int rowCount = 5;
		int rowCount = 1;

		while (result.next()) {
			Row row = sheet.createRow(rowCount++);

			for (int i = 1; i <= numberOfColumns; i++) {
				Object valueObject = result.getObject(i);

				Cell cell = row.createCell(i - 1);

				if (valueObject instanceof Boolean)
					cell.setCellValue((Boolean) valueObject);
				else if (valueObject instanceof Double)
					cell.setCellValue((double) valueObject);
				else if (valueObject instanceof Float)
					cell.setCellValue((float) valueObject);
				else if (valueObject instanceof BigDecimal)
					cell.setCellValue(((BigDecimal) valueObject).doubleValue());
				else if (valueObject instanceof Date) {
					cell.setCellValue((Date) valueObject);
					formatDateCell(workbook, cell);
				} else {
					// Setting the cell type to be a text cell
					/*
					 * The code style.setDataFormat(dataFormat.getFormat("@")); sets the data format
					 * of the cell style to "@". In Excel, the "@" symbol is used as a placeholder
					 * for text. When a cell is formatted with "@", any value entered into that cell
					 * will be treated as text, even if it looks like a number or a date.
					 */
					style.setDataFormat(dataFormat.getFormat("@"));
					cell.setCellStyle(style);
					cell.setCellValue((String) valueObject);
				}
			}
		}
	}

	private void writeDataLinesForPivote(ResultSet result, XSSFWorkbook workbook, XSSFSheet sheet) throws SQLException {
		ResultSetMetaData metaData = result.getMetaData();
		int numberOfColumns = metaData.getColumnCount();
		CellStyle style = workbook.createCellStyle();
		DataFormat dataFormat = workbook.createDataFormat();
		int rowCount = 1;

		while (result.next()) {
			Row row = sheet.createRow(rowCount++);

			for (int i = 1; i <= numberOfColumns; i++) {
				Object valueObject = result.getObject(i);

				Cell cell = row.createCell(i - 1);

				if (valueObject instanceof Boolean)
					cell.setCellValue((Boolean) valueObject);
				else if (valueObject instanceof Double)
					cell.setCellValue((double) valueObject);
				else if (valueObject instanceof Float)
					cell.setCellValue((float) valueObject);
				else if (valueObject instanceof BigDecimal)
					cell.setCellValue(((BigDecimal) valueObject).doubleValue());
				else if (valueObject instanceof Date) {
					cell.setCellValue((Date) valueObject);
					// commented by sandesh
					formatDateCellForPivote(workbook, cell);
				} else {
					// Setting the cell type to be a text cell
					/*
					 * The code style.setDataFormat(dataFormat.getFormat("@")); sets the data format
					 * of the cell style to "@". In Excel, the "@" symbol is used as a placeholder
					 * for text. When a cell is formatted with "@", any value entered into that cell
					 * will be treated as text, even if it looks like a number or a date.
					 */
					style.setDataFormat(dataFormat.getFormat("@"));
					cell.setCellStyle(style);
					cell.setCellValue((String) valueObject);
				}
			}

		}
	}

	private void formatDateCell(HSSFWorkbook workbook, Cell cell) {
		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper creationHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
		cell.setCellStyle(cellStyle);
	}

	private void formatDateCellForPivote(XSSFWorkbook workbook, Cell cell) {
		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper creationHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
		cell.setCellStyle(cellStyle);
	}
}
