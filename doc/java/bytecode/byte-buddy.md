# Byte-Buddy

## [新增 Field & Method](../../training-base/src/main/java/com/windred/training/bytebuddy/method/MethodByteBuddyTest.java)

- MethodDelegation 的方式实现拦截器, 委托方法常用注解

| 注解 | 作用 |
| :---- | :---- |
| @Argument | 绑定单个参数 |
| @AllArguments | 绑定所有参数的数组 |
| @This | 当前被拦截的、动态生成的那个对象 |
| @DefaultCall | 调用默认方法而非super的方法 |
| @SuperCall | 用于调用父类版本的方法 |
| @RuntimeType | 可以用在返回值、参数上，提示ByteBuddy禁用严格的类型检查 |
| @Super | 当前被拦截的、动态生成的那个对象的父类对象 |
| @FieldValue | 注入被拦截对象的一个字段的值 |

## [Byte-Buddy 实现AOP]()
