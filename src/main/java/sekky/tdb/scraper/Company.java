package sekky.tdb.scraper;

import java.io.Serializable;

/**
 * 会社情報
 *
 * @author sekky
 *
 */
public class Company implements Serializable {

	private static final long serialVersionUID = 725934507762849764L;

	private String code;
	private String name;
	private String address;
	private String type;

	public Company(String code, String name, String address, String type) {
		this.code = code;
		this.name = name;
		this.address = address;
		this.type = type;
	}


	public String getCode() {
		return code;
	}


	protected void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	protected void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	protected void setAddress(String address) {
		this.address = address;
	}


	public String getType() {
		return type;
	}


	protected void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "CompanyResult [code=" + code + ", name=" + name + ", address="
				+ address + ", type=" + type + "]";
	}
}
