package abb.interview.model;

import java.util.HashMap;

public class Measurements  extends HashMap<Key, Measurement>{

    public Measurement find(Key key) {
        return get(key);
    }

}
