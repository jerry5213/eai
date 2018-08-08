package com.zxtech.ecs.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.util.AppUtil;

import java.util.List;

import static com.zxtech.ecs.common.Constants.LANGUAGE_EN;

/**
 * @auth: hexl
 * @date: 2018/2/6
 * @desc:
 */

public class CompanyEntity extends BaseResponse<CompanyEntity> implements Parcelable {

    /**
     * Result : true
     * ResultInfo : [{"Title":"北富推介书","CoverPath":"http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2B1_1.jpg","FileType":"pdf","Position":"1","FileInfoList":[{"FileName":"ComTag2B1_1_1.pdf","Path":"http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2B1_1_1.pdf","Describe":null,"OrderId":"1"}]}]
     * ResultMessage :
     */

    private boolean Result;
    private String ResultMessage;
    private List<ResultInfoBean> ResultInfo;

    protected CompanyEntity(Parcel in) {
        Result = in.readByte() != 0;
        ResultMessage = in.readString();
    }

    public static final Creator<CompanyEntity> CREATOR = new Creator<CompanyEntity>() {
        @Override
        public CompanyEntity createFromParcel(Parcel in) {
            return new CompanyEntity(in);
        }

        @Override
        public CompanyEntity[] newArray(int size) {
            return new CompanyEntity[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (Result ? 1 : 0));
        dest.writeString(ResultMessage);
    }

    public static class ResultInfoBean implements Parcelable {
        /**
         * Title : 北富推介书
         * CoverPath : http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2B1_1.jpg
         * FileType : pdf
         * Position : 1
         * FileInfoList : [{"FileName":"ComTag2B1_1_1.pdf","Path":"http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2B1_1_1.pdf","Describe":null,"OrderId":"1"}]
         */

        private String Title;
        private String Title_En;
        private String CoverPath;
        private String FileType;
        private String Position;
        private List<FileInfoListBean> FileInfoList;

        protected ResultInfoBean(Parcel in) {
            Title = in.readString();
            CoverPath = in.readString();
            FileType = in.readString();
            Position = in.readString();
        }

        public static final Creator<ResultInfoBean> CREATOR = new Creator<ResultInfoBean>() {
            @Override
            public ResultInfoBean createFromParcel(Parcel in) {
                return new ResultInfoBean(in);
            }

            @Override
            public ResultInfoBean[] newArray(int size) {
                return new ResultInfoBean[size];
            }
        };

        public String getTitle() {
            if (LANGUAGE_EN.equals(AppUtil.getAppLanguage()) && Title_En != null) {
                return Title_En;
            }
            return Title;
        }

        public String getTitle_En() {
            return Title_En;
        }

        public void setTitle_En(String title_En) {
            Title_En = title_En;
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

        public List<FileInfoListBean> getFileInfoList() {
            return FileInfoList;
        }

        public void setFileInfoList(List<FileInfoListBean> FileInfoList) {
            this.FileInfoList = FileInfoList;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(Title);
            dest.writeString(CoverPath);
            dest.writeString(FileType);
            dest.writeString(Position);
        }

        public static class FileInfoListBean implements Parcelable {
            /**
             * FileName : ComTag2B1_1_1.pdf
             * Path : http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2B1_1_1.pdf
             * Describe : null
             * OrderId : 1
             */

            private String FileName;
            private String Path;
            private String CoverPath;
            private String CreateTime;
            private String OrderId;

            protected FileInfoListBean(Parcel in) {
                FileName = in.readString();
                Path = in.readString();
                OrderId = in.readString();
                CoverPath = in.readString();
                CreateTime = in.readString();
            }

            public static final Creator<FileInfoListBean> CREATOR = new Creator<FileInfoListBean>() {
                @Override
                public FileInfoListBean createFromParcel(Parcel in) {
                    return new FileInfoListBean(in);
                }

                @Override
                public FileInfoListBean[] newArray(int size) {
                    return new FileInfoListBean[size];
                }
            };

            public String getCoverPath() {
                return CoverPath;
            }

            public void setCoverPath(String coverPath) {
                CoverPath = coverPath;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String createTime) {
                CreateTime = createTime;
            }

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

            public String getOrderId() {
                return OrderId;
            }

            public void setOrderId(String OrderId) {
                this.OrderId = OrderId;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(FileName);
                dest.writeString(Path);
                dest.writeString(OrderId);
                dest.writeString(CoverPath);
                dest.writeString(CreateTime);
            }
        }
    }
}
