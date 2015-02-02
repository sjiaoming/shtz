package com.shtz.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.DataFormat;

public class ExcelCreate {

   public  HSSFWorkbook wb = null;

   public  HSSFSheet sheet = null;

   public  HSSFDataFormat format = null;

   public  HSSFRow hdRow = null;

	int listlength = 0;

	/**
	 * ���ù�����ĸ�ʽ
	 * 
	 * @param sheetName
	 */
	public ExcelCreate() {
		wb = new HSSFWorkbook();
	}

	public void createSheet(String sheetName) {
		sheet = wb.createSheet(sheetName);
		format = wb.createDataFormat();
		hdRow = sheet.createRow(0);
		sheet.setDefaultRowHeightInPoints(12);
		sheet.setDefaultColumnWidth(12);
	}

	/* ���ø��е�Ԫ���� */
	public void setDefaultCellHighWidthInRange(short[] eachCellWidth, int high) {
		// �ٶ���һ�к͵�һ������ĵ�Ԫ���Ѿ��������ˣ�Ҳ����˵������֮ǰ�Ѿ�������DesignXlsHeaderFooter.setXlsHeader
		sheet.setDefaultRowHeightInPoints(high);// ����Ĭ�ϸ�
		/* ���ø��е�Ԫ���� */
		for (int i = 0; i < eachCellWidth.length; i++) {
			// System.out.print(""+i+"\t");
			sheet.setColumnWidth((short) i, (short) ((eachCellWidth[i]) * 256));
		}
	}

	/**
	 * ��ͷ����
	 * 
	 * @throws Exception
	 */
	public void addHeader(List rowvalues, boolean isFilter) throws Exception {
		listlength = rowvalues.size();
		// ��������
		HSSFFont workFont = wb.createFont();
		workFont.setFontName("΢���ź�");
		workFont.setFontHeightInPoints((short) 14);
		workFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// ��ͷ��ʽ������ɫ
		HSSFCellStyle hdStyle = wb.createCellStyle();
		hdStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		hdStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		hdStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		hdStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		hdStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		hdStyle.setRightBorderColor(HSSFColor.BLACK.index);
		hdStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		hdStyle.setTopBorderColor(HSSFColor.BLACK.index);
		hdStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		hdStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		hdStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		hdStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		hdStyle.setFont(workFont);

		String[] title = new String[rowvalues.size()];
		for (int i = 0; i < rowvalues.size(); i++) {
			title[i] = (String) rowvalues.get(i);
		}
		HSSFRow dtRow = sheet.createRow((1));
		if (isFilter == true) {
			for (int i = 0; i < title.length; i++) {
				HSSFCell cell1 = hdRow.createCell(i);
				HSSFRichTextString value = new HSSFRichTextString(title[i]);
				cell1.setCellValue(value);
				cell1.setCellStyle(hdStyle);
			}
		} else {
			for (int i = 0; i < title.length; i++) {
				
				HSSFCell cell2 = dtRow.createCell(i);
				HSSFRichTextString value2 = new HSSFRichTextString(title[i]);
				cell2.setCellValue(value2);
				
			}
		}
	}

	/**
	 * ���ݵĵ���
	 */
	// public void addRow(HashMap<Integer, List> rowvalues) {
	// for (int i = 0; i < rowvalues.size(); i++) {
	// HSSFRow dtRow = sheet.createRow((i + 2));
	// List list = (List) rowvalues.get(i);
	// for (int j = 0; j < list.size(); j++) {
	// Object cell_data = list.get(j);
	// HSSFCell cell = dtRow.createCell(j);
	// if (cell_data instanceof String) {
	// cell.setCellValue(new HSSFRichTextString((String) cell_data));
	// } else if (cell_data instanceof Double) {
	// HSSFCellStyle dtStyle = wb.createCellStyle();
	// dtStyle.setDataFormat(format.getFormat("yyyy/MM/dd"));
	// cell.setCellValue((Double) cell_data);
	// } else if (cell_data instanceof Integer) {
	// cell.setCellValue(Double.valueOf(String.valueOf(cell_data)));
	// } else if (cell_data instanceof Date) {
	// cell.setCellValue((Date) cell_data);
	// } else if (cell_data instanceof Boolean) {
	// cell.setCellValue((Boolean) cell_data);
	// }
	// // ���ĸ�ʽ
	// HSSFCellStyle dtStyle = wb.createCellStyle();
	// dtStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	// dtStyle.setBottomBorderColor(HSSFColor.BLACK.index);
	// dtStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	// dtStyle.setLeftBorderColor(HSSFColor.BLACK.index);
	// dtStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	// dtStyle.setRightBorderColor(HSSFColor.BLACK.index);
	// dtStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	// dtStyle.setTopBorderColor(HSSFColor.BLACK.index);
	// dtStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
	// //������ɫ
	// if(i%2!=0)
	// dtStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	// dtStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	// cell.setCellStyle(dtStyle);
	// }
	// }
	//
	// }
	public HSSFCellStyle setdtStyle(){
		HSSFCellStyle dtStyle = wb.createCellStyle();
		dtStyle.setDataFormat(format.getFormat("text"));
		dtStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dtStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		dtStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dtStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		dtStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dtStyle.setRightBorderColor(HSSFColor.BLACK.index);
		dtStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dtStyle.setTopBorderColor(HSSFColor.BLACK.index);
		dtStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		return dtStyle;
	}
	public HSSFCellStyle setdateStyle(){
		HSSFCellStyle dateStyle = wb.createCellStyle();
		dateStyle.setDataFormat(format.getFormat("yyyy-m-d"));
		dateStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dateStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		dateStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dateStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		dateStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dateStyle.setRightBorderColor(HSSFColor.BLACK.index);
		dateStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dateStyle.setTopBorderColor(HSSFColor.BLACK.index);
		dateStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		return dateStyle;
	}
	
	/**
	 * ���һ��
	 */
	int s = 1;
	
	public void addRow(List rowvalues) {
		HSSFRow dtRow = sheet.createRow(s++);
		DataFormat format = wb.createDataFormat();
		
		HSSFCellStyle dateStyle = wb.createCellStyle();
		dateStyle.setDataFormat(format.getFormat("yyyy-m-d"));
		dateStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dateStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		dateStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dateStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		dateStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dateStyle.setRightBorderColor(HSSFColor.BLACK.index);
		dateStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dateStyle.setTopBorderColor(HSSFColor.BLACK.index);
		dateStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		
		HSSFCellStyle dtStyle = wb.createCellStyle();
		dtStyle.setDataFormat(format.getFormat("text"));
		dtStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dtStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		dtStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dtStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		dtStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dtStyle.setRightBorderColor(HSSFColor.BLACK.index);
		dtStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dtStyle.setTopBorderColor(HSSFColor.BLACK.index);
		dtStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		for (int j = 0; j < rowvalues.size(); j++) {
			String flag = "";
			Object cell_data = rowvalues.get(j);
			HSSFCell cell = dtRow.createCell(j);
			// ���ĸ�ʽ
			if (cell_data instanceof String) {
				flag = "string";
				cell.setCellValue((String)cell_data);
			}
			else if (cell_data instanceof Double) {
				cell.setCellValue((Double) cell_data);
			} 
			else if (cell_data instanceof Integer) {
				cell.setCellValue(Double.valueOf(String.valueOf(cell_data)));
			} 	
			else if (cell_data instanceof Date) {
				flag = "date";
				cell.setCellValue((Date) cell_data);
			} 
			else if (cell_data instanceof Boolean) {
				cell.setCellValue((Boolean) cell_data);
			}else if (cell_data instanceof Float) {
				cell.setCellValue((Float) cell_data);
			}
			// ������ɫ
//			if(s%2!=0)
//			dtStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
//			dtStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			if(flag==""||flag.equals("string")){
				cell.setCellStyle(dtStyle);
			}else if(flag.equals("date")){
				cell.setCellStyle(dateStyle);
			}
			
		}
		// }
	}
	
	public void addRow(List rowvalues,HSSFCellStyle dtStyle,HSSFCellStyle dateStyle) {
		HSSFRow dtRow = sheet.createRow(s++);
		DataFormat format = wb.createDataFormat();
		
		for (int j = 0; j < rowvalues.size(); j++) {
			String flag = "";
			Object cell_data = rowvalues.get(j);
			HSSFCell cell = dtRow.createCell(j);
			// ���ĸ�ʽ
			if (cell_data instanceof String) {
				flag = "string";
				cell.setCellValue((String)cell_data);
			}
			else if (cell_data instanceof Double) {
				cell.setCellValue((Double) cell_data);
			} 
			else if (cell_data instanceof Integer) {
				cell.setCellValue(Double.valueOf(String.valueOf(cell_data)));
			} 	
			else if (cell_data instanceof Date) {
				flag = "date";
				cell.setCellValue((Date) cell_data);
			} 
			else if (cell_data instanceof Boolean) {
				cell.setCellValue((Boolean) cell_data);
			}else if (cell_data instanceof Float) {
				cell.setCellValue((Float) cell_data);
			}
			// ������ɫ
//			if(s%2!=0)
//			dtStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
//			dtStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			if(flag==""||flag.equals("string")){
				cell.setCellStyle(dtStyle);
			}else if(flag.equals("date")){
				cell.setCellStyle(dateStyle);
			}
			
		}
		// }
	}
	
	/**
	 * �����ͬ����
	 * @param starRow
	 * @param rows
	 */
//	public  void insertRow(int starRow,int rows) {
//
//		  sheet.shiftRows(starRow + 1, sheet.getLastRowNum(), rows,true,false);
////		  Parameters:
////		   startRow - the row to start shifting
////		   endRow - the row to end shifting
////		   n - the number of rows to shift
////		   copyRowHeight - whether to copy the row height during the shift
////		   resetOriginalRowHeight - whether to set the original row's height to the default
//		  
//		  starRow = starRow - 1;
//
//		  HSSFRow sourceRow = null;
//		  HSSFRow targetRow = null;
//		  HSSFCell sourceCell = null;
//		  HSSFCell targetCell = null;
//		  for (int i = 0; i < rows; i++) {
//
//		   short m;
//
//		   starRow = starRow + 1;
//		   sourceRow = sheet.getRow(starRow);
//		   targetRow = sheet.createRow(starRow + 1);
//		   targetRow.setHeight(sourceRow.getHeight());
//
//		   for (m = sourceRow.getFirstCellNum(); m < sourceRow.getLastCellNum(); m++) {
//
//		    sourceCell = sourceRow.getCell(m);
//		    targetCell = targetRow.createCell(m);
//
//		    //targetCell.setEncoding(sourceCell.getEncoding());
//		    targetCell.setCellStyle(sourceCell.getCellStyle());
//		    targetCell.setCellType(sourceCell.getCellType());
//
//		   }
//		  }
//
//		 } 
	/**
	 * ��ָ������׷��һ������
	 * 
	 * @param rowvalues
	 * @param row
	 */
	public void insertRow(List rowvalues, int row) {
		sheet.shiftRows(row, sheet.getLastRowNum(), 1);
		HSSFRow dtRow = sheet.createRow(row);
		HSSFCellStyle dtStyle = wb.createCellStyle();
		dtStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dtStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		dtStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dtStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		dtStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dtStyle.setRightBorderColor(HSSFColor.BLACK.index);
		dtStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dtStyle.setTopBorderColor(HSSFColor.BLACK.index);
		dtStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		DataFormat format = wb.createDataFormat();
		Short str = format.getFormat("text");
		Short date = format.getFormat("yyyy-m-d");
		for (int j = 0; j < rowvalues.size(); j++) {
			Object cell_data = rowvalues.get(j);
			HSSFCell cell = dtRow.createCell(j);
			if (cell_data instanceof String) {
				dtStyle.setDataFormat(str);
				cell.setCellValue((String) cell_data);
			} else if (cell_data instanceof Double) {
				cell.setCellValue((Double) cell_data);
			} else if (cell_data instanceof Integer) {
				cell.setCellValue(Double.valueOf(String.valueOf(cell_data)));
			} else if (cell_data instanceof Date) {
				dtStyle.setDataFormat(date);
				cell.setCellValue((Date) cell_data);
			} else if (cell_data instanceof Boolean) {
				cell.setCellValue((Boolean) cell_data);
			}else if (cell_data instanceof Float) {
				cell.setCellValue((Float) cell_data);
			}
			// ������ɫ
//			 if(s%2!=0)
//			dtStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
//			dtStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cell.setCellStyle(dtStyle);
		}
	}
	/**
	 * ɾ��ָ������
	 * @param row
	 */
	public void delRow(int row) {
		if(row>0){
			try {
				//HSSFRow dtRow = sheet.getRow(row);
				sheet.shiftRows(row, sheet.getLastRowNum(), -1);
				//sheet.removeRow(dtRow);
			} catch (RuntimeException e) {
				e.printStackTrace();
				System.out.println("error");
			}
		}else{
			System.out.println("�����");
		}
	}
	/**
	 * ��ָ�����и��������б�
	 * 
	 * @param row
	 * @param cells
	 * @param list
	 */
	public void setSelect(int row, int cells, List cellvalue) {
		String[] str = new String[cellvalue.size()];
		for (int i = 0; i < cellvalue.size(); i++) {
			str[i] = (String) cellvalue.get(i);
		}
		CellRangeAddressList regions = new CellRangeAddressList(row, 65535, cells,cells);
		DVConstraint constraint = DVConstraint.createExplicitListConstraint(str);
		HSSFDataValidation dataValidate = new HSSFDataValidation(regions,constraint);
		sheet.addValidationData(dataValidate); // ����������Ч�Ե���ǰsheet����
	}

	/**
	 * �ϲ���Ԫ��//���Ͻǵ����½�int col1,int row1,int col2,int row2
	 */
	 public void hebing(int col1,int row1,int col2,int row2){
//	 HSSFRow row = sheet.createRow(0);
//	 HSSFCell cell = row.createCell(0);
//	 cell.setCellValue(sheetName);
	 //sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 5));
	 sheet.addMergedRegion(new CellRangeAddress(
			 col1, //first row (0-based)
             row1, //last row  (0-based)
             col2, //first column (0-based)
             row2  //last column  (0-based)
     ));//���úϲ�������
	 }
	/**
	 * �����ļ����ɵ�·��
	 * 
	 * @param file
	 * @throws Exception
	 */
	public void exportExcel(String file) throws Exception {
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.close();
	}

	/**
	 * �����ļ����ɵ��ļ�
	 * 
	 * @param file
	 * @throws Exception
	 */
	public void exportExcel(File file) throws Exception {
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.close();
	}

	/**
	 * �����ļ����ɵ��ļ�
	 * 
	 * @param file
	 * @throws Exception
	 */
	public void exportExcel(OutputStream outputstream) throws Exception {
		BufferedOutputStream buffout = new BufferedOutputStream(outputstream);
		wb.write(buffout);
		buffout.flush();
		buffout.close();
	}

	public static void main(String[] args) throws Exception {
		List<String> list = new ArrayList<String>();
		list.add("Ա����");
		list.add("����");
		list.add("������ǰ");
		list.add("�����ص�");
		list.add("ְ��");
		list.add("�Ա�");
		list.add("���e");

		ExcelCreate s = new ExcelCreate();
		s.createSheet("ϵͳ������");
		s.addHeader(list, true);
		//s.addHeader(list, false);

		List lists = null;
		for (int i = 0; i < 6; i++) {

			lists = new ArrayList();
			lists.add("A000" + i);
			if(i==1){
			   lists.add("����");
			}
			else if(i==2){
				 lists.add("����");
			}
			else if(i==3){
				 lists.add("�ŷ�");
			}
			else if(i==4){
				 lists.add("ʲô");
			}
			else if(i==5){
				 lists.add("�Ǹ�");
			}
			lists.add("6500444444444444444444444444444444 ");
//			new SimpleDateFormat("yyyy-M-d").format(new Date())
			lists.add(new Date());
			lists.add("����ս" + i);
			lists.add("��" + i);
			lists.add("��ɽ��" + i);
			s.addRow(lists);
		}
		List lists1 = new ArrayList();
		lists1.add("�廢�Ͻ�֮һ");
		lists1.add("����");
		lists1.add("6500");
		lists1.add("2010-9-1");
		lists1.add("����ս");
		lists1.add("��sdfdsf");
		lists1.add("weher");
		

		s.insertRow(lists1, 3);
		//s.insertRow(5, 6);
		//s.delRow(5);
		s.setSelect(3, 3, list);
		s.createSheet("�ڶ���ϵͳ������");// �ڶ��Ź�����

		File file = new File("F:\\ss.xls");
		s.exportExcel(file);
	}
}


