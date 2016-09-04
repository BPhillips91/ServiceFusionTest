package brendan.servicefusiontest;

/**
 * Created by brendan on 8/30/16.
 */
public class Persons {

    String first;
    String last;
    String dob;
    String zip;

    private Persons() {
    }

    public Persons(String first, String last, String dob, String zip) {
        this.first = first;
        this.last = last;
        this.dob = dob;
        this.zip = zip;
    }

    public String getfirst() {
        return first;
    }

    public String getlast() {
        return last;
    }

    public String getdob() {
        return dob;
    }

    public String getzip() {
        return zip;
    }
}
