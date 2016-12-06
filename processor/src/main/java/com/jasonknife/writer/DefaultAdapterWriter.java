package com.jasonknife.writer;

import com.jasonknife.annotation.ViewInjector;

import java.io.IOException;
import java.io.Writer;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;

/**
 * Created by Administrator on 2016/12/5.
 */

public class DefaultAdapterWriter extends AbstractWriter {
    public DefaultAdapterWriter(ProcessingEnvironment processingEnv){
        super(processingEnv);
    }
    protected void generateImport(Writer writer,InjectInfo info) throws IOException {
        writer.write("package "+info.packageName+";");
        writer.write("\n\n");
        writer.write("import com.jasonknife.adapter.InjectAdapter;");
        writer.write("\n");
        writer.write("import com.jasonknife.util.ViewFinder;");
        writer.write("\n\n\n");
        writer.write("/**this class is generated by JasonKnife*/");
        writer.write("\n");
        writer.write("public class "+info.newClassName+" implements InjectAdapter<");
        writer.write("\n\n");
        writer.write("public void injects("+info.className+" target){");
        writer.write("\n");

    }
    protected  void writeField(Writer writer, VariableElement element,InjectInfo info) throws IOException {
        ViewInjector viewInjector = element.getAnnotation(ViewInjector.class);
        int id = viewInjector.value();
        String fieldName = element.getSimpleName().toString();
        writer.write("target."+fieldName+" = ViewFinder.findViewById(target,"+id+");");
        writer.write("\n");
    }
    protected void writeEnd(Writer writer) throws IOException {
        writer.write("}");
        writer.write("\n\n\n");
        writer.write("}");
    }
}
