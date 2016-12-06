package com.jasonknife.apt;

import com.jasonknife.anno.handler.AnnotationHandler;
import com.jasonknife.anno.handler.ViewInjectHandler;
import com.sun.org.apache.xpath.internal.operations.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * Created by Administrator on 2016/12/5.
 */
@SupportedAnnotationTypes("com.jasonknife.annotation.ViewInjector")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ViewInjectorProcessor extends AbstractProcessor {
    //考虑到架构的灵活性问题，对应的注解，可以动态添加解析的处理类
    List<AnnotationHandler> mHandlers = new ArrayList<>();

    public synchronized void init(ProcessingEnvironment processingEnvironment){
        super.init(processingEnvironment);
    }

    private void registerHandlers(){
        mHandlers.add(new ViewInjectHandler());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //1.roundEnv一团乱麻->整理出一个map,  Map<String,List<VariableElement>>  key是完整的类名，value是带注解的属性集合
        ViewInjectHandler handler = new ViewInjectHandler();
        handler.attachProcessingEnv(processingEnv);

        //2、根据map生成辅助类

        Map<String, List<VariableElement>> map = handler.handleAnnotation(roundEnv);

        return false;
    }
}
