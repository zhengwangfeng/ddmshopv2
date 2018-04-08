/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年7月15日 下午1:54:17
 */
package com.tcsb.settleaccount.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年7月15日 下午1:54:17
 */
public class ExportSettleUtil implements java.io.Serializable {
	
	
	/** *序号*/
	@Excel(name="*序号")
	private int id;
	
	/** *付款方客户账号*/
	@Excel(name="*付款方客户账号",width=30)
	private String payerClientAccount;
	
	/** *付款方账户名称*/
	@Excel(name="*付款方账户名称",width=30)
	private String payerClientName;
	
	/** *收款方行别代码（01-本行 02-国内他行）*/
	@Excel(name="*收款方行别代码（01-本行 02-国内他行）",width=30)
	private String beneficiaryBankCode;
	
	/** *收款方客户账号*/
	@Excel(name="*收款方客户账号",width=30)
	private String beneficiaryAccount;
	
	/** *收款方账户名称*/
	@Excel(name="*收款方账户名称",width=30)
	private String beneficiaryName;
	
	/** 收款方开户行名称*/
	@Excel(name="收款方开户行名称",width=30)
	private String beneficiaryOpeningBankName;
	
	/** 收款方联行号*/
	@Excel(name="收款方联行号",width=30)
	private String beneficiaryLineNumber;
	
	/** 客户方流水号*/
	@Excel(name="客户方流水号",width=30)
	private String clientSerialNumber;
	
	/** *金额*/
	@Excel(name="*金额",width=10)
	private String settleMoney;
	
	/** *用途*/
	@Excel(name="*用途",width=10)
	private String purpose;
	
	/** 备注*/
	@Excel(name="备注",width=10)
	private String note;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	/** *付款方客户账号*/
	public String getPayerClientAccount() {
		return payerClientAccount;
	}
	/** *付款方客户账号*/
	public void setPayerClientAccount(String payerClientAccount) {
		this.payerClientAccount = payerClientAccount;
	}
	/** *付款方账户名称*/
	public String getPayerClientName() {
		return payerClientName;
	}
	/** *付款方账户名称*/
	public void setPayerClientName(String payerClientName) {
		this.payerClientName = payerClientName;
	}
	/** *收款方行别代码（01-本行 02-国内他行）*/
	public String getBeneficiaryBankCode() {
		return beneficiaryBankCode;
	}
	/** *收款方行别代码（01-本行 02-国内他行）*/
	public void setBeneficiaryBankCode(String beneficiaryBankCode) {
		this.beneficiaryBankCode = beneficiaryBankCode;
	}
	/** *收款方客户账号*/
	public String getBeneficiaryAccount() {
		return beneficiaryAccount;
	}
	/** *收款方客户账号*/
	public void setBeneficiaryAccount(String beneficiaryAccount) {
		this.beneficiaryAccount = beneficiaryAccount;
	}
	/** *收款方账户名称*/
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	/** *收款方账户名称*/
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	/** 收款方开户行名称*/
	public String getBeneficiaryOpeningBankName() {
		return beneficiaryOpeningBankName;
	}
	/** 收款方开户行名称*/
	public void setBeneficiaryOpeningBankName(String beneficiaryOpeningBankName) {
		this.beneficiaryOpeningBankName = beneficiaryOpeningBankName;
	}
	/** 收款方联行号*/
	public String getBeneficiaryLineNumber() {
		return beneficiaryLineNumber;
	}
	/** 收款方联行号*/
	public void setBeneficiaryLineNumber(String beneficiaryLineNumber) {
		this.beneficiaryLineNumber = beneficiaryLineNumber;
	}
	/**
	 * 客户方流水号
	 * 
	 */
	public String getClientSerialNumber() {
		return clientSerialNumber;
	}
	/**
	 * 客户方流水号
	 * 
	 */
	public void setClientSerialNumber(String clientSerialNumber) {
		this.clientSerialNumber = clientSerialNumber;
	}

	/** *金额
	 */
	public String getSettleMoney() {
		return settleMoney;
	}
	/** *金额
	 */
	public void setSettleMoney(String settleMoney) {
		this.settleMoney = settleMoney;
	}
	/**
	 * 用途
	 */
	public String getPurpose() {
		return purpose;
	}
	/**
	 * 用途
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	/**
	 * 备注
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	
	


	

}
