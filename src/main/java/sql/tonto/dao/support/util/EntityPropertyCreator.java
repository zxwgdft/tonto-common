package sql.tonto.dao.support.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class EntityPropertyCreator {
	
	/**
	 * 创建数据库实例的属性常量类
	 * @param entityPackage		entity所在包（需要hbm.xml配置文件）
	 * @param toPackage			生成类存放的包
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void createEntityPropertyConstant(String entityPackage,String toPackage) throws DocumentException,
			IOException {

		String basePath = System.getProperty("user.dir") + "/src/"
				+ entityPackage.replaceAll("\\.", "/");
		File file = new File(basePath);
		File[] files = file.listFiles();
		for (File f : files) {
			if (!f.isDirectory()) {
				String filename = f.getName();
				if (filename.endsWith(".hbm.xml")) {
					String className = filename.substring(0,
							filename.indexOf("."))
							+ "Property";
					createConstant2Java(f, toPackage, className);
				}
			}
		}
	}
	
	

	@SuppressWarnings("unchecked")
	public static void createConstant2Java(File xml, String packageName,
			String className) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(xml);

		StringBuilder content = new StringBuilder();

		content.append("package ").append(packageName).append(";\r\n\r\n\r\n");
		content.append("public class ").append(className).append(" {\r\n\r\n");

		Element classElement = document.getRootElement().element("class");

		Element idElement = classElement.element("id");
		if (idElement != null) {
			String value = idElement.attributeValue("name");
			String name = idElement.element("column").attributeValue("name");
			content.append("public static final String ").append(name)
					.append(" = \"").append(value).append("\";\r\n\r\n");
		}

		List<Element> propertyElements = classElement.elements("property");
		for (Element propertyElement : propertyElements) {
			String value = propertyElement.attributeValue("name");
			String name = propertyElement.element("column").attributeValue(
					"name");
			content.append("public static final String ").append(name)
					.append(" = \"").append(value).append("\";\r\n\r\n");
		}

		List<Element> foreignKeyElements = classElement.elements("many-to-one");
		for (Element foreignKeyElement : foreignKeyElements) {
			String value = foreignKeyElement.attributeValue("name");

			StringBuilder sb = new StringBuilder();
			char[] chars = value.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				char c = chars[i];
				if (c <= 'Z' && c >= 'A') {
					sb.append("_").append(c);
				} else if (c >= 'a' && c <= 'z') {
					c = (char) (c - 32);
					sb.append(c);
				} else {
					sb.append(c);
				}

			}

			String name = sb.toString();

			String foreignId = foreignKeyElement.element("column")
					.attributeValue("name");
			foreignId = foreignId.toLowerCase();
			sb = new StringBuilder();
			chars = foreignId.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				char c = chars[i];
				if (c == '_') {
					if (++i > chars.length)
						break;
					c = (char) (chars[i] - 32);
					sb.append(c);
				} else {
					sb.append(c);
				}

			}
			value += "." + sb.toString();
			content.append("public static final String ").append(name)
					.append(" = \"").append(value).append("\";\r\n\r\n");
		}

		content.append("}");

		String outpath = System.getProperty("user.dir") + "/src/"
				+ packageName.replaceAll("\\.", "/") + "/" + className
				+ ".java";
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(outpath);
			out.write(content.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
