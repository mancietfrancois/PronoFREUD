package com.couchbase.grocerysync;

/**
 * Created by david on 04/08/14.
 */
public class Event {
    public enum Pronostic {
         DRAW, EXT, HOME, ;

        public Pronostic opposite() {
            if (this == DRAW) {
                return EXT;
            }
            else {
                return HOME;
            }
        }
    }


    private String m_home, m_visit;

    public Pronostic m_prono;

}
