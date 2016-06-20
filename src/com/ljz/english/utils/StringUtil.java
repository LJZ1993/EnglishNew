package com.ljz.english.utils;

import java.util.Random;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import android.R.string;
import android.util.Log;
import android.util.Xml.Encoding;

public class StringUtil {
	private static final String APPID = "2015063000000001";
	private static final String TOKEN = "w2V879JoSEgOe0ntrfsn";
	public final static String ZH = "zh";
	public final static String EN = "en";
	public final static String JP = "jp";
	public final static String KOR = "kor";
	public final static String CHT = "cht";
	public final static String AUTO = "auto";

	// http://api.fanyi.baidu.com/api/trans/vip/translate?q=apple&from=en&to=zh&appid=2015063000000001&salt=1435660288&sign=f89f9594663708c1605f3d736d01d2d4
	// http://api.fanyi.baidu.com/api/trans/vip/translate?q=apple&from=auto&to=zh&appid=20160527000022230&salt=78156468&sign=22cb75ff664f978c83e90fe91a0836a4
	public static String getUrl(String write, String form, String to) {
		// 获取随机数
		Random random = new Random();
		int salt = random.nextInt(100000000);
		// 对appId+源文+随机数+token计算md5值
		StringBuilder md5String = new StringBuilder();
		md5String.append(APPID).append(write).append(salt).append(TOKEN);
		// String md5 = DigestUtils.md5Hex(md5String.toString());
		String md5 = new String(Hex.encodeHex(DigestUtils.md5(md5String
				.toString())));
		String url = "http://api.fanyi.baidu.com/api/trans/vip/translate?q="
				+ write + "&from=" + form + "&to=" + to
				+ "&appid=20160527000022230&salt=" + salt + "&sign=" + md5;
		Log.w("url", url);
		return url;
	}

}
