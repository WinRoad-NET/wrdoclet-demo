package net.winroad.Models;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.winroad.beans.School;

import java.io.*;

/**
 * 学生
 * @author AdamsLi
 * @version 0.1
 * @memo init create
 */
public class Student extends Person {
	private static final long serialVersionUID = 6977402643848374753L;

	public String getSno() {
		return sno;
	}

	/**
	 * @author Bob
	 * @version 0.0.1
	 * @memo 添加学号字段
	 * 
	 * @author Adams
	 * @version 0.0.2
	 * @memo 修改为可选字段
	 * @occurs optional
	 * @param sno 学号
	 */
	public void setSno(String sno) {
		this.sno = sno;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	@NotNull
	@Valid
	private transient School school;
	
	@JsonProperty("studentNo")
	@NotEmpty(message = "sno should not be empty")
	private String sno;

	public String getTransientKey() {
		return transientKey;
	}

	public void setTransientKey(String transientKey) {
		this.transientKey = transientKey;
	}

	private transient String transientKey;

	public Boolean isBeautiful() {
		return good;
	}

	public void setBeautiful(Boolean good) {
		this.good = good;
	}

	private transient Boolean good;

	public static void main(String[] args) throws JsonProcessingException {
		Student student = new Student();
		student.setSno("1234");
		student.setTransientKey("2342432adafda");
		student.setBeautiful(true);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		System.out.println("jackson");
		System.out.println(ow.writeValueAsString(student));
		System.out.println("fastjson");
		System.out.println(JSON.toJSONString(student));
/*		try {
			ObjectOutputStream os = new ObjectOutputStream(
					new FileOutputStream("D:/student.txt"));
			os.writeObject(student);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(
					"D:/student.txt"));
			student = (Student) is.readObject(); // 从流中读取User的数据
			is.close();

			System.out.println("\nread after Serializable: ");
			System.out.println("transientKey: " + student.getTransientKey());
			System.out.println("good: " + student.isBeautiful());


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}*/
	}
}
