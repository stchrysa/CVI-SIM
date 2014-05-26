package tools;

import java.util.Comparator;

import model.components.Link;

@SuppressWarnings("rawtypes")
public class LinkComparator implements Comparator {

	@Override
	public final int compare(Object a, Object b) {
		// TODO Auto-generated method stub
		int x = ((Link)a).getId() ;
		int y= ((Link)b).getId() ;
		return Integer.valueOf(x).compareTo(Integer.valueOf(y));
	}

}

