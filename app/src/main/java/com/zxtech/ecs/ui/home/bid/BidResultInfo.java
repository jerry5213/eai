package com.zxtech.ecs.ui.home.bid;

import com.zxtech.ecs.model.BidAttachment;

import java.util.List;

/**
 * Created by syp523 on 2018/7/25.
 */

public class BidResultInfo {

    private boolean IsBidding;
    private String BidRemark;
    private List<BidAttachment> BidResultFiles;


    public boolean isBidding() {
        return IsBidding;
    }

    public void setBidding(boolean bidding) {
        IsBidding = bidding;
    }

    public String getBidRemark() {
        return BidRemark;
    }

    public void setBidRemark(String bidRemark) {
        BidRemark = bidRemark;
    }

    public List<BidAttachment> getBidResultFiles() {
        return BidResultFiles;
    }

    public void setBidResultFiles(List<BidAttachment> bidResultFiles) {
        BidResultFiles = bidResultFiles;
    }
}
