package com.www.utils.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * ʵ���๤��
 * @author www
 */
public class BeanUtils {
	
	/**
	 * ͨ�����佫mapת����bean����
	 * @param map
	 * @param bean
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object mapToObject(Map map,Object obj){
		try {
			if (obj instanceof List) {//objΪlist�಻����ת��
				return null;
			}
			if ((obj instanceof String)||(obj instanceof Integer)||(obj instanceof Double)||
					(obj instanceof Float)||(obj instanceof Date)||(obj instanceof Timestamp)||
					(obj instanceof BigDecimal)) {//objΪ��Щ�����ȡmap�ĵ�һ��ֵ
				obj=getMapFirstValue(map,obj);
				return obj;
			}
			if (obj instanceof Map) {//objΪmap������map�����ֵ
				((Map) obj).putAll(map);
				return obj;
			}
			//ͨ�������obj������и�ֵ
			Field[] fields = obj.getClass().getDeclaredFields();   
			for (Field field : fields) {    
				int mod = field.getModifiers();    
				if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){    
					continue;    
				}    
				field.setAccessible(true);    
				field.set(obj, map.get(field.getName().toUpperCase()));
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	/**
	 * �����Ƶ��¶���
	 * @param <T> Ŀ�������
	 * @param sourceObj Դ����
	 * @param toClass Ŀ�������
	 * @return Ŀ�����
	 */
	public static <T> T copyWithObject(Object sourceObj,Class<T> toClass){
		try {
			T targetObj = toClass.newInstance();
			copyWithObject(sourceObj, targetObj);
			return targetObj;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ������
	 * @param sourceObj Դ����
	 * @param targetObj Ŀ�����
	 */
	public static void copyWithObject(Object sourceObj,Object targetObj){
		try {
			if (sourceObj==null||(sourceObj instanceof String)||(sourceObj instanceof Integer)||(sourceObj instanceof List)||
					(sourceObj instanceof Double)||(sourceObj instanceof Float)||(sourceObj instanceof Date)||
					(sourceObj instanceof Timestamp)||(sourceObj instanceof BigDecimal)||(sourceObj instanceof Character)) {
				return;
			}
			if (targetObj==null||(targetObj instanceof String)||(targetObj instanceof Integer)||(targetObj instanceof List)||
					(targetObj instanceof Double)||(targetObj instanceof Float)||(targetObj instanceof Date)||
					(targetObj instanceof Timestamp)||(targetObj instanceof BigDecimal)||(targetObj instanceof Character)) {
				return;
			}
			Field[] souFields = sourceObj.getClass().getDeclaredFields();  
			Field[] tarFields = targetObj.getClass().getDeclaredFields();  
			for (Field sfield : souFields) {
				for (Field tfield : tarFields) {
					if (sfield.getName().toLowerCase().equals(tfield.getName().toLowerCase())&&
							sfield.getType().getName().equals(tfield.getType().getName())) {
						sfield.setAccessible(true);
						tfield.setAccessible(true);
						tfield.set(targetObj, sfield.get(sourceObj));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡmap��һ��ֵ
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Object getMapFirstValue(Map map,Object obj){
		Iterator<Entry<String,String>> iter = map.entrySet().iterator(); 
        if(iter.hasNext()){ 
            Entry<String,String> entry = iter.next(); 
            obj = entry.getValue(); 
            return obj;
        }
        return null;
	}
	/**
	 * �ж϶����Ƿ�Ϊ��
	 * @param obj
	 * @return true��Ϊ�գ�falseΪ��
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}
	/**
	 * �ж϶����Ƿ�Ϊ��
	 * @param obj
	 * @return trueΪ�գ�false��Ϊ��
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		if (obj instanceof String)
			return obj.toString().trim().length() == 0;
		if (obj instanceof Object[])
			return ((Object[]) obj).length == 0;
		if (obj instanceof Collection)
			return ((Collection) obj).isEmpty();
		if (obj instanceof Map)
			return ((Map) obj).isEmpty();
		else
			return false;
	}
}
