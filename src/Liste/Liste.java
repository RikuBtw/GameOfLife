package Liste;

import java.util.ArrayList;

public class Liste {
    private ArrayList<Object> liste = new ArrayList();

    public void add(Object object) {
        this.liste.add(object);
    }

    public Object get(int n) {
        try {
            Object object = this.liste.get(n);
            return object;
        }
        catch (IndexOutOfBoundsException var2_3) {
            return null;
        }
    }

    public boolean remove(Object object) {
        return this.liste.remove(object);
    }

    public int size() {
        return this.liste.size();
    }

    public String toString() {
        return this.liste.toString();
    }
}