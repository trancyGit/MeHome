package listener;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by renhui on 2017/3/2.
 */
//@Component
//@Order(value=1)
public class RedisStartRunner implements CommandLineRunner {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
//    @Autowired
//    private RedisTemplate redisTemplate;
    @Override
    public void run(String... args) throws Exception {
//        Set<String> keys = redisTemplate.keys("*");
//        redisTemplate.delete(keys);
//        log.info("ababababbbbbbbbbbbbbbbbbbbbbbb");

        String path =  ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String templatePath = path+"/static/html";
        log.info("======================");
        log.info(path);
        log.info("======================");



        Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
        cfg.setDirectoryForTemplateLoading(new File(templatePath));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);

        Map<Object,Object> root = new HashMap<Object,Object>();
        root.put("user", "Big Joe");
        Product latest = new Product();
        latest.setUrl("products/greenmouse.html");
        latest.setName("green mouse");
        root.put("latestProduct", latest);

        /* Get the template (uses cache internally) */
        Template temp = cfg.getTemplate("test.html");

        /* Merge data-model with template */
        Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);


    }
}
