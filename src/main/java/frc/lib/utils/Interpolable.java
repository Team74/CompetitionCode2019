package frc.lib.utils;

/*
Interpolable is an interface used by an Interpolating Tree as the Value type. Given two end points and an
interpolation parameter on [0, 1], it calculates a new Interpolable representing the interpolated value.
*/

public interface Interpolable<T> {
    public T interpolate(T other, double x);
}