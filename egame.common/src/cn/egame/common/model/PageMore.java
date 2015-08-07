package cn.egame.common.model;

import java.io.Serializable;

public class PageMore implements Serializable {
	private static final long serialVersionUID = -9193358870514416845L;

	private int baseId;

	private Object content;

	private boolean more = false;
	
	private EnumCommon.FetchType fetchType = EnumCommon.FetchType.big;

	private int rowsOfPage = 10;

	public PageMore() {
	}

	public PageMore(int baseId, boolean hasMore, int rowsOfPage) {
		this.baseId = baseId;
		this.more = hasMore;
		this.rowsOfPage = rowsOfPage;
	}

	public int getBaseId() {
		return baseId;
	}

	public Object getContent() {
		return content;
	}

	public EnumCommon.FetchType getFetchType() {
		return fetchType;
	}

	public int getRowsOfPage() {
		return rowsOfPage;
	}

	public boolean hasMore() {
		return more;
	}

	public boolean isMore() {
		return more;
	}

	public void setBaseId(int baseId) {
		this.baseId = baseId;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public void setCountOfPage(int rowsOfPage) {
		this.rowsOfPage = rowsOfPage;
	}

	public void setFetchType(EnumCommon.FetchType fetchType) {
		this.fetchType = fetchType;
	}

	public void setMore(boolean more) {
		this.more = more;
	}

	public void setRowsOfPage(int rowsOfPage) {
		this.rowsOfPage = rowsOfPage;
	}
}
