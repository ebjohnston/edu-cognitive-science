import java.util.ArrayList;

public class Frame {
    String head;
    ArrayList<Frame> body;
    public Frame(String str, Frame... frames){
        head = str;
        body = new ArrayList();
        for(Frame f : frames){
            body.add(f);
        }
    }


    public static void main(String[] args){
        Frame scriptsList = new Frame("SCRIPTS",
                new Frame("shopping",
                        new Frame("PTRANS",
                                new Frame("ACTOR", new Frame("?shopper")),
                                new Frame("OBJECT", new Frame("?shopper")),
                                new Frame("TO", new Frame("?store"))),
                        new Frame("PTRANS",
                                new Frame("ACTOR", new Frame("?shopper")),
                                new Frame("OBJECT", new Frame("?bought")),
                                new Frame("TO", new Frame("?shopper"))),
                        new Frame("ATRANS",
                                new Frame("ACTOR", new Frame("?store")),
                                new Frame("OBJECT", new Frame("?bought")),
                                new Frame("FROM", new Frame("?store")),
                                new Frame("TO", new Frame("?shopper"))),
                        new Frame("ATRANS",
                                new Frame("ACTOR", new Frame("?shopper")),
                                new Frame("OBJECT", new Frame("money")),
                                new Frame("FROM", new Frame("?shopper")),
                                new Frame("TO", new Frame("?store"))),
                        new Frame("PTRANS",
                                new Frame("ACTOR", new Frame("?shopper")),
                                new Frame("OBJECT", new Frame("?shopper")),
                                new Frame("FROM", new Frame("?store")),
                                new Frame("TO", new Frame("?elsewhere")))));
        Frame kiteStory = new Frame("kiteStory",
                new Frame("PTRANS",
                        new Frame("ACTOR", new Frame("PERSON", new Frame
                                ("NAME", new Frame("jack")))),
                        new Frame("OBJECT", new Frame("PERSON", new Frame
                                ("NAME", new Frame("jack")))),
                        new Frame("TO", new Frame("store"))),
                new Frame("ATRANS",
                        new Frame("OBJECT", new Frame("kite")),
                        new Frame("TO", new Frame("person"))),
                new Frame("PTRANS",
                        new Frame("ACTOR", new Frame("person")),
                        new Frame("OBJECT", new Frame("person")),
                        new Frame("TO", new Frame("house"))));
    }

    public String toString(){
        String out = "("+this.head;
        if(this.body.size()>0){
            out+=":";
            for(Frame f : this.body){
                out+=f;
            }
        }
        out+=")";
        return out;
    }

    //match needs to be recursive. it needs to be same() as well as itself.
    public Frame match(Frame patt, Frame cons, Frame bl){
        //before doing anything check to make sure the structure of
        // cons and bl are correct.
        if(!bl.isBL()){
            System.out.println("improper bl");
            System.exit(0);
        }
        if(!cons.allCons()){
            System.out.println("improper cons");
            System.exit(0);
        }

        //handle trivial case where the headers dont match.
        if(!patt.simpleMatch(cons)){
            System.out.println("the pattern didnt match the constants");
            return null;
        }

        //at this point the only way we dont return the (updated) binding
        // list is if we get a conflict when adding a variable to the binding
        // list.

        Frame out = bl;

        return out;
    }


    public boolean inBL(Frame add){
        for(Frame bl : this.body){
            if(bl.head==add.head
                    &&bl.isLeaf()
                    &&add.isLeaf()
                    &&bl.body.get(0).simpleMatch(add.body.get(0)))
                return true;
        }
        return false;
    }

    public void add2BL(String h, String b){
        this.body.add(new Frame(h, new Frame(b)));
    }

    public boolean allCons(){
        if(this.isLeaf()){
            for(Frame f : this.body){
                if(f.isLeaf()
                        &&f.allCons()){
                    return false;
                }else{
                    return !f.isVar();
                }
            }
        }
        return true;
    }

    public boolean isVar(){
        return this.body.size()==0&&this.head.charAt(0)=='?';
    }

    public boolean isLeaf(){
        return this.body.size()==0;
    }

    public boolean simpleMatch(Frame f){
        return this.head==f.head;
    }

    public static boolean strIsVar(String s){
        return s.length()!=0&&s.charAt(0)=='?';
    }

    public boolean isBL(){
        for(Frame f : this.body){
            if(strIsVar(f.head)
                    &&!f.isLeaf()
                    &&f.body.get(0).isLeaf()
                    &&!f.body.get(0).isVar()
                    )
                return false;
        }
        return true;
    }

}
