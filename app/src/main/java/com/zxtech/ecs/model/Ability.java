package com.zxtech.ecs.model;

/**
 * Created by syp523 on 2017/9/29.
 */

public class Ability {

    private String id;

    private String ability;

    public Ability() {
    }

    public Ability(String id, String ability) {
        this.id = id;
        this.ability = ability;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    @Override
    public String toString() {
        return ability;
    }
}
