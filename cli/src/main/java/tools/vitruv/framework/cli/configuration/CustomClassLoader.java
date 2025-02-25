package tools.vitruv.framework.cli.configuration;

import java.net.URL;
import java.net.URLClassLoader;

public class CustomClassLoader extends URLClassLoader {
  public CustomClassLoader(URL[] urls, ClassLoader parent) {
    super(urls, parent);
}

public void addJar(URL url) {
    this.addURL(url);
}
}
