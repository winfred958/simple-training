package com.winfred.training.designpattern.structure.proxy.jdk;

import com.winfred.training.designpattern.structure.proxy.base.ProxyEntity;
import com.winfred.training.designpattern.structure.proxy.base.UserService;
import com.winfred.training.designpattern.structure.proxy.base.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class JdkDynamicProxyClient {
  
  @Test
  public void jdkProxyTest() {
    // jdk proxy 只支持被代理的对象必须要实现接口
    UserService userService = new UserServiceImpl();
    MyInvocationHandler myInvocationHandler = new MyInvocationHandler(userService);
    
    UserService proxy = (UserService) myInvocationHandler.getProxy();
    
    writeClassFile(proxy);
    
    ProxyEntity entity = new ProxyEntity();
    entity.setName("xxx");
    proxy.testBefore(entity);
    System.out.println("=====================");
    proxy.testAfter(entity);
    System.out.println("=====================");
    proxy.testAround(entity);
    System.out.println("=====================");
  }
  
  /**
   * 代理类字节码写入文件, 方便反编译查看
   *
   * @param proxy
   */
  private void writeClassFile(UserService proxy) {
    String classSimpleName = proxy.getClass().getSimpleName();
    byte[] bytes = ProxyGenerator.generateProxyClass(classSimpleName, new Class[]{UserService.class});
    
    //
    Path path = Paths.get("./").getFileSystem().getPath("src", "main", "resources", "proxy", "Proxy21.class");
    
    try (FileOutputStream fileOutputStream = new FileOutputStream(path.toFile(), false);) {
      fileOutputStream.write(bytes);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}
