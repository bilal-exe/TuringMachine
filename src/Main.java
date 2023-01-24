import java.util.*;

public class Main {

    static Set<Character> symbols = new HashSet<Character>();
    static Boolean readResult;
    static TapeHead tapeHead;

    public static void main(String[] args)
    {
        symbols.addAll(List.of('a'));
        State.parseStates(
                """
                        0,i
                        1
                        2
                        3
                        4
                        5
                        6
                        7
                        8
                        9,a"""
        );
        tapeHead = new TapeHead(
                "dddd﹡",
                State.getInitial(),
                Transition.parseTransitions(
                        """
                                0,x|y,x|y,r,0
                                0,d,x,r,1
                                0,b,y,r,2
                                0,﹡,﹡,l,7
                                1,b|d|y|x|﹡,b|d|y|x|﹡,r,1
                                1, ,d,l,3
                                3,d|b,d|b,l,3
                                3,﹡,﹡,l,4
                                4,x|y,x|y,l,4
                                4,d,x,r,1
                                4, , ,r,0
                                4,b,y,r,2
                                2,d|y|b|﹡,d|y|b|﹡,r,2
                                2, ,b,l,5
                                5,d|b,d|b,l,5
                                5,﹡,﹡,l,6
                                6,d|b|x|y,d|b|x|y,l,6
                                6, , ,r,0
                                7,x|y,x|y,l,7
                                7, , ,l,8
                                8,d|b|﹡,d|b|﹡,r,8
                                8, , ,r,9
                                """
                )
        );

        symbols.add(' ');
        // add symbols and states
        while (true) {
            readResult = TapeHead.read(TapeHead.tape.get(TapeHead.currentPosition));
            if (readResult != null) {
                if (readResult.equals(true)) {
                    System.out.println("Sucess");
                    System.out.println(TapeHead.tape);
                    System.exit(0);
                } else if (readResult.equals(false)) {
                    System.out.println("Fail");
                    System.out.println(TapeHead.tape);
                    System.exit(0);
                }
            }
        }
    }
}