package alogic.markov.util;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	
	public static String readFile(MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				return new String(bytes);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}
}
