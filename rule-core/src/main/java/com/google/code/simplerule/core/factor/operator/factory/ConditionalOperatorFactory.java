package com.google.code.simplerule.core.factor.operator.factory;

import com.google.code.simplerule.core.factor.ConditionalOperator;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * @EnglishDescription:ConditionalOperatorFactory
 * @ChineseDescription:条件操作类型工厂
 * @author:sunny
 * @since:2013-03-06
 * @version:1.0.0
 */
public class ConditionalOperatorFactory {

    private static final Logger logger = Logger.getLogger(ConditionalOperatorFactory.class);

    /**
     * 获取条件操作符数组
     * @param cls 操作符对应类Class对象
     * @return
     */
    public static ConditionalOperator[] get(Class... cls) {
        List<ConditionalOperator> list = new ArrayList<ConditionalOperator>();
        for (Class clss :cls){
            try {
                Object  obj = clss.newInstance();
                if (obj instanceof ConditionalOperator){
                    ConditionalOperator conditionalOperator = (ConditionalOperator)obj;
                    list.add(conditionalOperator);
                }
            } catch (InstantiationException e) {
                logger.info("ConditionalOperatorFactory | get | error | " + e.getMessage());
            } catch (IllegalAccessException e) {
                logger.info("ConditionalOperatorFactory | get | error | " + e.getMessage());
            }
        }
        return list.toArray(new ConditionalOperator[0]);
    }

}
