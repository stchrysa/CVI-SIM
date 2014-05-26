package tools;

import java.util.Comparator;

import model.components.Node;

@SuppressWarnings("rawtypes")
public class NodeComparator implements Comparator
{
@Override
public final int compare(Object a, Object b) {
	// TODO Auto-generated method stub
	int x = ((Node)a).getId() ;
	int y= ((Node)b).getId() ;
	return Integer.valueOf(x).compareTo(Integer.valueOf(y));
}
} 