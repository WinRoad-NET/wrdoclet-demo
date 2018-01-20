package net.winroad.dubbo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseAlliance implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	public String toString() {
		return null;
	}
}
