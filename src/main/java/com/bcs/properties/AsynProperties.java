package com.bcs.properties;

import com.bcs.asynconst.AsynConst;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = AsynConst.MAIN_PROPERTIES_PREFIX)
public class AsynProperties {

    /**
     * If enabled asyn.
     */
    private boolean enabled = true;

    /**
     * pointCut
     */
    private String pointCut = "com.bcs.annotation.AsynOperation";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPointCut() {
        return pointCut;
    }

    public void setPointCut(String pointCut) {
        this.pointCut = pointCut;
    }
}
