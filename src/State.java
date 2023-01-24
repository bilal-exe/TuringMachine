import java.util.*;

public class State {

    final Property property;
    private static State initial = null;
    private static Map<String, State> states = new HashMap<String,State>();
    private static boolean initialised = false;
    private static int statesCount = 0;
    private final int num;

    enum Property {
        None,
        Initial,
        Accept,
        Reject
    }

    @Override
    public String toString()
    {
         return "State number " + this.num + ", is accept " + (this.property == Property.Accept) + ", is inital " + (this.property == Property.Initial);
    }

    public static State getInitial()
    {
        return initial;
    }

    static void add(String name, Property property)
    {
        if (states.containsKey(name)) throw new Error("2 States with the same name");
        if (property == Property.Initial) {
            if (initial != null) throw new Error("Attempted 2 initial states");
            states.put(name, new State(property));
            initial = getStateByName(name);
        } else {
            states.put(name, new State(property));
        }
    }

    static void add(String name)
    {
        if (states.containsKey(name)) throw new Error("2 States with the same name");
        states.put(name, new State(Property.None));
    }

    static State getStateByName(String name)
    {
        if (states.containsKey(name)) return states.get(name);
        throw new Error("Looked for state that dosent exist: " + name);
    }

    public static String getNameByState(State value) {
        for (Map.Entry<String, State> entry : states.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        throw new Error("Name not found by state.");
    }

    static void parseStates(String statesString)
    {
        for (String s : statesString.split(System.lineSeparator())) {
            if (s.contains(",")) {
                String[] split = s.split(",");
                switch (split[1].toLowerCase()) {
                    case "i" -> add(split[0], Property.Initial);
                    case "a" -> add(split[0], Property.Accept);
                    case "r" -> add(split[0], Property.Reject);
                }
            } else add(s);
        }
    }

    protected State(Property property)
    {
        this.property = property;
        this.num = statesCount;
        ++statesCount;
    }
}
