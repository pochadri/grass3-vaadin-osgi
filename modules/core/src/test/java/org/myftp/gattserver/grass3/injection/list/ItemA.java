package org.myftp.gattserver.grass3.injection.list;

import org.springframework.stereotype.Component;

@Component("itemA")
public class ItemA implements ItemInterface {

	public String getName() {
		return "itemA";
	}

}
