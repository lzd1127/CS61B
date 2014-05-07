// This buffer is for notes you don't want to save, and for Lisp evaluation.
// If you want to create a file, visit that file with C-x C-f,
// then enter the text in that file's own buffer.

public class XYZ {
    public static void main(String[] args) {
        Y.main(args);
    }
}

class X {

    public static final String BLEEP = "@*#&";
    public String forZ(int a) {
        return "X";
    }
    public static void awesome(int a) {
        System.out.println(a);
    }
}
interface Z {
    //public String forZ();    //PART 2A Compile
    //public int forZ();       //PART 2B DNC
    //private String forZ();   //PART 2C DNC
    public String forZ(int b); //PART 2D Runs and compiles
    public static final String BLEEP = "$$$";   //PART 3A runs and compiles
                                                //constant doesnt matter
    

}
class Y extends X implements Z{
    private static Y y = new Y();
    private static Y[] ya = new Y[2];

    public static void awesome(int a) {
        System.out.println(a*2);
    }

    public static  void main(String[] args) {
        X x = new X();
        X[] xa = new X[2];
        xa = ya;
        //ya=xa;          //incompatible types, COMPILE
        //ya = (Y) xa;    //incontrovertible types, COMPILE
        ya = ((Y[]) xa);  //PART 1A
        ya[0] = y;
        ya[1] = y;
        ya = ((Y[]) xa);  //PART 1B, runs
        xa = ya;          //PART 1B, runs and compiles
        //xa[0]=x;
        //xa[1]=x;
        xa = ya;
        xa[0]=y;
        xa[1]=y;
        xa = ya;    //PART 1C, yes and doesnt make a difference, b/c inside
                    //array doesnt matter the objects classes'
        //System.out.println(BLEEP);  //PART 3B DNC ambiguous reference to bleep
        System.out.println(Z.BLEEP);  //PART 3C Works! (so does X.BLEEP, btw)
        X.awesome(5);
        //((X) Y).awesome();
    }
}
