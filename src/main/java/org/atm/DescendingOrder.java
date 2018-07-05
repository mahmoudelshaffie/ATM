package org.atm;

import java.util.Comparator;

public class DescendingOrder implements Comparator<Short> {

	@Override
	public int compare(Short o1, Short o2) {
		int order = 0;
		if (o1 > o2)
			order = -1;
		else if (o1 < o2)
			order = 1;
		return order;
	}

}
