package net.arver.oa.config.xss;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 跨站脚本攻击请求包装.
 * @author leegvv
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 构造函数.
     * @param request 请求
     */
    public XssHttpServletRequestWrapper(final HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(final String name) {
        String value = super.getParameter(name);
        if (StrUtil.isNotBlank(value)) {
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(final String name) {
        final String[] values = super.getParameterValues(name);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                value = HtmlUtil.filter(value);
                values[i] = value;
            }
        }
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        final Map<String, String[]> map = super.getParameterMap();
        for (final String[] values : map.values()) {
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    value = HtmlUtil.filter(value);
                    values[i] = value;
                }
            }
        }
        return map;
    }

    @Override
    public String getHeader(final String name) {
        String value = super.getHeader(name);
        if (StrUtil.isNotBlank(value)) {
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ServletInputStream in = super.getInputStream();
        final InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8"));
        final BufferedReader buffer = new BufferedReader(reader);
        final StringBuffer body = new StringBuffer();
        String line = buffer.readLine();
        while (line != null) {
            body.append(line);
            line = buffer.readLine();
        }
        buffer.close();
        reader.close();
        in.close();
        String json = body.toString();
        if (StrUtil.isNotBlank(json)) {
            final JSONObject jsonObject = JSONUtil.parseObj(body.toString());
            final LinkedHashMap<String, Object> result = new LinkedHashMap<>();
            for (final Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                final String key = entry.getKey();
                final Object value = entry.getValue();
                if (value instanceof String) {
                    if (StrUtil.isNotBlank(value.toString())) {
                        result.put(key, HtmlUtil.filter(value.toString()));
                    }
                } else {
                    result.put(key, value);
                }
            }
            json = JSONUtil.toJsonStr(result);
        }

        final ByteArrayInputStream byteIn = new ByteArrayInputStream(json.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(final ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteIn.read();
            }
        };
    }
}
