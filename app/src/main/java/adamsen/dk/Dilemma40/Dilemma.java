package adamsen.dk.Dilemma40;
/**
 * Created by Frederik on 11/25/2015.
 */

public class Dilemma {
    String titel;
    String desc;

    String[] options;
    int[] votes;

    public Dilemma(String titel, String desc, String[] options) {
        this.titel = titel;
        this.desc = desc;
        this.options = options;
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

}
