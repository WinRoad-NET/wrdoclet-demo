package net.winroad.Models;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Address implements Serializable {
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCounty() {
		return county;
	}

	/**
	 * @param county 县
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * 详细地址
	 * @return
	 */
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * 城市
	 */
	private String city;
	@NotNull(message = "不能为空")
	private String province;
	/**
	 * 县名
	 */
	private String county;
	
	/**
	 * 地址
	 */
	private String addr;

}
