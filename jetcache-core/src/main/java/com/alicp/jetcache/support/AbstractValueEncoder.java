package com.alicp.jetcache.support;

import java.util.function.Function;

/**
 * Created on 2016/10/4.
 *
 * @author huangli
 */
public abstract class AbstractValueEncoder implements Function<Object, byte[]>, ValueEncoders {

    protected boolean useIdentityNumber;

    public AbstractValueEncoder(boolean useIdentityNumber) {
        this.useIdentityNumber = useIdentityNumber;
    }

    public boolean isUseIdentityNumber() {
        return useIdentityNumber;
    }
}
