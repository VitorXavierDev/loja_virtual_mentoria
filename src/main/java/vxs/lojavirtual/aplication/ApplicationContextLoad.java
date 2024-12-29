package vxs.lojavirtual.aplication;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextLoad implements ApplicationContextAware{

	@Autowired
	private static ApplicationContext aplicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	
	}
	
	public static ApplicationContext getAplicationContext() {
		return aplicationContext;
	}

	
}
