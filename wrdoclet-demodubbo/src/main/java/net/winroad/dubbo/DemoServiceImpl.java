package net.winroad.dubbo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DemoServiceImpl implements DemoService {

	/**
	 * @egname name name
	 * @egvalue name Bill Gates
	 */
	public String sayHello(String name) {
		return "Hello " + name;
	}

	public List<String> sayHello(HashMap<String, LinkedList<Integer>> map) {
		return null;
	}
	
	public BankAlliance createBankAlliance() {
		return null;
	}
}
