package com.cl.common.lang.util;

import com.cl.common.log.Logger;
import com.cl.common.log.LoggerFactory;
import com.cl.common.log.LoggerImpl;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.sf.cglib.core.*;
import org.apache.commons.lang.StringUtils;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Type;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 属性复制工具类，通过使用cglib生成源代码，通过直接调用java方法set属性 <br/>
 * 并修复原代码没有setter时抛空指针异常的bug
 * 
 * <h3>Usage Examples</h3>
 * 
 * <pre>
 * {@code
 *  TestBean1 testBean1 = new TestBean1();
 *  BeanCopier.staticCopy(t, testBean1);
 *
 *  //忽略name,age属性
 *  TestBean1 testBean1 = new TestBean1();
 *  BeanCopier.staticCopy(t, testBean1,"name","age"); 
 * }
 * 
 * <pre>

 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
abstract public class BeanCopier {
	private static final Logger logger = LoggerFactory
		.getLogger("com.cl.common.lang.util.BeanCopier");
	private static final BeanCopierKey KEY_FACTORY = (BeanCopierKey) KeyFactory
		.create(BeanCopierKey.class);
	private static final Type CONVERTER = TypeUtils
		.parseType("net.sf.cglib.core.Converter");
	private static final Type BEAN_COPIER = TypeUtils
		.parseType("com.cl.common.lang.util.BeanCopier");
	private static final Signature COPY = new Signature("copy", Type.VOID_TYPE,
		new Type[] { Constants.TYPE_OBJECT, Constants.TYPE_OBJECT, CONVERTER });
	private static final Signature CONVERT = TypeUtils
		.parseSignature("Object convert(Object, Class, Object)");
	
	static {
		//摘要日志中不会调用栈信息,但是非摘要日志中要保证日志栈信息正常
		if (logger instanceof LoggerImpl) {
			((LoggerImpl) logger).setFqcn(BeanCopier.class.getName());
		}
	}
	
	interface BeanCopierKey {
		public Object newInstance(String source, String target, boolean useConverter);
	}
	
	public static BeanCopier create(Class source, Class target, boolean useConverter) {
		Generator gen = new Generator();
		gen.setSource(source);
		gen.setTarget(target);
		gen.setUseConverter(useConverter);
		return gen.create();
	}
	
	public static BeanCopier create(Class source, Class target, String... ignorePropeties) {
		Generator gen = new Generator();
		gen.setSource(source);
		gen.setTarget(target);
		gen.setUseConverter(false);
		gen.setIgnorePropeties(ignorePropeties);
		return gen.create();
	}
	
	abstract public void copy(Object from, Object to, Converter converter);
	
	public static class Generator extends AbstractClassGenerator {
		private static final Source SOURCE = new Source(BeanCopier.class.getName());
		private Class source;
		private Class target;
		private boolean useConverter;
		private String[] ignorePropeties;
		
		public Generator() {
			super(SOURCE);
		}
		
		public void setSource(Class source) {
			if (!Modifier.isPublic(source.getModifiers())) {
				setNamePrefix(source.getName());
			}
			this.source = source;
		}
		
		public void setTarget(Class target) {
			if (!Modifier.isPublic(target.getModifiers())) {
				setNamePrefix(target.getName());
			}
			
			this.target = target;
		}
		
		public String[] getIgnorePropeties() {
			return ignorePropeties;
		}
		
		public void setIgnorePropeties(String[] ignorePropeties) {
			this.ignorePropeties = ignorePropeties;
		}
		
		public void setUseConverter(boolean useConverter) {
			this.useConverter = useConverter;
		}
		
		protected ClassLoader getDefaultClassLoader() {
			return source.getClassLoader();
		}
		
		public BeanCopier create() {
			Object key = KEY_FACTORY.newInstance(source.getName(), target.getName(), useConverter);
			return (BeanCopier) super.create(key);
		}
		
		public void generateClass(ClassVisitor v) {
			Type sourceType = Type.getType(source);
			Type targetType = Type.getType(target);
			ClassEmitter ce = new ClassEmitter(v);
			ce.begin_class(Constants.V1_2, Constants.ACC_PUBLIC, getClassName(), BEAN_COPIER, null,
				Constants.SOURCE_FILE);
			
			EmitUtils.null_constructor(ce);
			CodeEmitter e = ce.begin_method(Constants.ACC_PUBLIC, COPY, null);
			PropertyDescriptor[] getters = ReflectUtils.getBeanGetters(source);
			PropertyDescriptor[] setters = ReflectUtils.getBeanGetters(target);
			
			Map names = new HashMap();
			for (int i = 0; i < getters.length; i++) {
				names.put(getters[i].getName(), getters[i]);
			}
			Local targetLocal = e.make_local();
			Local sourceLocal = e.make_local();
			if (useConverter) {
				e.load_arg(1);
				e.checkcast(targetType);
				e.store_local(targetLocal);
				e.load_arg(0);
				e.checkcast(sourceType);
				e.store_local(sourceLocal);
			} else {
				e.load_arg(1);
				e.checkcast(targetType);
				e.load_arg(0);
				e.checkcast(sourceType);
			}
			for (PropertyDescriptor setter : setters) {
                if (ignorePropeties != null) {
                    String propertyName = setter.getName();
                    if (ArrayUtil.contains(ignorePropeties, propertyName)) {
                        continue;
                    }
                }
				PropertyDescriptor getter = (PropertyDescriptor) names.get(setter.getName());
				if (getter == null) {
					logger.debug("[对象属性复制]原对象[{}.{}]getter方法不存在", source.getCanonicalName(),
                            setter.getName());
					continue;
				}
				Method readMethod = getter.getReadMethod();
				if (readMethod == null) {
					logger.debug("[对象属性复制]原对象[{}.{}]getter方法不存在", source.getCanonicalName(),
                            getter.getName());
					continue;
				}
				Method writeMethod = setter.getWriteMethod();
				if (writeMethod == null) {
					logger.debug("[对象属性复制]目标对象[{}.{}]setter方法不存在", target.getCanonicalName(),
                            setter.getName());
					continue;
				}
				MethodInfo read = ReflectUtils.getMethodInfo(readMethod);
				MethodInfo write = ReflectUtils.getMethodInfo(writeMethod);
				if (useConverter) {
					Type setterType = write.getSignature().getArgumentTypes()[0];
					e.load_local(targetLocal);
					e.load_arg(2);
					e.load_local(sourceLocal);
					e.invoke(read);
					e.box(read.getSignature().getReturnType());
					EmitUtils.load_class(e, setterType);
					e.push(write.getSignature().getName());
					e.invoke_interface(CONVERTER, CONVERT);
					e.unbox_or_zero(setterType);
					e.invoke(write);
				} else if (compatible(getter, setter)) {
					e.dup2();
					e.invoke(read);
					e.invoke(write);
				}
			}
			e.return_value();
			e.end_method();
			ce.end_class();
		}
		
		private static boolean compatible(PropertyDescriptor getter, PropertyDescriptor setter) {
			// TODO: allow automatic widening conversions?
			boolean match = setter.getPropertyType().isAssignableFrom(getter.getPropertyType());
			if (!match) {
				logger.debug("[对象属性复制]属性类型不匹配{}.{}({})->{}.{}({})", getter.getReadMethod()
					.getDeclaringClass().getSimpleName(), getter.getName(),
					getter.getPropertyType(), setter.getWriteMethod().getDeclaringClass()
						.getSimpleName(), setter.getName(), setter.getPropertyType());
			}
			return match;
		}
		
		protected Object firstInstance(Class type) {
			return ReflectUtils.newInstance(type);
		}
		
		protected Object nextInstance(Object instance) {
			return instance;
		}
		
	}
	
	private static Set<String> convertPropertiesToSetter(String[] ignoreProperties) {
		Set<String> set = Sets.newHashSet();
		for (String p : ignoreProperties) {
			if (StringUtils.isBlank(p)) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("set");
			sb.append(Character.toUpperCase(p.charAt(0)));
			sb.append(p.substring(1, p.length()));
			set.add(sb.toString());
		}
		return set;
	}
	
	private static final Map<Key, BeanCopier> copierMap = Maps.newConcurrentMap();
	
	/**
	 * 属性复制
	 * @param from 源对象
	 * @param to 目标对象
	 */
	public static void staticCopy(Object from, Object to) {
		staticCopy(from, to, (String) null);
	}
	
	/**
	 * 属性复制<br/>
	 * 不提供
	 * {@link org.springframework.beans.BeanUtils#copyProperties(Object, Object, String[])}
	 * 方法，因为用cglib来处理性能不是太好。<br/>
	 * 替代方式是可以通过静态方法newIgnorePropertiesConverter来生成忽略某些属性的Converter，
	 * 请缓存住converter
	 * @param from 源对象
	 * @param to 目标对象
	 * @param converter 属性转换器,可以用来处理某个属性值
	 */
	@Deprecated
	public static void staticCopy(Object from, Object to, Converter converter) {
		Assert.notNull(from, "源对象不能为空");
		Assert.notNull(to, "目标对象不能为空");
		boolean useConverter = converter != null;
		Key key = getKey(from, to, useConverter);
		if (!copierMap.containsKey(key)) {
			synchronized (BeanCopier.class) {
				if (!copierMap.containsKey(key)) {
					BeanCopier copy = BeanCopier.create(from.getClass(), to.getClass(),
						useConverter);
					copierMap.put(key, copy);
				}
			}
		}
		staticCopy(from, to, converter, key);
	}
	
	/**
	 * 属性复制<br/>
	 * 性能优于其他属性复制工具<br/>
	 * @param from 源对象
	 * @param to 目标对象
	 * @param ignorePropeties 忽略参数
	 */
	public static void staticCopy(Object from,  Object to,
									String... ignorePropeties) {
		Assert.notNull(from, "源对象不能为空");
		Assert.notNull(to, "目标对象不能为空");
		Key key = getKey(from, to, false, ignorePropeties);
		BeanCopier beanCopier = copierMap.get(key);
		if (beanCopier == null) {
			synchronized (BeanCopier.class) {
				beanCopier = copierMap.get(key);
				if (beanCopier == null) {
					BeanCopier copy = BeanCopier.create(from.getClass(), to.getClass(),
						ignorePropeties);
					copierMap.put(key, copy);
					beanCopier = copy;
				}
				if (beanCopier != null) {
					beanCopier.copy(from, to, null);
				}
			}
		} else {
			beanCopier.copy(from, to, null);
		}
	}
	
	/**
	 * 生成忽略某些属性的converter
	 * 
	 * @param ignoreProperties
	 * @return
	 * @deprecated 使用
	 */
	@Deprecated
	public static Converter newIgnorePropertiesConverter(String[] ignoreProperties) {
		final Set<String> set = convertPropertiesToSetter(ignoreProperties);
		return new Converter() {
			@Override
			public Object convert(Object value, @SuppressWarnings("rawtypes") Class target,
									Object context) {
				if (set.contains(context)) {
					return null;
				}
				return value;
			}
		};
	}
	
	private static void staticCopy(Object from, Object to, Converter converter, Key key) {
		BeanCopier copy = copierMap.get(key);
		copy.copy(from, to, converter);
	}
	
	private static Key getKey(Object from, Object to, boolean useConverter) {
		Class<?> fromClass = from.getClass();
		Class<?> toClass = to.getClass();
		return new Key(fromClass, toClass, useConverter);
	}
	
	private static Key getKey(Object from, Object to, boolean useConverter,
								String[] ignoreProperties) {
		Class<?> fromClass = from.getClass();
		Class<?> toClass = to.getClass();
		return new Key(fromClass, toClass, useConverter, ignoreProperties);
	}
	
	private static class Key {
		private Class<?> fromClass;
		private Class<?> toClass;
		private boolean useConverter;
		private String[] ignoreProperties;
		
		public Key(Class<?> fromClass, Class<?> toClass, boolean useConverter) {
			super();
			this.fromClass = fromClass;
			this.toClass = toClass;
			this.useConverter = useConverter;
		}
		
		public Key(Class<?> fromClass, Class<?> toClass, boolean useConverter,
					String[] ignoreProperties) {
			super();
			this.fromClass = fromClass;
			this.toClass = toClass;
			this.useConverter = useConverter;
			this.ignoreProperties = ignoreProperties;
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			
			Key key = (Key) o;
			
			if (useConverter != key.useConverter)
				return false;
			if (!fromClass.equals(key.fromClass))
				return false;
			if (!Arrays.equals(ignoreProperties, key.ignoreProperties))
				return false;
			if (!toClass.equals(key.toClass))
				return false;
			
			return true;
		}
		
		@Override
		public int hashCode() {
			int result = fromClass.hashCode();
			result = 31 * result + toClass.hashCode();
			result = 31 * result + (useConverter ? 1 : 0);
			result = 31 * result
						+ (ignoreProperties != null ? Arrays.hashCode(ignoreProperties) : 0);
			return result;
		}
	}
}
