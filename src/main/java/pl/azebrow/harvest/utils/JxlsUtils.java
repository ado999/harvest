package pl.azebrow.harvest.utils;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class JxlsUtils {

    public byte[] generateReport(String template, Object datasource) {
        Context ctx = new Context();
        ctx.putVar("ds", datasource);
        try (var is = new ClassPathResource(template).getInputStream();
             var os = new ByteArrayOutputStream()) {
            JxlsHelper.getInstance().processTemplateAtCell(is, os, ctx, "Result!A1");
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
