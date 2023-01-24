import java.util.ArrayList;
import java.util.Set;

public class TapeHead {

    static Character currentSymbol;
    static State currentState;
    static int currentPosition = 0;
    static MultiWayTape<Character> tape;
    static Set<Transition> transitions;
    static int transitionsCompleted = 0;

    static Boolean read(Character c)
    {
        for (Transition t : transitions) {
//            if (c.equals("﹡")) System.out.println("aaaaaaaaaaaaaaaaaaa");
            if (t.symbolRead.equals(c) && t.originState == currentState) {
                System.out.println("----------");
                System.out.println("current State: " + currentState + ", Current Position:" + currentPosition);
                System.out.println("current transition " + t);
                tape.set(currentPosition, t.symbolReplaced);
                System.out.println("Tape: " + tape);
                switch (t.move) {
                    case Left -> {
                        currentPosition--;
                        if (tape.negativeSize() >= currentPosition) tape.addLeft(' ');
                    }
                    case Right -> {
                        currentPosition++;
                        if (tape.positiveSize() <= currentPosition) tape.addRight(' ');
                    }
                }
                currentState = t.nextState;
                transitionsCompleted++;
                if (currentState.property == State.Property.Accept) return true;
                if (currentState.property == State.Property.Reject) return false;
                return null;
            }
        }
        faliureMessage(c);
        return false;
    }

    static void faliureMessage(Character c)
    {
        System.out.println(
                "Failed\nstate " + currentState +
                "\nPosition " + currentPosition +
                "\nChar " + c +
                "\ntape " + tape.toString() +
                "\ntransitionsCompleted " + transitionsCompleted
        );
    }

    public TapeHead(String tapeString, State first, Set<Transition> transitionsSet)
    {
        tape = new MultiWayTape<Character>();
        for (Character c : tapeString.toCharArray()) tape.addRight(c);
        currentState = first;
        transitions = transitionsSet;
    }
}
