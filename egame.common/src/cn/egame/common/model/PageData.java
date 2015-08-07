package cn.egame.common.model;

import java.io.Serializable;
import java.util.List;

import cn.egame.common.util.Utils;

public class PageData implements Serializable {
	private static final long serialVersionUID = 4274250070014494469L;

	private int total = 0;

	private int rowsOfPage = 10;

	private Object content;

	private int currentPage = 0;

	public PageData() {
	}

	public PageData(int currentPage, int total, int rowsOfPage) {
		setTotal(total);
		setRowsOfPage(rowsOfPage);
		setCurrentPage(currentPage);
	}

	public <T> PageData(List<T> list, int currentPage, int rowsOfPage) {
		setTotal(list.size());
		setRowsOfPage(rowsOfPage);
		setCurrentPage(currentPage);
		content = Utils.page(list, currentPage, rowsOfPage);
	}

	public Object getContent() {
		return content;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageCount() {
		return Utils.page(this.total, this.rowsOfPage);
	}

	public int getRowsOfPage() {
		return rowsOfPage;
	}

	public int getTotal() {
		return total;
	}

	public boolean hasNext() {
		return getCurrentPage() >= 0 && getCurrentPage() < getPageCount() - 1;
	}

	public boolean hasPrior() {
		return getCurrentPage() < getPageCount();
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public void setCountOfPage(int rowsOfPage) {
		this.rowsOfPage = rowsOfPage;
	}

	public void setCurrentPage(int currentPage) {
		if (currentPage < 0)
			currentPage = 0;

		if (currentPage >= getPageCount() && currentPage > 0)
			currentPage = getPageCount() - 1;

		this.currentPage = currentPage;
	}

	public void setRowsOfPage(int rowsOfPage) {
		this.rowsOfPage = rowsOfPage;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public boolean existPage(int page) {
		return page < getPageCount();
	}
}
