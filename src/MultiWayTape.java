import java.util.*;

class MultiWayTape<T> implements List<T> {

    private final ArrayList<T> positiveEnd;
    private final ArrayList<T> negativeEnd;

    @Override
    public int size()
    {
        return negativeEnd.size() + positiveEnd.size();
    }

    @Override
    public boolean isEmpty()
    {
        return positiveEnd.isEmpty() && negativeEnd.isEmpty();
    }

    @Override
    public boolean contains(Object o)
    {
        return negativeEnd.contains(o) || positiveEnd.contains(o);
    }

    @Override
    public Iterator<T> iterator()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray()
    {
        ArrayList<T> full = new ArrayList<>();
        for (int i = negativeEnd.size() - 1; i > 0; i--) full.add(negativeEnd.get(i));
        full.addAll(positiveEnd);
        return full.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T t)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o)
    {
        Collections.reverse(negativeEnd);
        if (negativeEnd.remove(o)) {
            Collections.reverse(negativeEnd);
            return true;
        } else {
            Collections.reverse(negativeEnd);
            return positiveEnd.remove(o);
        }
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        for (Object o : c) if (!(negativeEnd.contains(o) || positiveEnd.contains(o))) return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        boolean changed = false;
        for (Object o : c) if (this.remove(o)) changed = true;
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        boolean changed = false;
        for (Object o : c)
            if (!negativeEnd.contains(o)) {
                changed = true;
                negativeEnd.remove(o);
            }
        for (Object o : c)
            if (!positiveEnd.contains(o)) {
                changed = true;
                positiveEnd.remove(o);
            }
        return changed;
    }

    public T get(int index)
    {
        return index >= 0 ? positiveEnd.get(index) : negativeEnd.get(-index - 1);
    }


    boolean addRight(T t)
    {
        return positiveEnd.add(t);
    }

    boolean addLeft(T t)
    {
        return negativeEnd.add(t);
    }

    public T set(int index, T element)
    {
//        return index >= 0 ? positiveEnd.set(index, element) : negativeEnd.set(Math.abs(++index), element);
        if (index >= 0) return positiveEnd.set(index, element);
        else return negativeEnd.set(-index-1, element);
    }

    @Override
    public void add(int index, T element)
    {
        if (index >= 0) positiveEnd.add(index, element);
        else negativeEnd.add(index, element);
    }

    @Override
    public T remove(int index)
    {
        if (index >= 0) return positiveEnd.remove(index);
        else return negativeEnd.remove(-index - 1);
    }

    @Override
    public int indexOf(Object o)
    {
        if (negativeEnd.contains(o)) return negativeEnd.lastIndexOf(o);
        else return positiveEnd.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o)
    {
        if (negativeEnd.contains(o)) return negativeEnd.indexOf(o);
        else return positiveEnd.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex)
    {
        MultiWayTape<T> sub = new MultiWayTape<>(fromIndex, toIndex);
        for (int i = negativeSize(); i < positiveSize(); i++) sub.set(i, this.get(i));
        return sub;
    }

    int positiveSize()
    {
        return positiveEnd.size();
    }

    int negativeSize()
    {
        return -(negativeEnd.size() + 1);
    }

    public void clear()
    {
        positiveEnd.clear();
        negativeEnd.clear();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o.getClass() != this.getClass()) return false;
        MultiWayTape m = (MultiWayTape) o;
        if (this.negativeSize() != m.negativeSize() || this.positiveSize() != m.positiveSize()) return false;
        for (int i = negativeSize(); i <= positiveSize(); i++) if (this.get(i) != m.get(i)) return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        return 0;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        MultiWayTape<T> clone = new MultiWayTape<>();
        for (int i = 0; i < negativeEnd.size(); i++) clone.addLeft(negativeEnd.get(i));
        for (int i = 0; i < positiveEnd.size(); i++) clone.addRight(positiveEnd.get(i));
        return clone;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = negativeEnd.size() - 1; i > 0; i--) sb.append(negativeEnd.get(i)).append(',');
        sb.append("(zero)");
        for (int i = 0; i < positiveEnd.size(); i++) sb.append(positiveEnd.get(i)).append(',');
        return sb.deleteCharAt(sb.length() - 1).append(']').toString();
    }

    public MultiWayTape()
    {
        negativeEnd = new ArrayList<>();
        positiveEnd = new ArrayList<>();
    }

    public MultiWayTape(int negSize, int posSize)
    {
        negativeEnd = new ArrayList<>(negSize);
        positiveEnd = new ArrayList<>(++posSize);
    }
}
