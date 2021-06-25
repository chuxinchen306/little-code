import java.net.URL;

public class ResourceLoader {

    public ResourceUrl getResource(String location) {
        URL url = this.getClass().getClassLoader().getResource(location);
        return new ResourceUrl(url);
    }
}
