package advent.of.code.util;

import java.util.Objects;

public class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
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

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Pair<?, ?> pair)) {
            return false;
        }

        return Objects.equals(getFirst(), pair.getFirst()) &&
               Objects.equals(getSecond(), pair.getSecond());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getFirst());
        result = 31 * result + Objects.hashCode(getSecond());
        return result;
    }

    @Override
    public String toString() {
        return "Pair{" +
               "first=" + first +
               ", second=" + second +
               '}';
    }
}
