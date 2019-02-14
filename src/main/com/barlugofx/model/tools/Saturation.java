package com.barlugofx.model.tools;

import com.barlugofx.model.imageTools.Image;
import com.barlugofx.model.tools.common.ImageFilterImpl;
import com.barlugofx.model.tools.common.ParametersName;


/**
 * A saturation class that allows to change an image saturation. It only accepts one parameter, saturation, which must be between
 * -255 and 255. Eventual other value will result in an {@link IllegalStateException}.
 *
 *
 */
public  final class Saturation extends ImageFilterImpl{

    @Override
    public Image applyFilter(final Image toApply) {
        return null;
    }

    @Override
    protected boolean isAccepted(final ParametersName name) {
        // TODO Auto-generated method stub
        return false;
    }

}
