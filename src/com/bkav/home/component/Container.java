package com.bkav.home.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.bkav.home.common.Attribute;
import com.bkav.home.common.Changable;
import com.bkav.home.common.Deletable;
import com.bkav.home.common.Element;
import com.bkav.home.common.Exportable;
import com.bkav.home.common.Importable;
import com.bkav.home.common.Process;
import com.bkav.home.common.Property;
import com.bkav.home.common.Unloadable;
import com.bkav.home.data.Data;
import com.bkav.home.data.DataBuilder;
import com.bkav.home.data.ValidString;

/**
 * Lớp cơ sở cho phép quản lý một nhóm các đối tượng bất kỳ, có thể duyệt qua
 * các đối tượng thông qua <code>iteractor</code>, có thể truy cập các đối 
 * tượng có tên thông qua tên. Lớp <code>Container</code> cài đặt các phương 
 * thức của <code>Process</code>, tự động gọi phương thức <code>process</code>
 * của tất cả các đối tượng có cài đặt giao diện <code>Process</code>.
 * <p>
 * Ngoài ra, <code>Container</code> còn cài đặt hầu hết các phương thức của các
 * giao diện <code>Exportable</code>, <code>Unloadable</code> cho phép các 
 * lớp mở rộng có thể thêm các tính chất này mà không cần thiết cài đặt các 
 * phương thức đó.
 * 
 * @author Phạm Quang Hoà
 *
 */
public class Container implements Process, Iterable<Object> {
	/**
	 * Khởi tạo đối tượng <code>Container</code> rỗng.
	 */
	public Container() {
		this.elements = new LinkedList<Object>();
		this.addingList = new ArrayList<Object>();
		this.removingList = new ArrayList<Object>();
		this.builder = null;
		this.processing = false;
	}
	
	/**
	 * Thêm một phần tử vào danh sách.
	 * 
	 * @param object Phần tử cần thêm.
	 */
	public void add(Object object) {
		this.elements.add(object);
	}
	
	public void safeAdd(Object object) {
		if (this.processing)
			this.addingList.add(object);
		else
			add(object);
	}

	/**
	 * Xoá một phần tử khỏi danh sách. Nếu phần tử là một thể hiện của
	 * <code>Unloadable</code>, thực hiện việc huỷ phần tử.
	 * @param object Phần tử cần xoá.
	 */
	public void remove(Object object) {
		if (this.elements.remove(object)) {
			if (object instanceof Unloadable)
				((Unloadable)object).unload();
			if (object instanceof Deletable)
				((Deletable)object).delete();
		}
	}
	
	public void safeRemove(Object object) {
		if (this.processing)
			this.removingList.add(object);
		else
			remove(object);
	}
	
	/**
	 * Trả về đối tượng có tên chỉ bởi <code>name</code>.
	 * 
	 * @param name Tên đối tượng.
	 * @return Đối tượng có tên tương ứng, hoặc <code>null</code> nếu không 
	 * tìm thấy.
	 */
	public Element getElement(String name) {
		for (Object element: this)
			if (element instanceof Element) {
				if (((Element)element).getName().equals(name))
					return (Element)element;
			}
		return null;
	}
	
	/**
	 * Trả về đối tượng <code>Iteractor</code> cho phép duyệt qua danh sách các
	 * đối tượng của container.
	 */
	@Override
	public Iterator<Object> iterator() {
		return new ObjectIteractor();
	}
	
	/**
	 * Thêm một thay đổi vào danh sách các thay đổi trong một lượt xử lý.
	 * @param name Tên đối tượng thay đổi.
	 * @param value Giá trị mới của đối tượng.
	 */
	public void addChange(String name, String value) {
		if (this.builder == null) {
			this.builder = new DataBuilder();
			this.builder.beginObject();
		}
		
		this.builder.addPair(ValidString.validate(name), ValidString.validate(value));
	}
	
	/**
	 * Thêm một thay đổi vào danh sách các thay đổi trong một lượt xử lý.
	 * @param name Tên đối tượng thay đổi.
	 * @param value Giá trị mới của đối tượng.
	 */
	public void addChange(String name, int value) {
		if (this.builder == null) {
			this.builder = new DataBuilder();
			this.builder.beginObject();
		}
		
		this.builder.addPair(ValidString.validate(name), value);
	}
	
	
	/**
	 * Xuất các thay đổi trong lượt xử lý gần nhất. Phương thức này tương thích
	 * với phương thức <code>exportChange</code> của giao diện <code>
	 * Changable</code>.
	 * 
	 * @return Xâu ký tự biểu diễn dữ liệu thay đổi trong lần xử lý gần nhất.
	 */
	public String exportChange() {
		for (Object object: this) {
			if (object instanceof Element) {
				Element element = (Element)object;
				if (element instanceof Changable) {
					String data = ((Changable)element).exportChange();
					if (data != null) {
						if (this.builder == null) {
							this.builder = new DataBuilder();
							this.builder.beginObject();
						}
						this.builder.addPair(ValidString.validate(element.getName()), data);
					}				
				}
			}
		}
		
		if (this.builder != null) {
			this.builder.endObject();		
			return this.builder.toString();
		}
		else
			return null;
	}
	
	public String exportAttributes() {
		DataBuilder builder = null;
		for (Object object: this) {
			String data = null;
			if (object instanceof Attribute) {
				data = ((Attribute)object).exportData();
				
				if (builder == null) {
					builder = new DataBuilder();
					builder.beginObject();
				}				
				builder.addPair(ValidString.validate(((Attribute)object).getName()), data);
			}
		}
		
		if (builder != null) {
			builder.endObject();
			return builder.toString();
		}
		else 
			return null;		
	}

	public String exportProperties() {
		DataBuilder builder = null;
		
		for (Object object: this) {
			String data = null;
			String name = null;
			
			if (object instanceof Property) {
				name = ((Property)object).getName();
				data = ((Property)object).exportData();
			}
			else if (object instanceof Container) {
				if (object instanceof Element) {
					name = ((Element)object).getName();
					data = ((Container)object).exportProperties();
				}
			}
			
			if (data != null) {
				if (builder == null) {
					builder = new DataBuilder();
					builder.beginObject();						
				}
				builder.addPair(ValidString.validate(name), data);
			}
		}
		
		if (builder != null) {
			builder.endObject();
			return builder.toString();
		}
		else 
			return null;
	}
	
	/**
	 * Xuất toàn bộ dữ liệu của đối tượng. Phương thức này tương thích
	 * với phương thức <code>exportData</code> của giao diện <code>
	 * Exportable</code>.
	 *  
	 * @return Xâu ký tự biểu diễn toàn bộ dữ liệu đối tượng.
	 */
	public String exportData() {
		DataBuilder builder = null;
		
		for (Object object: this.elements) {
			if (object instanceof Element) {
				Element element = (Element)object;
				if (element instanceof Exportable) {
					String data = ((Exportable)element).exportData();
					if (data != null) {
						if (builder == null) {
							builder = new DataBuilder();
							builder.beginObject();
						}
						builder.addPair(ValidString.validate(element.getName()), data);
					}				
				}
			}
		}
		
		if (builder != null) {
			builder.endObject();		
			return builder.toString();
		}
		else
			return null;
	}
	
	/**
	 * Nhập dữ liệu từ đối tượng dữ liệu. Phương thức này tương thích với 
	 * phương thức <code>importData</code> của giao diện
	 * <code>Importable</code>
	 * 
	 * @param data Đối tượng chứa dữ liệu cần nhập.
	 */
	public void importData(Data data) {
		String[] names = data.getNames();
		for (String name: names) {
			Element element = getElement(name);
			if (element instanceof Importable)
				((Importable)element).importData(data.getObject(name));
			else if (element == null) {
				element = createElement(name, data.getObject(name));
				if (element != null) {
					this.safeAdd(element);
					if (element instanceof Exportable) {
						String export = ((Exportable)element).exportData();
						if (export != null)
							addChange(ValidString.validate(name), export);
					}
				}
			}
		}
	}

	/**
	 * Cài đặt phương thức <code>process</code> của giao diện <code>Process
	 * </code>.<p>
	 * Duyệt qua các phần tử, thực hiện gọi phương thức <code>process</code>
	 * của các <code>Process</code>.
	 */
	@Override
	public void process() {
		this.builder = null;
		this.processing = true;
		for (Object element: this.elements) {
			if (element instanceof Process)
				((Process)element).process();
		}
		this.processing = false;
		
		for (Object object: this.addingList)
			add(object);
		this.addingList.clear();
		
		for (Object object: this.removingList)
			remove(object);
		this.removingList.clear();
	}
	
	/**
	 * Duyệt qua các phần tử, gọi phương thức <code>unload</code> của các
	 * <code>Unloadable</code>, tương thích với phương thức <code>unload</code> 
	 * của giao diện <code>Unloadable</code>.<p>
	 */
	public void unload() {
		for (Object element: this.elements) {
			if (element instanceof Unloadable)
				((Unloadable)element).unload();
		}
	}
	
	/**
	 * Tạo một đối tượng mới dựa vào tên và dữ liệu.
	 * 
	 * @param name Tên đối tượng được tạo.
	 * @param data Dữ liệu đối tượng được tạo.
	 */
	protected Element createElement(String name, Data data) {
		return null;
	}
	
	private LinkedList<Object> elements;
	private DataBuilder builder;
	private ArrayList<Object> addingList;
	private ArrayList<Object> removingList;
	private boolean processing;
	
	private class ObjectIteractor implements Iterator<Object> {
		public ObjectIteractor() {
			this.iteractor = Container.this.elements.iterator();
		}
		
		@Override
		public boolean hasNext() {
			return this.iteractor.hasNext();
		}

		@Override
		public Object next() {
			return this.iteractor.next();
		}

		@Override
		public void remove() {
			this.iteractor.remove();
		}
		
		private Iterator<Object> iteractor;
	}
}
