package pl.azebrow.harvest.utils;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class JxlsUtils {

    private static final String DATASOURCE_OBJECT_NAME = "ds";
    private static final String TARGET_CELL = "Report!A1";

    public byte[] generateReport(String template, Object datasource) {
        Context ctx = new Context();
        ctx.putVar(DATASOURCE_OBJECT_NAME, datasource);
        try (var is = new ClassPathResource(template).getInputStream();
             var os = new ByteArrayOutputStream()) {
            JxlsHelper.getInstance().processTemplateAtCell(is, os, ctx, TARGET_CELL);
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
