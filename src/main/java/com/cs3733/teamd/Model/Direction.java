package com.cs3733.teamd.Model;

import com.cs3733.teamd.Model.Entities.Tag;

/**
 * Created by Me on 4/25/2017.
 */
public class Direction {
    public DirectionType directionType;
    public Tag nearbyTag;

    public Direction(DirectionType directionType, Tag nearbyTag) {
        this.directionType = directionType;
        this.nearbyTag = nearbyTag;
    }
}
