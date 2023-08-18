package net.yoedtos.blog.control.validator;

public class MimeType {

	enum Image {
		JPG("image/jpeg"),
		PNG("image/png"),
		GIF("image/gif");
		
		private Image (String contentType) {
			this.contentType = contentType;
		}
		
		private String contentType;
		
		public String getContentType() {
			return contentType;
		}
	}

	enum Video {
		MP4("video/mp4"),
		MPEG("video/mpeg"),
		SWF("application/x-shockwave-flash"),
		WEBM("video/webm");
	
		private Video(String contentType) {
			this.contentType = contentType;
		}
		
		private String contentType;
		
		public String getContentType() {
			return contentType;
		}
	}

	enum Document {
		TXT("text/plain"),
		PDF("application/pdf"),
		EPUB("application/epub+zip");
		
		private Document(String contentType) {
			this.contentType = contentType;
		}
		
		private String contentType;
		
		public String getContentType() {
			return contentType;
		}
	}
	
	enum Compress {
		GZ("application/gzip"),
		ZIP("application/zip"),
		RAR("application/vnd.rar"),
		_7ZIP("application/x-7z-compressed");
		
		private Compress(String contentType) {
			this.contentType = contentType;
		}
		
		private String contentType;
		
		public String getContentType() {
			return contentType;
		}
	}
}
