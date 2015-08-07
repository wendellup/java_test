package Test.mongoTest;

import cn.egame.common.dataserver.BaseDBObj;
import cn.egame.common.dataserver.ann.DB_FIELD;
import cn.egame.common.dataserver.ann.DB_TABLE;

@DB_TABLE(tableName = "user")
public class User extends BaseDBObj {
	

	@DB_FIELD(fieldName="uid1")
	private long uid;
	@DB_FIELD(fieldName="name_1")
	private String name;
	@DB_FIELD(fieldName="gendar_1")
	private boolean gendar;
	@DB_FIELD(fieldName="age_1")
	private int age;

	@Override
	public String[] getUninId() {
		String[] ids = { "uid", uid + "" };
		return ids;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isGendar() {
		return gendar;
	}

	public void setGendar(boolean gendar) {
		this.gendar = gendar;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", name=" + name + ", gendar=" + gendar + ", age=" + age + "]";
	}
}
