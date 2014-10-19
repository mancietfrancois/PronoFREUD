package fr.freud.indexedlistview.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.freud.indexedlistview.adapter.Row;

/**
 * Created by François_2 on 19/10/2014.
 */
public abstract class Indexer {

    protected List<Object[]> alphabet = new ArrayList<Object[]>();
    protected HashMap<Object, Integer> sections = new HashMap<Object, Integer>();

    public List<Object[]> getAlphabet() {
        return alphabet;
    }

    public HashMap<Object, Integer> getSections() {
        return sections;
    }

    public abstract List<Row> buildAlphabet();
}
