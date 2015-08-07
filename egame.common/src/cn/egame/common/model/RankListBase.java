package cn.egame.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.egame.common.util.Utils;

public class RankListBase<T1, T2, T3> implements java.io.Serializable {
	private byte[] SyncRoot = new byte[1];
	private boolean isUniq = false;

	public boolean isUniq() {
		return isUniq;
	}

	public void setUniq(boolean isUniq) {
		this.isUniq = isUniq;
		if (this.isUniq) {
			uniq();
		} else
			map.clear();
	}

	protected List<RankBase<T1, T2, T3>> list = new ArrayList<RankBase<T1, T2, T3>>();
	protected Map<T1, RankBase<T1, T2, T3>> map = new HashMap<T1, RankBase<T1, T2, T3>>();

	public RankListBase() {

	}

	public RankListBase(boolean uniq) {
		this.isUniq = uniq;
	}

	public void sort() {
		synchronized (SyncRoot) {
			List<RankBase<T1, T2, T3>> temp = new ArrayList<RankBase<T1, T2, T3>>();
			for (RankBase<T1, T2, T3> item : list) {
				int index = this.rankSort(temp, item.getSort());
				if (index >= 0)
					list.add(index, item);
				else
					list.add(item);
			}
			list = temp;
		}
	}

	public int rankSort(List<RankBase<T1, T2, T3>> l, T2 sort) {

		if (l.size() < 1)
			return 0;

		long sc = Utils.toLong(sort, 0);
		RankBase<T1, T2, T3> item = (RankBase<T1, T2, T3>) l.get(0);
		if (sc >= Utils.toLong(item.getSort(), 0))
			return 0;

		item = (RankBase<T1, T2, T3>) l.get(list.size() - 1);
		if (Utils.toLong(item.getSort(), 0) > sc)
			return -1;

		long s = 0L;
		int left = 0, right = list.size() - 1, mid = (int) Math
				.ceil((left + right) / 2.0);
		for (;right - left > 1; mid = (int) Math
				.ceil((left + right) / 2.0)) {
	
			item = l.get(mid);
			s = Utils.toLong(item.getSort(), 0);
			if (sc >= s)
				right = mid;
			else
				left = mid;
		}

		item = l.get(mid);
		s = Utils.toLong(item.getSort(), 0);
		if (sc <= s)
			mid = right;
		return mid;
	}

	private void uniq() {
		for (int i = list.size(); i >= 0; i--) {
			RankBase b = (RankBase) list.get(i);
			if (!map.containsKey(b.getId())) {
				this.add(b);
			}
		}
	}

	public RankBase get(T1 id) {
		if (isUniq)
			return map.get(id);

		for (RankBase item : list) {
			if (item.getId() == id)
				return item;
		}
		return null;
	}

	public int indexOf(RankBase o) {
		return list.indexOf(o);
	}

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public boolean contains(RankBase<T1, T2, T3> o) {
		return list.contains(o);
	}

	public boolean contains(T1 id) {
		return map.containsKey(id);
	}

	public Iterator iterator() {
		return list.iterator();
	}

	public void add(RankBase<T1, T2, T3> o) {
		if (o != null) {
			synchronized (SyncRoot) {
				int index = rankSort(list, o.getSort());
				if (isUniq) {
					if (!map.containsKey(o.getId())) {
						map.put(o.getId(), o);
						if (index >= 0)
							list.add(index, o);
						else
							list.add(o);
					}
				} else {
					if (index >= 0)
						list.add(index, o);
					else
						list.add(o);
				}

			}
		}
	}

	public void remove(RankBase<T1, T2, T3> o) {
		if (o != null) {
			synchronized (SyncRoot) {
				if (isUniq) {
					if (!map.containsKey(o.getId())) {
						map.remove(o.getId());
						list.remove(o);
					}
				} else
					list.remove(o);
			}
		}
	}

	public void clear() {
		synchronized (SyncRoot) {
			if (isUniq)
				map.clear();
			list.clear();
		}
	}

	public static void main(String[] args) {
		RankListBase<Integer, Integer, Object> list = new RankListBase<Integer, Integer, Object>();
		RankBase<Integer, Integer, Object> o = null;
		for (int i = 0; i < 1000; i++) {
			o = new RankBase<Integer, Integer, Object>();
			o.setId(i);
			o.setSort(Utils.getRandom(1, 100));
			o.setObj(true);
			System.out.println("id=" + o.getId() + "\tsort=" + o.getSort());
			list.add(o);

			if (i > 4) {

			}
		}

		System.out.println("#########################");
		RankBase<Integer, Integer, Object> temp = null;

		Iterator it = list.iterator();
		while (it.hasNext()) {
			RankBase<Integer, Integer, Object> os = (RankBase<Integer, Integer, Object>) it
					.next();
			if (temp!=null && temp.getSort()<os.getSort())
			{
				int kkk=0;
				
				
				kkk+=1;
			}
			
			System.out.println("id=" + os.getId() + "\tsort=" + os.getSort());
			temp = os;
		}
		System.out.println("-------------------------");

	}
}
