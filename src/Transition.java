import java.util.HashSet;
import java.util.Set;

public class Transition {

    Character symbolRead;
    Character symbolReplaced;
    State originState;
    State nextState;
    Move move;

    @Override
    public String toString()
    {
        return
            "Transition\nOrigin State:" + originState.toString() +
            ", \n\tsymbolRead:" + symbolRead +
            ", \n\tsymbolReplaced:" + symbolReplaced +
            ", \n\tmove" + move.toString() +
            ", \n\tnextState" + nextState;
    }

    static Set<Transition> parseTransitions(String transitionString)
    {
        HashSet<Transition> transitionSet = new HashSet<>();
        Move m;
        for (String s : transitionString.split(System.lineSeparator())) {
            String[] args = s.split(",");
            if (args[3].equalsIgnoreCase("r")){ m = Move.Right;}
            else if (args[3].equalsIgnoreCase("l")) {m = Move.Left;}
            else throw new Error();
            if (args[1].contains("|")) {
                String[] symbolsRead = args[1].split("\\|");
                String[] symbolsReplaced = args[2].split("\\|");
                for (int i = 0; i < symbolsRead.length; i++) {
                    transitionSet.add(
                            new Transition(
                                    State.getStateByName(args[0]),
                                    symbolsRead[i].charAt(0),
                                    symbolsReplaced[i].charAt(0),
                                    m,
                                    State.getStateByName(args[4])
                            )
                    );
                }
            } else {
                transitionSet.add(
                        new Transition(
                                State.getStateByName(args[0]),
                                args[1].charAt(0),
                                args[2].charAt(0),
                                m,
                                State.getStateByName(args[4])
                        )
                );
            }
        }
        return transitionSet;
    }

    public Transition(State currentState, Character symbolRead, Character symbolReplaced, Move move, State nextState) {
        this.originState = currentState;
        this.symbolRead = symbolRead;
        this.symbolReplaced = symbolReplaced;
        this.move = move;
        this.nextState = nextState;
    }
}
