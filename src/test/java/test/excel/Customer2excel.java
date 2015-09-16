package test.excel;

import java.util.Date;
import java.util.regex.Pattern;

import com.tonto.common.base.annotation.Convert;
import com.tonto.common.base.annotation.Property;
import com.tonto.common.base.annotation.PropertyConvert;
import com.tonto.common.base.annotation.PropertyType;
import com.tonto.common.base.annotation.PropertyValidate;
import com.tonto.common.base.annotation.Validate;

public class Customer2excel {
	
    @Property(name="姓名",type=PropertyType.STRING,nullable=false,minLength=5)
    private String name;
    
	@Property(name="性别",type=PropertyType.STRING,nullable=false)
	@Convert(convert=GenderConvert.class)
    private Integer gender_fk;
	
	@Property(name="手机",type=PropertyType.STRING,regex="^1[3|4|5|8]\\d{9}$")
    private String cellPhone;
	
    @Property(name="出生日期",type=PropertyType.DATE,nullable=false)
    private Date dateOfBirth;
    
    @Property(name="证件类型",type=PropertyType.INTEGER,nullable=false)
    @Convert(convert=IdTypeConvert.class)
    private Integer idType_fk;
    
    @Property(name="证件号",type=PropertyType.STRING)
    @Validate(validate=IdValidate.class)
    private String idNumber;   
    
    @Property(name="固定电话",type=PropertyType.STRING,regex="^(\\d{3,4}-?)?\\d{7,8}$")
    private String telephone;
    
    @Property(name="职业",type=PropertyType.STRING)
    @Convert(convert=OccupationConvert.class)
    private Integer occupation_fk;
    
    @Property(name="工作单位",type=PropertyType.STRING)
    private String workPlace;    
    
    @Property(name="婚姻状况",type=PropertyType.STRING)
    @Convert(convert=MaritalConvert.class)
    private Integer maritalStatus_fk;
    
    @Property(name="紧急联系人",type=PropertyType.STRING)
    private String ecName;
    
    @Property(name="国籍",type=PropertyType.STRING)
    private String nationality_fk;
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGender_fk() {
		return gender_fk;
	}
	public void setGender_fk(Integer gender_fk) {
		this.gender_fk = gender_fk;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Integer getIdType_fk() {
		return idType_fk;
	}
	public void setIdType_fk(Integer idType_fk) {
		this.idType_fk = idType_fk;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Integer getOccupation_fk() {
		return occupation_fk;
	}
	public void setOccupation_fk(Integer occupation_fk) {
		this.occupation_fk = occupation_fk;
	}
	public String getWorkPlace() {
		return workPlace;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	public Integer getMaritalStatus_fk() {
		return maritalStatus_fk;
	}
	public void setMaritalStatus_fk(Integer maritalStatus_fk) {
		this.maritalStatus_fk = maritalStatus_fk;
	}
	public String getEcName() {
		return ecName;
	}
	public void setEcName(String ecName) {
		this.ecName = ecName;
	}
	public String getNationality_fk() {
		return nationality_fk;
	}
	public void setNationality_fk(String nationality_fk) {
		this.nationality_fk = nationality_fk;
	} 
	
	
	public static class GenderConvert implements PropertyConvert<Integer>{

		@Override
		public Integer convert(Object obj) {
			String str=(String) obj;
			if("男".equals(str))
				return 1;
			return 0;
		}
		
	}
	
	public static class IdTypeConvert implements PropertyConvert<Integer>{

		@Override
		public Integer convert(Object obj) {
			String str=(String) obj;
			if("身份证".equals(str))
				return 1;
			return 0;
		}
		
	}
	public static class OccupationConvert implements PropertyConvert<Integer>{

		@Override
		public Integer convert(Object obj) {
			String str=(String) obj;
			if("公务员".equals(str))
				return 1;
			return 0;
		}
		
	}
	public static class MaritalConvert implements PropertyConvert<Integer>{

		@Override
		public Integer convert(Object obj) {
			String str=(String) obj;
			if("未婚".equals(str))
				return 1;
			return 0;
		}
		
	}
	public static class IdValidate implements PropertyValidate{
		Pattern p=Pattern.compile("(^\\d{15}$)|(^\\d{17}(\\d|X|x)$)");
		@Override
		public boolean validate(Object obj, Object value) {
			Customer2excel c2e=(Customer2excel) obj;
			if(c2e.idType_fk==1)
			{
				return p.matcher(value.toString()).matches();
			}
			return true;
		}
		
	}
}
