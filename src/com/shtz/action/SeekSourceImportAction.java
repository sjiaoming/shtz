package com.shtz.action;

import java.io.BufferedInputStream;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.shtz.model.Plan;
import com.shtz.service.PlanService;
import com.shtz.service.UserCompService;
import com.shtz.util.ExcelCreate;
import com.shtz.util.ExcelRead;
import com.shtz.util.ExcelRead2010;

/**
 * @author sjm
 *  
 */
public class SeekSourceImportAction extends ActionSupport {

	private ExcelCreate excelCreateService;
	
	private ExcelRead ExcelReadService;
	
	private ExcelRead2010 ExcelReadxService;
	
	private String importfilename;
	
	private File some;
	
	private PlanService pservice;
	
	private Map<Integer, Map<Integer,String>> Feedback =new TreeMap<Integer, Map<Integer,String>>();
	
	private int numb;
	
	private String ex;
	
	public String getEx() {
		return ex;
	}
	public void setEx(String ex) {
		this.ex = ex;
	}
	public void setExcelReadxService(ExcelRead2010 excelReadxService) {
		ExcelReadxService = excelReadxService;
	}
	public File getSome() {
		return some;
	}
	public void setSome(File some) {
		this.some = some;
	}
	public int getNumb() {
		return numb;
	}
	public void setNumb(int numb) {
		this.numb = numb;
	}
	public Map<Integer, Map<Integer, String>> getFeedback() {
		return Feedback;
	}
	public void setFeedback(Map<Integer, Map<Integer, String>> feedback) {
		Feedback = feedback;
	}
	public void setPservice(PlanService pservice) {
		this.pservice = pservice;
	}
	public String getImportfilename() {
		return importfilename;
	}
	public void setImportfilename(String importfilename) {
		this.importfilename = importfilename;
	}
	public void setExcelReadService(ExcelRead excelReadService) {
		ExcelReadService = excelReadService;
	}
	public void setExcelCreateService(ExcelCreate excelCreateService) {
		this.excelCreateService = excelCreateService;
	}
	



	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		//上传到服务器端upload固定文件中,文件 名改为test.xls
		String someFileName="test.xls";
		String path = "/upload";
		try {
			//路径upload创建,不存在就创建，存在就不创建
			String filePath = "/shtz/upload";
			File file = new File(filePath);
			 //判断文件夹是否存在,如果不存在则创建文件夹
			 if (!file.exists()) {
				 file.mkdirs();
				 /*if(file.mkdir()){ //创建文件夹，如果要创建文件： 
				   file.createNewFile();
			   	}*/
			 }
		} catch (Exception e) {
		}
		
		path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath(path);
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(some));
		File f=new File(path, someFileName);
		BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(f));
		IOUtils.copy(is, os);
		is.close();
		os.close();
	
		//excel读取文件
		if(ex!=null&&ex.equals("xls")){
			ExcelReadService.importExcel(path + "/" + someFileName);
		}else if(ex!=null&&ex.equals("xlsx")){
			ExcelReadxService.importExcel(path + "/" + someFileName);
		}
        //List<List<String>> allList=new ArrayList<List<String>>();
		int rowcount=0;
		if(ex!=null&&ex.equals("xls")){
			 rowcount=ExcelReadService.getRowIndex(0);
		}else if(ex!=null&&ex.equals("xlsx")){
			rowcount=ExcelReadxService.getRowIndex(0);
		}
		List listuser = new ArrayList();
		List<List> lists = new ArrayList();
    	  numb=rowcount;
    	  for (int i = 1; i <= rowcount; i++) {
    		  if(ex!=null&&ex.equals("xls")){
    			  listuser =  ExcelReadService.readRow(0, i);
    			}else if(ex!=null&&ex.equals("xlsx")){
    				listuser = ExcelReadxService.readRow(0, i);
    			}
    		  lists.add(listuser);
    		  Map<Integer,String> map= this.isNumeric(listuser);
    		  boolean flag=false;
    		  for (String str : map.values()) {
				if(!str.equals("")){
					flag=true;
				}
    		  }
    		  if(flag){
    			  if(Feedback.size()>20){
        			  ServletActionContext.getRequest().getSession().setAttribute("msg","友情提示：您的错误数超过20个，请仔细核对后再尝试。");
        			  break;
        		  }else{
        			  ServletActionContext.getRequest().getSession().setAttribute("msg","错误信息");
        		  }
    			  Feedback.put(i, map);
    		  }
    	  }
    	  //判断有没有错误
    	  if(Feedback.size()!=0){
    		  if(f.exists()){
    			  f.delete();
    		  }
    		  return "feedback";
    	  }else{
    		  for (int i = 1; i <= rowcount; i++) {
        		  Plan p=new Plan();
        		  for (int j = 0; j < lists.get(i-1).size(); j++) {
    	    			  if(lists.get(i-1).get(j)!=null){
    	    				  switch (j) {
    	    				  case 1: p.setOldPlanNum(lists.get(i-1).get(j).toString());break;
    	    				  case 2: p.setPlanNum(lists.get(i-1).get(j).toString());break;
    	    				  case 3: p.setProcurementPrice(Double.parseDouble(lists.get(i-1).get(j).toString()));break;
    	    				  case 4: p.setProcurementMoney(Double.parseDouble(lists.get(i-1).get(j).toString()));break;
    	    				  }
    	    			  }else{
    	    				  continue;
    	    			  }
        		    }
        		  pservice.modifySeekSource(p);
        	  }
    		  if(f.exists()){
    			  f.delete();
    		  }
    	  }
		return "importfile_success";
	}
	/**
	 * 
	 * @param list
	 * @return 错误所在行号
	 */

	public  Map<Integer,String> isNumeric(List<String> list){ 
		Map<Integer,String> l=new HashMap<Integer, String>();
		for(int i=0;i<list.size();i++){
			if(i!=1 && i!=2){
				if(list.get(i)==null){
					l.put(i, "为空值");
					continue;
				}
				if(!isNumeric1(list.get(i))){
					l.put(i, "不是数字");
					continue;
				}
				l.put(i, "");
			}else{
				l.put(i, "");
			}
		}
		return l; 
	}

	public  boolean isNumeric1(String str){
		try {
			DecimalFormat df = new DecimalFormat("0"); 
			double strd=Double.parseDouble(str);
			Pattern pattern = Pattern.compile("^\\d+$|\\d+\\.\\d+$"); 
			if(pattern.matcher(df.format(strd).toString()).matches()){
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	
}
