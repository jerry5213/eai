package com.zxtech.esp.data.bean;

import java.util.List;

/**
 * Created by syp521 on 2018/4/23.
 */

public class Among {

    /**
     * result : [{"roll":-4.8921265602112,"qualities":{"illumination":99,"occlusion":{"right_eye":0,"nose":0,"mouth":0,"chin":0.0031474819406867,"left_eye":0,"left_cheek":0.013729977421463,"right_cheek":0.0086264098063111},"blur":1.1985361925326E-4,"completeness":1,"type":{"cartoon":0.0023459573276341,"human":0.99765402078629}},"location":{"top":75,"left":43,"width":61,"height":58},"face_probability":1,"rotation_angle":-4,"pitch":-1.1965211629868,"yaw":-2.4643912315369,"age":25}]
     * log_id : 2990373384042320
     * result_num : 1
     */

    private long log_id;
    private int result_num;
    private List<ResultBean> result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * roll : -4.8921265602112
         * qualities : {"illumination":99,"occlusion":{"right_eye":0,"nose":0,"mouth":0,"chin":0.0031474819406867,"left_eye":0,"left_cheek":0.013729977421463,"right_cheek":0.0086264098063111},"blur":1.1985361925326E-4,"completeness":1,"type":{"cartoon":0.0023459573276341,"human":0.99765402078629}}
         * location : {"top":75,"left":43,"width":61,"height":58}
         * face_probability : 1
         * rotation_angle : -4
         * pitch : -1.1965211629868
         * yaw : -2.4643912315369
         * age : 25
         */

        private double roll;
        private QualitiesBean qualities;
        private LocationBean location;
        private int face_probability;
        private int rotation_angle;
        private double pitch;
        private double yaw;
        private int age;

        public double getRoll() {
            return roll;
        }

        public void setRoll(double roll) {
            this.roll = roll;
        }

        public QualitiesBean getQualities() {
            return qualities;
        }

        public void setQualities(QualitiesBean qualities) {
            this.qualities = qualities;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public int getFace_probability() {
            return face_probability;
        }

        public void setFace_probability(int face_probability) {
            this.face_probability = face_probability;
        }

        public int getRotation_angle() {
            return rotation_angle;
        }

        public void setRotation_angle(int rotation_angle) {
            this.rotation_angle = rotation_angle;
        }

        public double getPitch() {
            return pitch;
        }

        public void setPitch(double pitch) {
            this.pitch = pitch;
        }

        public double getYaw() {
            return yaw;
        }

        public void setYaw(double yaw) {
            this.yaw = yaw;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public static class QualitiesBean {
            /**
             * illumination : 99
             * occlusion : {"right_eye":0,"nose":0,"mouth":0,"chin":0.0031474819406867,"left_eye":0,"left_cheek":0.013729977421463,"right_cheek":0.0086264098063111}
             * blur : 1.1985361925326E-4
             * completeness : 1
             * type : {"cartoon":0.0023459573276341,"human":0.99765402078629}
             */

            private int illumination;
            private OcclusionBean occlusion;
            private double blur;
            private int completeness;
            private TypeBean type;

            public int getIllumination() {
                return illumination;
            }

            public void setIllumination(int illumination) {
                this.illumination = illumination;
            }

            public OcclusionBean getOcclusion() {
                return occlusion;
            }

            public void setOcclusion(OcclusionBean occlusion) {
                this.occlusion = occlusion;
            }

            public double getBlur() {
                return blur;
            }

            public void setBlur(double blur) {
                this.blur = blur;
            }

            public int getCompleteness() {
                return completeness;
            }

            public void setCompleteness(int completeness) {
                this.completeness = completeness;
            }

            public TypeBean getType() {
                return type;
            }

            public void setType(TypeBean type) {
                this.type = type;
            }

            public static class OcclusionBean {
                /**
                 * right_eye : 0
                 * nose : 0
                 * mouth : 0
                 * chin : 0.0031474819406867
                 * left_eye : 0
                 * left_cheek : 0.013729977421463
                 * right_cheek : 0.0086264098063111
                 */

                private int right_eye;
                private int nose;
                private int mouth;
                private double chin;
                private int left_eye;
                private double left_cheek;
                private double right_cheek;

                public int getRight_eye() {
                    return right_eye;
                }

                public void setRight_eye(int right_eye) {
                    this.right_eye = right_eye;
                }

                public int getNose() {
                    return nose;
                }

                public void setNose(int nose) {
                    this.nose = nose;
                }

                public int getMouth() {
                    return mouth;
                }

                public void setMouth(int mouth) {
                    this.mouth = mouth;
                }

                public double getChin() {
                    return chin;
                }

                public void setChin(double chin) {
                    this.chin = chin;
                }

                public int getLeft_eye() {
                    return left_eye;
                }

                public void setLeft_eye(int left_eye) {
                    this.left_eye = left_eye;
                }

                public double getLeft_cheek() {
                    return left_cheek;
                }

                public void setLeft_cheek(double left_cheek) {
                    this.left_cheek = left_cheek;
                }

                public double getRight_cheek() {
                    return right_cheek;
                }

                public void setRight_cheek(double right_cheek) {
                    this.right_cheek = right_cheek;
                }
            }

            public static class TypeBean {
                /**
                 * cartoon : 0.0023459573276341
                 * human : 0.99765402078629
                 */

                private double cartoon;
                private double human;

                public double getCartoon() {
                    return cartoon;
                }

                public void setCartoon(double cartoon) {
                    this.cartoon = cartoon;
                }

                public double getHuman() {
                    return human;
                }

                public void setHuman(double human) {
                    this.human = human;
                }
            }
        }

        public static class LocationBean {
            /**
             * top : 75
             * left : 43
             * width : 61
             * height : 58
             */

            private int top;
            private int left;
            private int width;
            private int height;

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }
}
