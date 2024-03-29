package com.github.mxsm.register.mananger;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import org.jetbrains.annotations.NotNull;

/**
 * @author mxsm
 * @Date 2021/7/8
 * @Since 1.0.0
 */
public class MagpieBridgeClusterMetaData implements Comparable<MagpieBridgeClusterMetaData>{

    /**
     * magpie bridge name
     */
    private String magpieBridgeNameClusterName;

    private final Set<String> magpieBridgeNameSet = new HashSet<>(32);

    private final TreeMap<Long/*magpieBridge ID*/, MagpieBridgeMetaData> magpieBridgeAddresses = new TreeMap<>();

    public MagpieBridgeClusterMetaData(String magpieBridgeNameClusterName) {
        this.magpieBridgeNameClusterName = magpieBridgeNameClusterName;
    }

    /**
     * Compares this object with the specified object for order.  Returns a negative integer, zero, or a positive
     * integer as this object is less than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure
     * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))} for all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception iff {@code y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
     * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for all {@code z}.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any class that implements the {@code
     * Comparable} interface and violates this condition should clearly indicate this fact.  The recommended language is
     * "Note: this class has a natural ordering that is inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
     * <i>signum</i> function, which is defined to return one of {@code -1},
     * {@code 0}, or {@code 1} according to whether the value of
     * <i>expression</i> is negative, zero, or positive, respectively.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than
     * the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it from being compared to this object.
     */
    @Override
    public int compareTo(@NotNull MagpieBridgeClusterMetaData o) {
        return 0;
    }

    public Set<String> getMagpieBridgeNameSet() {
        return magpieBridgeNameSet;
    }

    public String getMagpieBridgeNameClusterName() {
        return magpieBridgeNameClusterName;
    }

    public void setMagpieBridgeNameClusterName(String magpieBridgeNameClusterName) {
        this.magpieBridgeNameClusterName = magpieBridgeNameClusterName;
    }

    public TreeMap<Long, MagpieBridgeMetaData> getMagpieBridgeAddresses() {
        return magpieBridgeAddresses;
    }


}
