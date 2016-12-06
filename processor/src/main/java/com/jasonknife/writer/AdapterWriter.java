package com.jasonknife.writer;

import java.util.List;
import java.util.Map;

import javax.lang.model.element.VariableElement;

/**
 * Created by Administrator on 2016/12/5.
 */

public interface AdapterWriter {
    void generate(Map<String ,List<VariableElement>> map);
}
