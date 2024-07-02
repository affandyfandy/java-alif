# Assignment 3 - Lecture 7

## 3.4 Q: Diference between BeanFactory and ApplicationContext ?​

| ​Feature | BeanFactory | ApplicationContext |
| --- | --- | --- |
| **Container Type** | Basic container | Advanced container |
| **Initialization** | Lazy initialized by default | Eager initialized by default |
| **Lightweight** | Yes | No (due to additional features) |
| **Customization** | Basic support for dependency injection and lifecycle management | Extensible with additional features and configuration options |
| **Core Interface** | Yes | Yes, extends BeanFactory |
| **Automatic Post-processing** | No | Yes |
| **Internationalization** | No | Yes, supports message resolution for localization |
| **Event Handling** | No | Yes, supports event propagation within the container |
| **Hierarchy Support** | No | Yes |
| **Common Implementations** | `XmlBeanFactory`, `DefaultListableBeanFactory` | `ClassPathXmlApplicationContext`, `AnnotationConfigApplicationContext` |
| **Suitable For** | Basic applications, resource-constrained environments | Most enterprise applications, larger systems |

### BeanFactory

BeanFactory is the most basic version of inversion of control containers.

1. Container type, BeanFactory provides basic mechanism for dependency injection and bean lifecycle management.
2. Lazy initialization, Beans are lazily initialized by default, means a bean created and its dependencies are injected only when requested via `getBean()` method.
3. Lightweight, BeanFactory is lightweight because it only initializes beans when needed.
4. Customization, BeanFactory provides extends and customization by impleemnting custom factories or extending existing implementations.
5. Core interface, it is the core interface for accessing and managing beans in Spring.
6. Commpon implementations including `XmlBeanFactory` and `DefaultListableBeanFactory`.
7. Suitable for basic application or environment with resource contstraints where lightweight and lazy init of beans are preferred.

### ApplicationContext

1. Container type, this extends the BeanFactory and provides more advanced container with additional enterprise features.
2. Eager initialization, beans are eagerly initialized by default, all singleton beans are created and their dependencies are injected at application startup unless marked as lazy.
3. Automatic post-processing, allowing more extensive configuration and customization of dependency injection.
4. Internationalization, provides messages based on locale-specific properties file or database resources.
5. Event handling, ApplicationContext supports event propagation within the container context, with communication between components through events and listeners.
6. Hieararchi support, where one ApplicationContext can be a parent of another.
7. Common implementations include `ClassPathXmlApplicationContext` and `AnnotationConfigApplicationContext`.
8. Suitable for most enterprise application and larger system where advanced features are required.
