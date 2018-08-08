package com.zxtech.ecs.model;

import com.zxtech.ecs.util.AppUtil;

import java.util.List;

import static com.zxtech.ecs.common.Constants.LANGUAGE_EN;

/**
 * Created by syp523 on 2018/5/4.
 */

public class CompanyProduct {


    /**
     * Result : true
     * ResultInfo : [{"SeatName":"客梯","SeatName_En":"Passenger Lift","Order":"1","CompanyInfoList":[{"Title":"客梯","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2A1_1.jpg","FileType":"pdf","Position":"1","FileInfoList":[{"FileName":"ComTag2A1_1_2.pdf","Path":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2A1_1_2.pdf","Describe":null,"OrderId":"1","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2A1_1.jpg","CreateTime":"2018-05-04T16:33:57.0639427+08:00"}],"Title_En":null},{"Title":"观光、医用梯","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2A1_1.jpg","FileType":"pdf","Position":"2","FileInfoList":[{"FileName":"ComTag2A1_2_2.pdf","Path":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2A1_2_2.pdf","Describe":null,"OrderId":"1","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2A1_1.jpg","CreateTime":"2018-05-04T16:33:57.0639427+08:00"}],"Title_En":null}]},{"SeatName":"别墅梯","SeatName_En":"Home Lift","Order":"2","CompanyInfoList":[{"Title":"别墅电梯","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2B1_1.jpg","FileType":"pdf","Position":"1","FileInfoList":[{"FileName":"ComTag2B1_1_2.pdf","Path":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2B1_1_2.pdf","Describe":null,"OrderId":"1","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2B1_1.jpg","CreateTime":"2018-05-04T16:33:57.0649192+08:00"}],"Title_En":null}]},{"SeatName":"货梯","SeatName_En":"Dumb waiter","Order":"3","CompanyInfoList":[{"Title":"杂物电梯","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2B1_1.jpg","FileType":"pdf","Position":"1","FileInfoList":[{"FileName":"ComTag2C1_1_2.pdf","Path":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2C1_1_2.pdf","Describe":null,"OrderId":"1","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2B1_1.jpg","CreateTime":"2018-05-04T16:33:57.0658957+08:00"}],"Title_En":null}]},{"SeatName":"扶梯","SeatName_En":"Escalator","Order":"4","CompanyInfoList":[{"Title":"扶梯、人行道","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2B1_1.jpg","FileType":"pdf","Position":"1","FileInfoList":[{"FileName":"ComTag2D1_1_2.pdf","Path":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2D1_1_2.pdf","Describe":null,"OrderId":"1","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2B1_1.jpg","CreateTime":"2018-05-04T16:33:57.0658957+08:00"}],"Title_En":null}]},{"SeatName":"其他","SeatName_En":"Others","Order":"6","CompanyInfoList":[{"Title":"装饰顶","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2F1_1.jpg","FileType":"jpg","Position":"1","FileInfoList":[{"FileName":"ComTag2F1_1_1.jpg","Path":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2F1_1_1.jpg","Describe":null,"OrderId":"1","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2F1_1.jpg","CreateTime":"2018-05-04T16:33:57.0668722+08:00"}],"Title_En":null},{"Title":"地板","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2F1_2.jpg","FileType":"jpg","Position":"2","FileInfoList":[{"FileName":"ComTag2F1_2_1.jpg","Path":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2F1_2_1.jpg","Describe":null,"OrderId":"1","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2F1_2.jpg","CreateTime":"2018-05-04T16:33:57.0668722+08:00"}],"Title_En":null},{"Title":"操纵盘","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2F1_3.jpg","FileType":"jpg","Position":"3","FileInfoList":[{"FileName":"ComTag2F1_3_1.jpg","Path":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2F1_3_1.jpg","Describe":null,"OrderId":"1","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2F1_3.jpg","CreateTime":"2018-05-04T16:33:57.0668722+08:00"}],"Title_En":null}]}]
     * ResultMessage :
     */

    private boolean Result;
    private String ResultMessage;
    private List<ResultInfoBean> ResultInfo;

    public boolean isResult() {
        return Result;
    }

    public void setResult(boolean Result) {
        this.Result = Result;
    }

    public String getResultMessage() {
        return ResultMessage;
    }

    public void setResultMessage(String ResultMessage) {
        this.ResultMessage = ResultMessage;
    }

    public List<ResultInfoBean> getResultInfo() {
        return ResultInfo;
    }

    public void setResultInfo(List<ResultInfoBean> ResultInfo) {
        this.ResultInfo = ResultInfo;
    }

    public static class ResultInfoBean {
        /**
         * SeatName : 客梯
         * SeatName_En : Passenger Lift
         * Order : 1
         * CompanyInfoList : [{"Title":"客梯","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2A1_1.jpg","FileType":"pdf","Position":"1","FileInfoList":[{"FileName":"ComTag2A1_1_2.pdf","Path":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2A1_1_2.pdf","Describe":null,"OrderId":"1","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2A1_1.jpg","CreateTime":"2018-05-04T16:33:57.0639427+08:00"}],"Title_En":null},{"Title":"观光、医用梯","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2A1_1.jpg","FileType":"pdf","Position":"2","FileInfoList":[{"FileName":"ComTag2A1_2_2.pdf","Path":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2A1_2_2.pdf","Describe":null,"OrderId":"1","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2A1_1.jpg","CreateTime":"2018-05-04T16:33:57.0639427+08:00"}],"Title_En":null}]
         */

        private String SeatName;
        private String SeatName_En;
        private String Order;
        private List<CompanyInfoListBean> CompanyInfoList;

        public String getSeatName() {
            if (LANGUAGE_EN.equals(AppUtil.getAppLanguage())) {
                return SeatName_En;
            }
            return SeatName;
        }

        public void setSeatName(String SeatName) {
            this.SeatName = SeatName;
        }

        public String getSeatName_En() {
            return SeatName_En;
        }

        public void setSeatName_En(String SeatName_En) {
            this.SeatName_En = SeatName_En;
        }

        public String getOrder() {
            return Order;
        }

        public void setOrder(String Order) {
            this.Order = Order;
        }

        public List<CompanyInfoListBean> getCompanyInfoList() {
            return CompanyInfoList;
        }

        public void setCompanyInfoList(List<CompanyInfoListBean> CompanyInfoList) {
            this.CompanyInfoList = CompanyInfoList;
        }

        public static class CompanyInfoListBean {
            /**
             * Title : 客梯
             * CoverPath : http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2A1_1.jpg
             * FileType : pdf
             * Position : 1
             * FileInfoList : [{"FileName":"ComTag2A1_1_2.pdf","Path":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2A1_1_2.pdf","Describe":null,"OrderId":"1","CoverPath":"http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2A1_1.jpg","CreateTime":"2018-05-04T16:33:57.0639427+08:00"}]
             * Title_En : null
             */

            private String Title;
            private String CoverPath;
            private String FileType;
            private String Position;
            private Object Title_En;
            private List<FileInfoListBean> FileInfoList;

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getCoverPath() {
                return CoverPath;
            }

            public void setCoverPath(String CoverPath) {
                this.CoverPath = CoverPath;
            }

            public String getFileType() {
                return FileType;
            }

            public void setFileType(String FileType) {
                this.FileType = FileType;
            }

            public String getPosition() {
                return Position;
            }

            public void setPosition(String Position) {
                this.Position = Position;
            }

            public Object getTitle_En() {
                return Title_En;
            }

            public void setTitle_En(Object Title_En) {
                this.Title_En = Title_En;
            }

            public List<FileInfoListBean> getFileInfoList() {
                return FileInfoList;
            }

            public void setFileInfoList(List<FileInfoListBean> FileInfoList) {
                this.FileInfoList = FileInfoList;
            }

            public static class FileInfoListBean {
                /**
                 * FileName : ComTag2A1_1_2.pdf
                 * Path : http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2A1_1_2.pdf
                 * Describe : null
                 * OrderId : 1
                 * CoverPath : http://47.94.99.203:80/CompanyManage/CompanyFile/ComTag2A1_1.jpg
                 * CreateTime : 2018-05-04T16:33:57.0639427+08:00
                 */

                private String FileName;
                private String Path;
                private Object Describe;
                private String OrderId;
                private String CoverPath;
                private String CreateTime;

                public String getFileName() {
                    return FileName;
                }

                public void setFileName(String FileName) {
                    this.FileName = FileName;
                }

                public String getPath() {
                    return Path;
                }

                public void setPath(String Path) {
                    this.Path = Path;
                }

                public Object getDescribe() {
                    return Describe;
                }

                public void setDescribe(Object Describe) {
                    this.Describe = Describe;
                }

                public String getOrderId() {
                    return OrderId;
                }

                public void setOrderId(String OrderId) {
                    this.OrderId = OrderId;
                }

                public String getCoverPath() {
                    return CoverPath;
                }

                public void setCoverPath(String CoverPath) {
                    this.CoverPath = CoverPath;
                }

                public String getCreateTime() {
                    return CreateTime;
                }

                public void setCreateTime(String CreateTime) {
                    this.CreateTime = CreateTime;
                }
            }
        }
    }
}
