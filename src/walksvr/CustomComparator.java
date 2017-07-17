package walksvr;

import java.util.Comparator;

public class CustomComparator implements Comparator<meeting> {

	@Override
	public int compare(meeting o1, meeting o2) {
		return o2.start.compareTo(o1.start);
	}

}
