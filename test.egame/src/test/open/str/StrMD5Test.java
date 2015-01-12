package test.open.str;

import java.security.NoSuchAlgorithmException;

import cn.egame.common.util.Utils;

public class StrMD5Test {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		String srcStr = "9game_wap_frienddateFrom=20130101000000dateTo=20140101000000pageNum=1pageSize=100syncEntity=game,package,platformsyncField=game.id,game.name,game.cpId,game.typeId,package.downUrl,platform.logoImageUrl,platform.versionsyncType=17142bfe56e43c4594baaa54bf248a92f";
		System.out.println(Utils.encryptMD5(srcStr));
		
//		e4e2eef72e3cc468e5ab5c837210fb46
//		e4e2eef72e3cc468e5ab5c837210fb46
	}
}	
