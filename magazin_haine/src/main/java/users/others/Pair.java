package users.others;

public class Pair<A,B extends Comparable<B>> implements Comparable<Pair<A,B>>{
    private A first;
    private B second;

    @Override
    public int compareTo(Pair<A, B> o) {
        return this.getSecond().compareTo(o.getSecond());
    }

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "("+first+", "+second+")";
    }

    public A getFirst() {
        return first;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public B getSecond() {
        return second;
    }

    public void setSecond(B second) {
        this.second = second;
    }



}
