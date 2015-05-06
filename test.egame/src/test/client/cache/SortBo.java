package test.client.cache;

import java.io.Serializable;

public class SortBo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3929055244441250445L;
	private int gId;
	private double gameRelativeNum;
	private double downloadHotNum;
	private int tagHotNum;

	public SortBo(int gId, double gameRelativeNum, double downloadHotNum,
			int tagHotNum) {
		super();
		this.gId = gId;
		this.gameRelativeNum = gameRelativeNum;
		this.downloadHotNum = downloadHotNum;
		this.tagHotNum = tagHotNum;
	}

	public int getgId() {
		return gId;
	}

	public void setgId(int gId) {
		this.gId = gId;
	}

	public double getGameRelativeNum() {
		return gameRelativeNum;
	}

	public void setGameRelativeNum(double gameRelativeNum) {
		this.gameRelativeNum = gameRelativeNum;
	}

	public double getDownloadHotNum() {
		return downloadHotNum;
	}

	public void setDownloadHotNum(double downloadHotNum) {
		this.downloadHotNum = downloadHotNum;
	}

	public int getTagHotNum() {
		return tagHotNum;
	}

	public void setTagHotNum(int tagHotNum) {
		this.tagHotNum = tagHotNum;
	}
	
//	@Override
//	public int hashCode() {
//		int prime = 31;
//        int result = 1;
//        result = 31 * result + gId;
//        return result;
//	}
	
}
