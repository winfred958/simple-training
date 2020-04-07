package com.winfred.training.netty.rpc.registry;

import com.winfred.training.netty.rpc.protocol.InvokeProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1. 根据包名将所有符合条件的class全部扫描出来
 * 2. 给每一个class起一个唯一的名字, 作为服务名, 保存在容器中
 * 3. 当客户的请求, 获取协议内容, {@link com.winfred.training.netty.rpc.protocol.InvokeProtocol}
 * 4. 去注册好的容器中找到符合条件的类, invoke
 * 5. 返回结果给客户端
 */
@Slf4j
public class RegistryHandler extends ChannelInboundHandlerAdapter {

    private List<String> classPaths = new ArrayList<>();
    private Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    /**
     * 触发读事件的回调
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result = null;
        InvokeProtocol invokeProtocol = (InvokeProtocol) msg;
        String className = invokeProtocol.getClassName();
        if (serviceMap.containsKey(className)) {
            Object service = serviceMap.get(className);
            Method method = service.getClass().getMethod(invokeProtocol.getMethodName(), invokeProtocol.getParameterTypes());
            result = method.invoke(service, invokeProtocol.getParameterValues());
        }
        ctx.write(result);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        ctx.close();
    }

    /**
     * 连接发生异常时的回调
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    }

    /**
     * 扫描指定包class
     * <p>
     * 正常情况下应该写到配置文件
     *
     * @param packageName
     */
    private void scanClass(String packageName) {
        URL url = Thread
                .currentThread()
                .getContextClassLoader()
                .getResource(StringUtils.replacePattern(packageName, "\\.", "/"));

        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                scanClass(packageName + "." + file.getName());
            } else {
                classPaths.add(packageName + "." + file.getName().replace(".class", ""));
            }
        }
    }

    private void doRegistry() {
        if (classPaths.isEmpty()) {
            return;
        }
        for (String classPath : classPaths) {
            try {
                Class<?> clazz = Class.forName(classPath);
                Class<?> clazzInterface = clazz.getInterfaces()[0];
                String interfaceName = clazzInterface.getName();
                serviceMap.put(interfaceName, clazz);
            } catch (ClassNotFoundException e) {
                log.error("", e);
            }
        }
    }
}
