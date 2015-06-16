/**   
 * 文件名：webUtil.java
 * 创建日期：2015-3-25   
 * Copyright (c) 2015 by Peter.版权所有.
 */

package com.example.popuandviewpagedemo.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 项目名称：ListViewFrashDemo1<br>
 * 类名称：webUtil <br>
 * 类描述： TODO(请输入类的描述) <br>
 * 创建人：Peter(李春福) <br>
 * 创建时间：2015-3-25 上午9:56:10 <br>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注：
 * 
 * @version V1.0
 */

public class WebUtil {
	public static String getStr(InputStream is) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		is.close();
		bos.flush();
		byte[] result = bos.toByteArray();
		System.out.println(new String(result));
		return new String(result);
	}
}
