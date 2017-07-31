package com.bkav.home.data;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.ParseException;
import java.util.HashMap;

/**
 * Phân tích chuỗi biểu diễn đối tượng dữ liệu.
 * 
 * @author Phạm Quang Hoà
 *
 */
public class DataParser {
	/**
	 * Phân tích xâu ký tự biểu diễn đối tượng dữ liệu.
	 * 
	 * @param content Xâu ký tự cần phân tích.
	 * @return Đối tượng <code>DataObject</code>, hoặc <code>null</code> 
	 * nếu có lỗi trong quá trình phân tích.
	 */
	public static Data parse(String content) {
		try {
			StringReader reader = new StringReader(content);
			return new DataParser(reader).parseValue();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Data parse(Reader reader) {
		try {
			return new DataParser(reader).parseValue();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Phân tích xâu ký tự biểu diễn đối tượng dữ liệu.
	 * 
	 * @param content Xâu ký tự cần phân tích.
	 * @return Đối tượng <code>DataObject</code>.
	 * @throws ParseException Nếu có lỗi trong quá trình phân tích.
	 */
	public static Data tryParse(String content) throws ParseException {
		StringReader reader = new StringReader(content);
		return new DataParser(reader).parseValue();
	}
	
	public static Data tryParse(Reader reader) throws ParseException {
		return new DataParser(reader).parseValue();
	}
	
	private Reader reader;
	private int ch;
	private int pos;
	
	private DataParser(Reader reader) {
		this.reader = reader;
		this.pos = -1;
		this.ch = -1;
	}
	
	private char next() throws ParseException {
		try {
			this.ch = this.reader.read();
			if (this.ch < 0)
				throw new ParseException("Unexpected end of content", this.pos);
			this.pos++;
			return (char)(this.ch & 0xFFFF);
		} catch (IOException e) {
			throw new ParseException("Unexpected end of content", this.pos);
		}
	}

	public char get() throws ParseException {
		if (this.ch < 0)
			throw new ParseException("Invalid character", this.pos);
		return (char)(this.ch & 0xFFFF);
	}
	
	public String nextString() throws ParseException {
		StringBuilder builder = new StringBuilder();
		boolean cont = true;
		do {
			char ch = get();
			switch (ch) {
			case '\\':
				escape(builder);
				next();
				break;
			case '{': case '}': case ',': case ':': case '[': case ']':
				cont = false;
				break;
			default:
				builder.append(ch);
				next();
				break;
			}
		} while (cont);
		return builder.toString();
	}
	
	private void escape(StringBuilder builder) throws ParseException {
		char ch = next();
		switch (ch) {
		case '{': case '}': case ',': case ':': case '[': case ']': case '\\':
			builder.append(ch);
			break;
		default:
			builder.append('\\');
			builder.append(ch);
			break;
		}
	}
	
	private Value parseValue() throws ParseException {
		char ch = next();
		switch (ch) {
		case '{':
			return parseObject();
		case '[':
			return parseArray();
		default:
			return parseString();
		}
	}
	
	private ObjectValue parseObject() throws ParseException {
		ObjectValue object = new ObjectValue();
		char ch = next();
		if (ch == '}')
			return object;
		
		boolean cont = true;
		do {
			String name = nextString();
			Value value;
			ch = get();
			switch (ch) {
			case ':':
				value = parseValue();
				break;
			default:
				throw new ParseException("Invalid character", this.pos);
			}
			
			object.put(name, value);
			
			if (value instanceof StringValue)
				ch = get();
			else
				ch = next();
			
			switch (ch) {
			case '}':
				cont = false;
				break;
			case ',':
				ch = next();
				break;
			default:
				throw new ParseException("Invalid character", this.pos);
			}
		} while (cont);
		
		return object;
	}
	
	private Value parseArray() throws ParseException {
		Value first = null;
		Value last = null;
		
		Value item;
		char ch = next();
		if (ch == ']')
			return new EmptyValue();
		
		boolean cont = true;
		do {
			switch (ch) {
			case '{':
				item = parseObject();
				break;
			case '[':
				item = parseArray();
				break;
			default:
				item = parseString();
			}
			
			if (first == null) {
				first = item;
				last = item;
			} else {
				last.setNext(item);
				last = item;
			}
			
			if (item instanceof StringValue)
				ch = get();
			else
				ch = next();
			
			switch (ch) {
			case ']':
				cont = false;
				break;
			case ',':
				ch = next();
				break;
			default:
				throw new ParseException("Invalid character", this.pos);
			}
		} while (cont);
		
		if (first instanceof StringValue)
			next();
		
		return first;
	}
	
	private StringValue parseString() throws ParseException {
		return new StringValue(nextString());
	}
	
	private abstract class Value implements Data {
		public Value() {
			this.next = null;
		}

		@Override
		public Data getNext() {
			return this.next;
		}
		
		public void setNext(Value next) {
			this.next = next;
		}

		private Value next;
	}
	
	private class EmptyValue extends Value {
		public EmptyValue() {
			
		}

		@Override
		public String getString(String name) {
			return "";
		}
		
		@Override
		public Data getObject(String name) {
			return null;
		}
		
		@Override 
		public String[] getNames() {
			return new String[] {};
		}
		
		@Override
		public String toString() {
			return "";
		}
	}
	
	private class StringValue extends Value {
		public StringValue(String value) {
			this.value = value;
		}
		
		@Override
		public String getString(String name) {
			return "";
		}
		
		@Override
		public Data getObject(String name) {
			return null;
		}
		
		@Override 
		public String[] getNames() {
			return new String[] {};
		}
		
		@Override 
		public String toString() {
			return this.value;
		}
		
		private String value;
	}
	
	private class ObjectValue extends Value {
		public ObjectValue() {
			this.objects = new HashMap<String, Data>();
		}
		
		@Override 
		public String getString(String name) {
			Data value = this.objects.get(name);
			if (value instanceof StringValue)
				return value.toString();
			else
				return "";
		}
		
		@Override
		public Data getObject(String name) {			
			return this.objects.get(name);
		}
		
		@Override 
		public String[] getNames() {
			return this.objects.keySet().toArray(new String[0]);
		}
		
		@Override
		public String toString() {
			return "";
		}

		public void put(String name, Value value) {
			this.objects.put(name, value);
		}
		
		private HashMap<String, Data> objects;		
	}
}