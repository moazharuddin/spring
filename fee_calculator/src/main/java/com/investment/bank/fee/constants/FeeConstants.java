package com.investment.bank.fee.constants;

public class FeeConstants {
	
	public static final String CSV_TYPE = ".csv";
	public static final String XSLX_TYPE = ".xlsx";
	
	public static final String FILENAME = "/Sample_Data";
	public static final String PATHNAME = "classpath:static";
	
	public static final String FILE = PATHNAME + FILENAME + XSLX_TYPE;
	
	public enum Fees{
		Five_Hundred("500",500),Hundred("100",100),Fifty("Fifty",50),Ten("Ten",10);
		
		private String name;
		private int fee;
		Fees(String name, int fee){
			this.name = name;
			this.fee = fee;
		}
		public String getName() {
			return name;
		}
		public int getFee() {
			return fee;
		}
		
	}
	
	public enum Priority{
		Normal, High
	}
	
	public enum FileType{
		CSV("csv"), XSLX("xslx");
		
		private String value;
		FileType(String value){
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		
	}

}
