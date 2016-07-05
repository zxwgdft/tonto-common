package test.word;

import java.util.Date;


/**
 * 私募基金合同信息
 * @author TontoZhou
 *
 */
public class FundInvestigationInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7743488958921171392L;
	
	
	/**
	 * ID
	 */
	private String id;
	
	
	/**
	 * 投资人外键
	 */
	private Long customer_fk;
	
	/**
	 * 投资人姓名
	 */
	private String name;
	
	/**
	 * 联系方式
	 */
	private String contactWay;
	
	/**
	 * 证件类型
	 */
	private String credentialsType;
	
	/**
	 * 证件号码
	 */
	private String credentialsNumber;
	
	/**
	 * 通讯地址
	 */
	private String contactAddress;
	
	
	/**
	 * 选择问题1
	 */
	private Integer selectQuestionOne;
	
	/**
	 * 选择问题2
	 */
	private Integer selectQuestionTwo;
	
	/**
	 * 选择问题3
	 */
	private Integer selectQuestionThree;
	
	/**
	 * 步骤
	 */
	private Integer step;
	
	
	private Date dateOfCreate;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	public String getCredentialsType() {
		return credentialsType;
	}

	public void setCredentialsType(String credentialsType) {
		this.credentialsType = credentialsType;
	}

	public String getCredentialsNumber() {
		return credentialsNumber;
	}

	public void setCredentialsNumber(String credentialsNumber) {
		this.credentialsNumber = credentialsNumber;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCustomer_fk() {
		return customer_fk;
	}

	public void setCustomer_fk(Long customer_fk) {
		this.customer_fk = customer_fk;
	}

	public Integer getSelectQuestionOne() {
		return selectQuestionOne;
	}

	public void setSelectQuestionOne(Integer selectQuestionOne) {
		this.selectQuestionOne = selectQuestionOne;
	}

	public Integer getSelectQuestionTwo() {
		return selectQuestionTwo;
	}

	public void setSelectQuestionTwo(Integer selectQuestionTwo) {
		this.selectQuestionTwo = selectQuestionTwo;
	}

	public Integer getSelectQuestionThree() {
		return selectQuestionThree;
	}

	public void setSelectQuestionThree(Integer selectQuestionThree) {
		this.selectQuestionThree = selectQuestionThree;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Date getDateOfCreate() {
		return dateOfCreate;
	}

	public void setDateOfCreate(Date dateOfCreate) {
		this.dateOfCreate = dateOfCreate;
	}
}
