package net.winroad.dubbo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAlliance<T extends Bank> extends BaseAlliance {
	private static final long serialVersionUID = 1L;
	
	private String allianceName;
	
	private List<T> bankList;
	
	private T leader;
}
