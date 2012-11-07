package types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A list of types, Composite pattern.
 */
public class TypeList extends Type implements Iterable<Type> {
    
    /* The types in this list. */
    private List<Type> list;
    
    /**
     * Construct a new type list.
     */
    public TypeList() {
        list = new ArrayList<Type>();
    }
    
    /**
     * Append a given type to the list.
     * @param type The type to append to this type list.
     */
    public void append(Type type) {
        list.add(type);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TypeList(");
        for (int i = 0; i < (list.size() - 1); ++i) {
            sb.append(list.get(i) + ", ");
        }
        if (list.size() >= 1) {
            sb.append(list.get(list.size()-1));
        }
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public boolean equivalent(Type that) {
        if (that == null) {
            return false;
        } else if (!(that instanceof TypeList)) {
            return false;
        } else {
        	List<Type> olist = ((TypeList) that).list;
        	
        	if (list.size() != olist.size()) {
            	return false;
        	}
        	
        	for (int i = 0; i < list.size(); ++i) {
            	if (!list.get(i).equivalent(olist.get(i)))
                	return false;
        	}
        	return true;
        }
    }
    
    @Override
    public Iterator<Type> iterator() {
        return list.iterator();
    }
}
