/**   
 * �ļ�����webUtil.java
 * �������ڣ�2015-3-25   
 * Copyright (c) 2015 by Peter.��Ȩ����.
 */

package com.example.popuandviewpagedemo.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * ��Ŀ���ƣ�ListViewFrashDemo1<br>
 * �����ƣ�webUtil <br>
 * �������� TODO(�������������) <br>
 * �����ˣ�Peter(���) <br>
 * ����ʱ�䣺2015-3-25 ����9:56:10 <br>
 * �޸��ˣ� <br>
 * �޸�ʱ�䣺 <br>
 * �޸ı�ע��
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
