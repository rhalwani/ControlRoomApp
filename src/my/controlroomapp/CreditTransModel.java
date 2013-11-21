package my.controlroomapp;

import java.util.ArrayList;

public class CreditTransModel {

    private final ArrayList<Object> rowEntry;

    CreditTransModel(String sender, String recipient, String evtdate, float balchng, float freeMonChng) {
        this.rowEntry = new ArrayList(5);
        this.rowEntry.add(sender);
        this.rowEntry.add(recipient);
        this.rowEntry.add(evtdate);
        this.rowEntry.add(Float.valueOf(balchng));
        this.rowEntry.add(Float.valueOf(freeMonChng));
    }

    public ArrayList<Object> getRowEntry() {
        return this.rowEntry;
    }

    public String getSender() {
        return (String) this.rowEntry.get(0);
    }

    public String getRecipient() {
        return (String) this.rowEntry.get(1);
    }

    public String getEvtdate() {
        return (String) this.rowEntry.get(2);
    }

    public Float getBalchng() {
        return (Float) this.rowEntry.get(3);
    }

    public Float getFreeMonChng() {
        return (Float) this.rowEntry.get(4);
    }
}
