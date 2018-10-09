/*
 *
 * Copyright © 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 * Copyright © 2018  SZCA. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.bcia.javachain.ca.szca.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class SpringUtils implements BeanFactoryPostProcessor {

	private static ConfigurableListableBeanFactory beanFactory; // Spring应用上下文环境

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		SpringUtils.beanFactory = beanFactory;
	}

 
	/**
	* Description:
	* @param name
	* @return
	* @throws BeansException
	* Date: 2018年4月18日 下午3:21:06
	* Author:power
	* Version: 1.0
	*/
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) beanFactory.getBean(name);
	}

 
	public static <T> T getBean(Class<T> clz) throws BeansException {
 		T result = (T) beanFactory.getBean(clz);
		return result;
	}

 
	/**
	* Description: 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
	* @param name
	* @return
	* Date: 2018年4月18日 下午3:21:30
	* Author:power
	* Version: 1.0
	*/
	public static boolean containsBean(String name) {
		return beanFactory.containsBean(name);
	}

 
	/**
	* Description: 
	* 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
	* 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	* @param name
	* @return
	* @throws NoSuchBeanDefinitionException
	* Date: 2018年4月18日 下午3:21:41
	* Author:power
	* Version: 1.0
	*/
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.isSingleton(name);
	}

 
	/**
	* Description:
	* @param name
	* @return
	* @throws NoSuchBeanDefinitionException
	* Date: 2018年4月18日 下午3:21:58
	* Author:power
	* Version: 1.0
	*/
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getType(name);
	}
 
	/**
	* Description:果给定的bean名字在bean定义中有别名，则返回这些别名
	* @param name
	* @return
	* @throws NoSuchBeanDefinitionException
	* Date: 2018年4月18日 下午3:22:18
	* Author:power
	* Version: 1.0
	*/
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getAliases(name);
	}

}
