package tonto.test.document.webservice;

/**
 * 电子合同
 * @author TontoZhou
 *
 */
public class ElectronicContract {
		
    private int id;
    
    private String name;// 文件名称
    
    private String originalName;// 文件原始名称
    
    private int size;// 文件大小
    
    private String contentType;// 文件类型
    
    private String suffix;// 文件后缀名
        
    private String contractStatus;
    
    //是否签署完成
	public int getIsSigned() {
		return "签署完成".equals(contractStatus)?1:0;
	}
	
	//文件URL
	public String getUrl() {
		return "method=jinbao.sys.attachment.file.get&id="+id;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getOriginalName() {
		return originalName;
	}


	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public String getSuffix() {
		return suffix;
	}


	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}


	public String getContractStatus() {
		return contractStatus;
	}


	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	

	
}
