package cn.egame.common.web;

import cn.egame.common.exception.ErrorCodeBase;
import cn.egame.common.exception.ExceptionCommonBase;


public class ExceptionWeb extends ExceptionCommonBase {
	private static final long serialVersionUID = -4874070287260476548L;

	public ExceptionWeb(String message) {
		super(ErrorCodeBase.ExceptionWeb, message);
	}

}
