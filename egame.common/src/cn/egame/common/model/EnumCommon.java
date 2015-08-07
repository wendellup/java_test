package cn.egame.common.model;

import java.util.TreeMap;

/**
 * 
 * @Copyright 爱游戏
 * 
 * @Project egame.common
 * 
 * @Author WF
 * 
 * @timer 2013-1-24
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */

public class EnumCommon {
	public enum FetchType {
		big(1), small(2);
		int _value = 0;

		private static TreeMap<Integer, FetchType> _map;

		static {
			_map = new TreeMap<Integer, FetchType>();
			for (FetchType num : FetchType.values()) {
				_map.put(new Integer(num.value()), num);
			}
		}

		public static FetchType lookup(int value) {
			return _map.get(new Integer(value));
		}
		
		FetchType(int value) {
			this._value = value;
		}
		
		public int value() {
			return this._value;
		}
	}
}
