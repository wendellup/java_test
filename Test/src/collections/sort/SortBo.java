package collections.sort;

public class SortBo implements Comparable<SortBo>{
	
	
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
	
	@Override
	public boolean equals(Object obj) {
		SortBo sb = (SortBo) obj;
		return this.gId == sb.gId;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return gId;
	}
	
	@Override
	public String toString() {
		return (this.getGameRelativeNum()*0.6 + this.getDownloadHotNum()*0.4/100 + this.getTagHotNum())+"";
	}

	@Override
	public int compareTo(SortBo o2) {
		int flag = 0;
		if (null != this && null != o2) {
			flag = ((this.getGameRelativeNum()*0.6 + this.getDownloadHotNum()*0.4/100 + this.getTagHotNum()) 
						- (o2.getGameRelativeNum()*0.6 + o2.getDownloadHotNum()*0.4/100 + o2.getTagHotNum())) 
						> 0 ? -1 : 1;
		}
		return flag;
	}
	
}
