package net.arver.oa;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import net.arver.oa.pojo.Config;
import net.arver.oa.service.ConfigService;
import net.arver.oa.vo.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

@EnableAsync
@SpringBootApplication
@ServletComponentScan
@Slf4j
public class OaServerApplication {

    /**
     * 文件临时存放地址.
     */
    @Value("${oa.image-folder}")
    private String imageFolder;

    /**
     * 系统配置mapper.
     */
    @Autowired
    private ConfigService configService;

    /**
     * 常量.
     */
    @Autowired
    private SystemConstants constants;

    public static void main(final String[] args) {
        SpringApplication.run(OaServerApplication.class, args);
    }

    /**
     * 初始化方法.
     */
    @PostConstruct
    public void init() {
        final List<Config> params = configService.searchAllParam();
        params.forEach(item -> {
            String key = item.getParamKey();
            key = StrUtil.toCamelCase(key);
            final String value = item.getParamValue();
            try {
                final Field field = constants.getClass().getDeclaredField(key);
                field.setAccessible(true);
                field.set(constants, value);
            } catch (final Exception e) {
                log.error("执行异常", e);
            }
        });
        new File(imageFolder).mkdirs();
    }

}
