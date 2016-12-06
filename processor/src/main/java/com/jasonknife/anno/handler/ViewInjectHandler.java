package com.jasonknife.anno.handler;

import com.jasonknife.annotation.ViewInjector;
import com.jasonknife.util.AnnotationUtil;
import com.jasonknife.util.FileUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * Created by Administrator on 2016/12/5.
 */

public class ViewInjectHandler extends AnnotationHandler {
    ProcessingEnvironment mProcessingEnv;
    public void attachProcessingEnv(ProcessingEnvironment mProcessingEnv){
        this.mProcessingEnv= mProcessingEnv;
    }
    public Map<String,List<VariableElement>> handleAnnotation(RoundEnvironment roundEnv){
        Map<String,List<VariableElement>>annotatioinMap = new HashMap();
        //获取所有的ViewInject注解的元素
        Set<? extends Element> elementSet = roundEnv.getElementsAnnotatedWith(ViewInjector.class);
        for(Element element:elementSet){
            //注解的字段
            VariableElement variableElement = (VariableElement)element;
            //某一个字段所属的类的完整路径
            String className = getParentClassName(variableElement);
            //获取这个类上的所有的带有这个注解的字段
            List<VariableElement>cacheElements = annotatioinMap.get(className);
            if(cacheElements==null){
                cacheElements = new LinkedList<VariableElement>();
            }
            //将元素添加到该类型的对应的列表中
            cacheElements.add(variableElement);
            //以类的路径为key，字段列表为value，存入map
            annotatioinMap.put(className,cacheElements);
            //FileUtil.write(className,cacheElements);

        }
        return annotatioinMap;
    }

    /**
     * 获取某个字段所属的类的完整路径
     * @param varElement
     * @return
     */
    private String getParentClassName(VariableElement varElement){
        TypeElement typeElement = (TypeElement) varElement.getEnclosingElement();
        String packageName = AnnotationUtil.getPackageName(mProcessingEnv,typeElement);
        return packageName;
    }
}
