package frc.lib.utils.geometry;

import frc.lib.utils.geometry.State;

public interface ITranslation2d<S> extends State<S> {
    public Translation2d getTranslation();
}