package adamsen.dk.Dilemma40;
/**
 * Created by Frederik on 11/25/2015.
 */

public class Dilemma {
    String id;
    String titel;
    String desc;

    String[] options;
    int[] votes;

    public Dilemma(String titel, String desc, String[] options) {
        this.titel = titel;
        this.desc = desc;
        this.options = options;
        votes = new int[options.length];
    }

    public Dilemma(Dilemma clone) {

    }

    public Dilemma() {

    }



    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addVotes(int pos) {
        votes[pos]++;
    }

    public int[] getVotes(){
        return votes;
    }
}
