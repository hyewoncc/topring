package springbook.service;

import java.lang.reflect.Proxy;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public class TxProxyFactoryBean implements FactoryBean<Object> {

    private final PlatformTransactionManager transactionManager;
    private final String pattern;
    private final Class<?> serviceInterface;
    private Object target;

    public TxProxyFactoryBean(final Object target,
                              final PlatformTransactionManager transactionManager,
                              final String pattern,
                              final Class<?> serviceInterface) {
        this.target = target;
        this.transactionManager = transactionManager;
        this.pattern = pattern;
        this.serviceInterface = serviceInterface;
    }

    @Override
    public Object getObject() throws Exception {
        TransactionHandler handler = new TransactionHandler(target, transactionManager, pattern);
        return Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{serviceInterface},
                handler);
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public void setTarget(final Object target) {
        this.target = target;
    }
}
