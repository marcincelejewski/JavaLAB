package plugins;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MyClassLoader extends ClassLoader {
	
	private static MyClassLoader _instance;
	public static MyClassLoader Instance() {
		if(_instance == null)
			_instance = new MyClassLoader();

		return _instance;
	}
	//for name
	public static void Unload() {
		_instance = null;
	}
	
	@Override
	public Class<?> findClass(String name) {
		Class<?> c = findLoadedClass(name);
		if(c==null)
		{
			byte[] bt = loadClassData(name);
			System.out.println("Za³adowano now¹ klase");
			return defineClass(name, bt, 0, bt.length);			
		}
		System.out.println("Znaleziono klase");
		return c;
		
	}

	private byte[] loadClassData(String className) {
		// read class
		InputStream is = getClass().getClassLoader().getResourceAsStream(className.replace(".", "/") + ".class");
		ByteArrayOutputStream byteSt = new ByteArrayOutputStream();
		// write into byte
		int len = 0;
		try {
			while ((len = is.read()) != -1) {
				byteSt.write(len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// convert into byte array
		return byteSt.toByteArray();
	}

}