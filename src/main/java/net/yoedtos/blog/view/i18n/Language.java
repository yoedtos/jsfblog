package net.yoedtos.blog.view.i18n;

public enum Language {
	EN("English"),
	PT("Português"),
	JA("日本語");

	private String label;

	Language(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
