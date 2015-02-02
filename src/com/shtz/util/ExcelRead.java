package com.shtz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ExcelRead {
   
    private HSSFWorkbook workbook;//������
   
   /*
    * ��ȡ�ļ�·���ַ���
    */
    
    public void importExcel(String strfile){
        try {
            //��ȡ������workbook
            workbook = new HSSFWorkbook(new FileInputStream(strfile));
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    /*
     * ��ȡ�ļ�
     */
    public void importExcel(File file){
    	try {
			 workbook = new HSSFWorkbook(new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    /*
     * ��ȡ�ļ���
     */
    public void importExcel(InputStream filestream){
    	try {
			 workbook = new HSSFWorkbook(filestream);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
   
   /*
    * ��ȡ��Ҫ�ڼ��Ź����������
    *importExcel����
    */
//    public List readSet(int sheetNumber){
//        List<List> result = new ArrayList<List>();
//       
//        //���ָ����sheet
//        HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
//        //���sheet������
//        int rowCount = sheet.getLastRowNum();
//        if(rowCount < 1){
//            return result;
//        }
//        //HashMap<Integer, Object> map=new HashMap<Integer, Object>();
//        //������row
//        for (int rowIndex = 0; rowIndex <= rowCount; rowIndex++) {
//            //����ж���
//            HSSFRow row = sheet.getRow(rowIndex);
//            if(null != row){
//               // List<Object> rowData = new ArrayList<Object>();
//            	Vector<Object> vector=new Vector<Object>();
//                //��ñ����е�Ԫ��ĸ���
//                int cellCount = row.getLastCellNum();
//                //������cell
//                for (short cellIndex = 0; cellIndex < cellCount; cellIndex++) {
//                    HSSFCell cell = row.getCell(cellIndex);
//                    //���ָ����Ԫ���е�����
//                    Object cellStr = this.getCellString(cell);
//                    
//                   // map.put(arg0, arg1)
//                    vector.add(cellStr);
//                }
//                result.add(vector);
//            }
//        }
//       
//        return result;
//    }
   
    /**
     * �ӵڼ��Ź�����ڼ��е�����
     * importExcel����
     */
     public List readRow(int sheetNumber,int rowIndex){
         List result = new ArrayList();
         //���ָ����sheet
         HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
         //���sheet������
         int rowCount = sheet.getLastRowNum();
         if(rowCount < 1){
             return result;
         }
         //������row
         //for (int rowIndex = rows+2; rowIndex <= rowCount; rowIndex++) {
             //����ж���
             HSSFRow row = sheet.getRow(rowIndex);
             if(null != row){
//             	Vector<Object> vector=new Vector<Object>();
                 //��ñ����е�Ԫ��ĸ���
                 int cellCount = row.getLastCellNum();
                 //������cell
                 for (short cellIndex = 0; cellIndex < cellCount; cellIndex++) {
                     HSSFCell cell = row.getCell(cellIndex);
                     //���ָ����Ԫ���е�����
                     Object cellStr =this.getCellString(cell);
//                     vector.add(cellStr);
                     if(cellStr!=null){
//	                   	  if(cellIndex == 3){
//	                   		 result.add( new DecimalFormat("#").format(Integer.valueOf((String) cellStr)));
//	                   	 }else{
	                   		 result.add(cellStr.toString());
//	                     	 }
                     }else{
                    	 result.add(null);
                     }
                       
                 }
             }
         //}
        
         return result;
     }
     /**
      * �ӵڼ��Ź�����ڼ��е�����
      * importExcel����
      */
      public List readRow1(int sheetNumber,int rowIndex){
          List result = new ArrayList();
          //���ָ����sheet
          HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
          //���sheet������
          int rowCount = sheet.getLastRowNum();
          if(rowCount < 1){
              return result;
          }
          //������row
          //for (int rowIndex = rows+2; rowIndex <= rowCount; rowIndex++) {
              //����ж���
              HSSFRow row = sheet.getRow(rowIndex);
              if(null != row){
//              	Vector<Object> vector=new Vector<Object>();
                  //��ñ����е�Ԫ��ĸ���
                  int cellCount = row.getLastCellNum();
                  //������cell
                  for (short cellIndex = 0; cellIndex < cellCount; cellIndex++) {
                      HSSFCell cell = row.getCell(cellIndex);
                      //���ָ����Ԫ���е�����
                      Object cellStr =this.getCellString(cell);
//                      vector.add(cellStr);
                      if(cellStr!=null){
 	                   		 result.add(cellStr.toString());
                      }else{
                     	 result.add(null);
                      }
                        
                  }
              }
          //}
         
          return result;
      }
     /**
      * ��ȡָ�����������
      * @param sheetNumber
      * @return
      */
     public int getRowIndex(int sheetNumber){
         HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
         //���sheet������
         int rowCount = sheet.getLastRowNum();
         if(rowCount < 1){
             return 0;
         }
         return rowCount;
     }
     /**
      * �ӵڼ��Ź�����ڼ��ж����ڼ���
      * @param sheetNumber
      * @param rows
      * @param getrows
      * @return
      */
//     public List readCell(int sheetNumber,int rows,int getrows){
//         List<List> result = new ArrayList<List>();
//        
//         //���ָ����sheet
//         HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
//         //���sheet������
//         int rowCount = getrows;
//         if(rowCount < 1){
//             return result;
//         }
//         //HashMap<Integer, Object> map=new HashMap<Integer, Object>();
//         //������row
//         for (int rowIndex = rows+2; rowIndex <= rowCount; rowIndex++) {
//             //����ж���
//             HSSFRow row = sheet.getRow(rowIndex);
//             if(null != row){
//                // List<Object> rowData = new ArrayList<Object>();
//             	Vector<Object> vector=new Vector<Object>();
//                 //��ñ����е�Ԫ��ĸ���
//                 int cellCount = row.getLastCellNum();
//                 //������cell
//                 for (short cellIndex = 0; cellIndex < cellCount; cellIndex++) {
//                     HSSFCell cell = row.getCell(cellIndex);
//                     //���ָ����Ԫ���е�����
//                     Object cellStr = this.getCellString(cell);
//                     
//                    // map.put(arg0, arg1)
//                     vector.add(cellStr);
//                 }
//                 result.add(vector);
//             }
//         }
//        
//         return result;
//     }
     /**
      * ��ȡ�ڼ��Ź�����ĵڼ���
      * @param sheetNumber
      * @param cells
      * @return
      */
//     public List readColum(int sheetNumber,int cells){
//         List<List> result = new ArrayList<List>();
//        
//         //���ָ����sheet
//         HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
//         //���sheet������
//         int rowCount = sheet.getLastRowNum();
//         if(rowCount < 1){
//             return result;
//         }
//         //HashMap<Integer, Object> map=new HashMap<Integer, Object>();
//         //������row
//         for (int rowIndex = 2; rowIndex <= rowCount; rowIndex++) {
//             //����ж���
//             HSSFRow row = sheet.getRow(rowIndex);
//             if(null != row){
//                // List<Object> rowData = new ArrayList<Object>();
//             	Vector<Object> vector=new Vector<Object>();
//                 //��ñ����е�Ԫ��ĸ���
//                     HSSFCell cell = row.getCell(cells);
//                     Object cellStr = this.getCellString(cell);
//                     vector.add(cellStr);
//                     result.add(vector);
//             }
//         }
//        
//         return result;
//     }
   /**
    * ��ȡһ��cell����������
    * @param cell
    * @return
    */
    private Object getCellString(HSSFCell cell) {
        // TODO Auto-generated method stub
        Object result = null;
        if(cell != null){
            //��Ԫ�����ͣ�Numeric:0,String:1,Formula:2,Blank:3,Boolean:4,Error:5
            int cellType = cell.getCellType();
            switch (cellType) {
            case HSSFCell.CELL_TYPE_STRING:
                result = cell.getRichStringCellValue().getString();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
				if(HSSFDateUtil.isCellDateFormatted(cell))
				{
					result = cell.getDateCellValue();
				}
				else{
						double a = cell.getNumericCellValue();
						if(String.valueOf(a).toUpperCase().indexOf('E') > -1 ){ 
							result = String.valueOf(new DecimalFormat("#.######").format(a));
							
						}else{
							result = String.valueOf(new DecimalFormat("#.######").format(a));
						}
					}
				break;
            case HSSFCell.CELL_TYPE_FORMULA:
                result = cell.getNumericCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                result = cell.getBooleanCellValue();
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                result = null;
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                result = null;
                break;
            default:
                System.out.println("ö������������");
                break;
            }
        }
        return result;
    }
   
    //test
    /*public static void main(String[] args) throws Exception {
        //File file = new File("E:\\123.xlsx");
    	//ExcelRead excel=new ExcelRead();
    	//XSSFWorkbook xw = new XSSFWorkbook(new FileInputStream(file));
    	
    	//excel.importExcel("E:\\123.xls");
        ExcelRead parser = new ExcelRead();
        parser.importExcel("E:\\123.xls");
        System.out.println("--����--"+parser.getRowIndex(0));
//        parser.importExcel(filestream);
        List<List> datas = parser.readRow(0, 2);
//        List datas = parser.readRow(0, 5);
        for (int i = 0; i < datas.size(); i++) {
        	System.out.println(datas.get(i));
            List row = datas.get(i);
            for (short j = 0; j < row.size(); j++) {
                Object value = row.get(j);
                String data = String.valueOf(value);
                System.out.print(data + "\t");
            }
            System.out.println();   
        }
    	List<Object> listparam=null;
    	String temp_user_id="";
    	int rowcount=parser.getRowIndex(0);
    	  for (int i = 0; i <= rowcount; i++) {
				List listuser = parser.readRow(0, i);
              for (int j = 0; j < listuser.size(); j++) {
              	temp_user_id=listuser.get(0).toString();
              	System.out.print(temp_user_id+"--");
				}
              System.out.println("--��"+i+"������"+listuser.size());
    	  }
        
        
		String ids="1,2,3,4,5";
		String[] id=new String[ids.length()];
		String sql="select * from meeting.mail_info where MAIL_ID in (";
		id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			if(i!=id.length-1){
				sql=sql+"'"+id[i]+"',";
			}else{
				sql+="'"+id[i]+"'";
			}		
		}
		sql=sql+")";
		System.out.println(sql);
    }*/
    
    public static void main(String[] args) throws Exception {

        ExcelRead parser = new ExcelRead();
        parser.importExcel("//Users//shijiaoming//2.xls");
        System.out.println("--����--"+parser.getRowIndex(0));

    	List<Object> listparam=null;
    	String temp_user_id="";
    	int rowcount=parser.getRowIndex(0);
    	  for (int i = 0; i <= rowcount; i++) {
				List listuser = parser.readRow(0, i);
				System.out.println(listuser.toString());
              for (int j = 0; j < listuser.size(); j++) {
              	temp_user_id=listuser.get(j).toString();
              	//System.out.print(temp_user_id+"--");
				}
             // System.out.println("--��"+i+"������"+listuser.size());
    	  }
        
       
    }
}

