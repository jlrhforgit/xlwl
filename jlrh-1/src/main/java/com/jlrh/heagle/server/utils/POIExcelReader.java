package com.jlrh.heagle.server.utils;
import java.io.FileInputStream ;
import java.io.IOException ;
import java.io.InputStream ;
import java.text.SimpleDateFormat ;
import java.util.ArrayList ;
import java.util.Date ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;

import org.apache.poi.hssf.usermodel.HSSFCell ;
import org.apache.poi.hssf.usermodel.HSSFDateUtil ;
import org.apache.poi.hssf.usermodel.HSSFRow ;
import org.apache.poi.hssf.usermodel.HSSFSheet ;
import org.apache.poi.hssf.usermodel.HSSFWorkbook ;
import org.apache.poi.poifs.filesystem.POIFSFileSystem ;
import org.apache.poi.ss.usermodel.Cell ;
import org.apache.poi.ss.usermodel.Row ;
import org.apache.poi.ss.usermodel.Sheet ;
import org.apache.poi.ss.usermodel.Workbook ;
import org.apache.poi.ss.usermodel.WorkbookFactory ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

/**
 * Excel上传数据工具类
 * 
 * @author Administrator
 *
 */
public class POIExcelReader {
	
	/**
	 * 
	 */
	private final static Logger	log	= LoggerFactory.getLogger(POIExcelReader.class) ;
	
	private InputStream			inputStream ;
	
	private Workbook			workbook ;
	
	private Sheet				sheet ;
	
	private Row					row ;
	
	
	public POIExcelReader() {
	}
	
	
	public POIExcelReader(InputStream inputStream) {
		this.inputStream = inputStream ;
		this.createWorkbook(inputStream) ;
	}
	
	
	/**
	 * 
	 *
	 * @param inputStream
	 */
	private void createWorkbook(InputStream inputStream) {
		try {
			workbook = WorkbookFactory.create(inputStream) ;
		} catch (Exception e) {
			log.error("Excel文件解析失败！", e) ;
		}
	}
	
	
	/** 
	 * 读取Excel表头内容
	 * 
	 * @param sheetAt
	 * @return 
	 */
	public String [] readExcelTitle(int sheetAt) {
		String [] title = null ;
		if (inputStream != null) {
			try {
				if (null == workbook) {
					workbook = WorkbookFactory.create(inputStream) ;
				}
				sheet = workbook.getSheetAt(sheetAt) ;
				row = sheet.getRow(0) ;
				// 标题总列数
				int colNum = row.getPhysicalNumberOfCells() ;
				title = new String [colNum] ;
				for (int i = 0; i < colNum; i++) {
					title[i] = getStringCellValue(row.getCell(i)) ;
				}
			} catch (Exception e) {
				log.error("Excel文件解析失败！", e) ;
			}
		}
		return title ;
	}
	
	
	/**
	 * 获取单元格数据内容为字符串类型的数据
	 *
	 * @param cell
	 * @return
	 */
	private String getStringCellValue(Cell cell) {
		String strCell = "" ;
		if (cell != null) {
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					strCell = cell.getStringCellValue() ;
					break ;
				case Cell.CELL_TYPE_FORMULA:
					String formulaValue = cell.getCellFormula() ;
					if (formulaValue != null && !"".equals(formulaValue)) {
						try {
							strCell = String.valueOf(cell.getNumericCellValue()) ;
						} catch (Exception e) {
							strCell = cell.getStringCellValue() ;
						}
					}
					break ;
				case Cell.CELL_TYPE_NUMERIC:
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						strCell = getDateCellValue(cell) ;
					} else {
						cell.setCellType(Cell.CELL_TYPE_STRING) ;
						String temp = cell.getStringCellValue() ;
						if (temp.indexOf(".") > -1) {
							strCell = String.valueOf(new Double(temp)).trim() ;
						} else {
							strCell = temp.trim() ;
						}
					}
					break ;
				case Cell.CELL_TYPE_BOOLEAN:

					strCell = String.valueOf(cell.getBooleanCellValue()) ;
					break ;
				case Cell.CELL_TYPE_BLANK:
					strCell = "" ;
					break ;
				default:
					strCell = "" ;
					break ;
			}
		}
		return strCell ;
	}
	
	
	/**
	 * 获取单元格数据内容为日期类型的数据
	 *
	 * @param cell
	 * @return
	 */
	private String getDateCellValue(Cell cell) {
		String result = "" ;
		try {
			int cellType = cell.getCellType() ;
			if (cellType == 0) {
				Date				date	= cell.getDateCellValue() ;
				SimpleDateFormat	sdf		= new SimpleDateFormat("yyyy-MM-dd") ;
				result = sdf.format(date) ;
			} else if (cellType == 1) {
				String date = getStringCellValue(cell) ;
				result = date.replaceAll("[年月]", "-").replace("日", "").trim() ;
			} else if (cellType == 3) {
				result = "" ;
			}
		} catch (Exception e) {
			log.error("Excel文件解析失败，日期格式不正确！", e) ;
		}
		return result ;
	}
	
	
	/**
	 * 
	 *
	 * @param sheetAt
	 * @return
	 */
	public int getRowNum(int sheetAt) {
		int rowNum = 0 ;
		if (inputStream != null) {
			try {
				if (null == workbook) {
					workbook = WorkbookFactory.create(inputStream) ;
				}
				sheet = workbook.getSheetAt(sheetAt) ;
				rowNum = sheet.getLastRowNum() ;
			} catch (Exception e) {
				log.error("文件解析失败", e) ;
			}
		}
		return rowNum ;
	}
	
	
	/**
	 * 读取Excel内容
	 *
	 * @return
	 */
	public Map<Integer, List<Object []>> readExcelCpmtent() {
		Map<Integer, List<Object []>> content = new HashMap<Integer, List<Object []>>() ;
		if (inputStream != null) {
			try {
				int sheetNum = workbook.getNumberOfSheets() ;
				for (int i = 0; i < sheetNum; i++) {
					List<Object []> sheetContent = new ArrayList<Object []>() ;
					sheet = workbook.getSheetAt(i) ;
					int rowNum = sheet.getLastRowNum() ;
					row = sheet.getRow(0) ;
					if (row != null) {
						int colNum = row.getPhysicalNumberOfCells() ;
						// 正文内容应该从第二行开始，第一行为表头的标题
						for (int j = 0; j < rowNum; j++) {
							row = sheet.getRow(j) ;
							if (row != null) {
								List<String>	values	= new ArrayList<String>() ;
								int				s		= 0 ;
								while (s < colNum) {
									values.add(getStringCellValue(row.getCell(s)).trim()) ;
									s++ ;
								}
								sheetContent.add(values.toArray()) ;
							}
						}
						content.put(i, sheetContent) ;
					}
				}
			} catch (Exception e) {
				log.error("Excel文件解析失败！", e) ;
			}
		}
		return content ;
	}
	
	
	/**
	 * 读取Excel内容
	 *
	 * @param sheetAt
	 * @return
	 */
	public List<Object []> readExcelCpmtent(int sheetAt) {
		List<Object []> content = new ArrayList<Object []>() ;
		if (inputStream != null) {
			try {
				sheet = workbook.getSheetAt(sheetAt) ;
				// 得到总行数
				int rowNum = sheet.getLastRowNum() ;
				row = sheet.getRow(0) ;
				if (row != null) {
					int colNum = row.getPhysicalNumberOfCells() ;
					for (int i = 0; i <= rowNum; i++) {
						row = sheet.getRow(i) ;
						List<String>	values	= new ArrayList<String>() ;
						int				j		= 0 ;
						while (j < colNum) {
							values.add(getStringCellValue(row.getCell(j)).trim()) ;
							j++ ;
						}
						content.add(values.toArray()) ;
					}
				}
			} catch (Exception e) {
				log.error("Excel文件解析失败！", e) ;
			}
		}
		return content ;
	}
	
	
	public boolean close() {
		boolean flag = true ;
		if (inputStream != null) {
			try {
				inputStream.close() ;
			} catch (Exception e) {
				flag = false ;
				log.error("文件流关闭失败，", e) ;
			}
		}
		return flag ;
	}
	
	
	/**
	 * 读取Excel数据内容
	 * @param is
	 * @return Map 包含单元格数据内容的Map对象
	 */
	@SuppressWarnings("deprecation")
	public List<List<String>> readExcelContent(InputStream is) {
		List<List<String>> content = new ArrayList<List<String>>() ;
		POIFSFileSystem fs ;
		HSSFWorkbook wb ;
		HSSFSheet sheet ;
		HSSFRow row ;
		String str = "" ;
		try {
			fs = new POIFSFileSystem(is) ;
			wb = new HSSFWorkbook(fs) ;
			sheet = wb.getSheetAt(0) ; // 得到总行数
			int rowNum = sheet.getLastRowNum() ;
			row = sheet.getRow(0) ;
			int colNum = row.getPhysicalNumberOfCells() ; // 正文内容应该从第二行开始,第一行为表头的标题
			for (int i = 1; i <= rowNum; i++) {
				row = sheet.getRow(i) ;
				int j = 0 ;
				List<String> list = new ArrayList<String>() ;
				while (j < colNum) { // 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
					// 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
					// str += getStringCellValue(row.getCell((short) j)).trim() +
					// "-";
					str = getCellFormatValue(row.getCell((short) j)).trim() ;
					list.add(str) ;
					j++ ;
				}
				content.add(list) ;
				str = "" ;
			}
		} catch (IOException e) {
			e.printStackTrace() ;
		}
		return content ;
	}
	
	
	/**
	 * 根据HSSFCell类型设置数据
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(HSSFCell cell) {
		String cellvalue = "" ;
		if (cell != null) { // 判断当前Cell的Type
			switch (cell.getCellType()) { // 如果当前Cell的Type为NUMERIC
				case HSSFCell.CELL_TYPE_NUMERIC:
				case HSSFCell.CELL_TYPE_FORMULA: { // 判断当前的cell是否为Date
					if (HSSFDateUtil.isCellDateFormatted(cell)) { // 如果是Date类型则，转化为Data格式
						// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
						// cellvalue = cell.getDateCellValue().toLocaleString();
						// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
						Date date = cell.getDateCellValue() ;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
						cellvalue = sdf.format(date) ;
					} // 如果是纯数字
					else { // 取得当前Cell的数值
						cellvalue = String.valueOf(cell.getNumericCellValue()) ;
					}
					break ;
				} // 如果当前Cell的Type为STRIN
				case HSSFCell.CELL_TYPE_STRING: // 取得当前的Cell字符串
					cellvalue = cell.getRichStringCellValue().getString() ;
					break ; // 默认的Cell值
				default:
					cellvalue = " " ;
			}
		} else {
			cellvalue = "" ;
		}
		return cellvalue ;
	}
	
	
	/**
	 * 
	 *
	 * @param args
	 */
	public static void main(String [] args) {
		try {
			// 读取Excel表格内容测试
			InputStream		is2			= new FileInputStream("") ;	// 文件路径
			POIExcelReader	excelReader	= new POIExcelReader(is2) ;
			// 第二个sheet行数
			System.out.println("总记录数：" + excelReader.getRowNum(1)) ;
			// Map<Integer, List<Object []>> content =
			// excelReader.readExcelCpmtent() ;
			List<Object []> singleContent = excelReader.readExcelCpmtent(0) ;
			excelReader.close() ;
			for (Object [] objs : singleContent) {
				for (Object obj : objs) {
					System.out.println(obj + "|") ;
				}
				System.out.println() ;
			}
		} catch (Exception e) {
			System.out.println("未找到指定的路径文件！") ;
			e.printStackTrace() ;
		}
	}
}
