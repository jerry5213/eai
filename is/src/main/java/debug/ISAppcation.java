package debug;

import com.zxtech.is.util.AppUtil;
import com.zxtech.module.common.base.BaseApplication;

/**
 * Created by syp523 on 2018/6/15.
 */

public class ISAppcation extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.init(this);
    }
}
