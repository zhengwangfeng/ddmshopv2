package org.jeecgframework.core.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;

/**
 * jimmy
 * 对 List 元素的多个属性进行排序的类
 */
@SuppressWarnings({ "unchecked" })
public class ListSorter {

	/**
	 * List 元素的多个属性进行排序。例如 ListSorter.sort(list, "name", "age")，则先按 name
	 * 属性排序，name 相同的元素按 age 属性排序。
	 * 
	 * @param list
	 *            包含要排序元素的 List
	 * @param properties
	 *            要排序的属性。前面的值优先级高。
	 *            默认的为升序
	 */
	public static <V> void sort(List<V> list, final String... properties) {
		Collections.sort(list, new Comparator<V>() {
			public int compare(V o1, V o2) {
				if (o1 == null && o2 == null)
					return 0;
				if (o1 == null)
					return -1;
				if (o2 == null)
					return 1;

				for (String property : properties) {
					Comparator c = new BeanComparator(property);
					int result = c.compare(o1, o2);
					if (result != 0) {
						return result;
					}
				}
				return 0;
			}
		});
	}
	
	public static <V> void sortDesc(List<V> list, final String... properties) {
		Collections.sort(list, new Comparator<V>() {
			public int compare(V o1, V o2) {
				if (o1 == null && o2 == null)
					return 0;
				if (o1 == null)
					return -1;
				if (o2 == null)
					return 1;

				for (String property : properties) {
					Comparator c = new BeanComparator(property);
					int result = c.compare(o1, o2);
					if (result != 0) {
						return result;
					}
				}
				return 0;
			}
		});
		Collections.reverse(list);
	}

	public static void main(String[] args) {
		/*List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = new ArrayList<>();
		TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity1 = new TcsbFullcutTemplateEntity();
		tcsbFullcutTemplateEntity1.setTotal(1000);
		tcsbFullcutTemplateEntity1.setDiscount(100);
		TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity2 = new TcsbFullcutTemplateEntity();
		tcsbFullcutTemplateEntity2.setTotal(400);
		tcsbFullcutTemplateEntity2.setDiscount(60);
		TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity4 = new TcsbFullcutTemplateEntity();
		tcsbFullcutTemplateEntity4.setTotal(400);
		tcsbFullcutTemplateEntity4.setDiscount(40);
		TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity3 = new TcsbFullcutTemplateEntity();
		tcsbFullcutTemplateEntity3.setTotal(200);
		tcsbFullcutTemplateEntity3.setDiscount(30);
		tcsbFullcutTemplateEntities.add(tcsbFullcutTemplateEntity1);
		tcsbFullcutTemplateEntities.add(tcsbFullcutTemplateEntity2);
		tcsbFullcutTemplateEntities.add(tcsbFullcutTemplateEntity3);
		tcsbFullcutTemplateEntities.add(tcsbFullcutTemplateEntity4);
		sortDesc(tcsbFullcutTemplateEntities, "total","discount");
		for (TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity : tcsbFullcutTemplateEntities) {
			System.out.println(tcsbFullcutTemplateEntity.getTotal()+"...."+tcsbFullcutTemplateEntity.getDiscount());
		}*/
	}
}
