package com.jasonknife.writer;

import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;
import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;

/**
 * Created by Administrator on 2016/12/5.
 */

public abstract class AbstractWriter implements AdapterWriter {
    private ProcessingEnvironment mProcessingEnv;
    private Filer mFiler;

    public AbstractWriter(ProcessingEnvironment mProcessingEnv){
        this.mProcessingEnv =mProcessingEnv;
        this.mFiler = this.mProcessingEnv.getFiler();
    }
    @Override
    public void generate(Map<String, List<VariableElement>> map) {
        //模板方法
        Iterator<Map.Entry<String,List<VariableElement>>>iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,List<VariableElement>>entry = iterator.next();
            //属性集合
            List<VariableElement>cacheElements = entry.getValue();
            if(cacheElements==null || cacheElements.size()==0){
                continue;
            }
            InjectInfo info = createInjectInfo(cacheElements.get(0));

            //创建一个java源文件
            Writer writer = null;
            try {
                JavaFileObject javaFileObject = mFiler.createSourceFile(info.getFullClassName());
                writer = javaFileObject.openWriter();
                //头部
                generateImport(writer,info);
                //属性
                for(VariableElement variableElement :cacheElements){
                    writerField(writer,variableElement,info);
                }
                //尾部
                writerEnd(writer);
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }

    protected abstract void writerEnd(Writer writer);

    protected abstract void writerField(Writer writer, VariableElement variableElement, InjectInfo info);

    protected abstract void generateImport(Writer writer, InjectInfo info);

}
