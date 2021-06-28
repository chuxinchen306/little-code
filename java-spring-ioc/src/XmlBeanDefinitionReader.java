
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    public void readerXML(String location) throws Exception {
        ResourceLoader resourceLoader = new ResourceLoader();
        InputStream inputStream = resourceLoader.getResource(location).getInputStream();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document doc = documentBuilder.parse(inputStream);
        registerBeanDefinitions(doc);
        inputStream.close();
    }

    private void registerBeanDefinitions(Document doc) {
        Element root = doc.getDocumentElement();
        parseBeanDefinitions(root);
    }


    private void parseBeanDefinitions(Element root) {
        NodeList n1 = root.getChildNodes();
        for (int i = 0; i < n1.getLength(); i++) {
            Node node = n1.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                processBeanDefinition(ele);
            }
        }
    }

    private void processBeanDefinition(Element ele) {
        String name = ele.getAttribute("name");
        String className = ele.getAttribute("class");
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setClassName(className);
        addPropertyValues(ele, beanDefinition);
        getRegistry().put(name, beanDefinition);
    }

    private void addPropertyValues(Element ele, BeanDefinition beanDefinition) {
        NodeList propertyNode = ele.getElementsByTagName("property");
        for (int i = 0; i < propertyNode.getLength(); i++) {
            Node node = propertyNode.item(i);
            if (node instanceof Element) {
                Element propertyEle = (Element) node;
                String name = propertyEle.getAttribute("name");
                String value = propertyEle.getAttribute("value");
                if (value != null && value.length() > 0) {
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
                } else {
                    String ref = propertyEle.getAttribute("ref");
                    if (ref == null || ref.length() == 0) {
                        throw new IllegalArgumentException(
                                "Configure problem:<property> element for property '"
                                        + name + "' must specify a ref or value");

                    }
                    BeanReference beanRef = new BeanReference(name);
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanRef));
                }
            }
        }
    }
}
