package com.zxtech.ecs.model;

/**
 * Created by syp523 on 2018/4/9.
 */

public class DistributionElevator  {

    private String elevator;

    private String productId;

    private String position;//被分配的井道位置

    private boolean distribution;

    public boolean isDistribution() {
        return distribution;
    }

    public void setDistribution(boolean distribution) {
        this.distribution = distribution;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public DistributionElevator() {
    }

    public DistributionElevator(String elevator, String productId) {
        this.elevator = elevator;
        this.productId = productId;
    }
}
