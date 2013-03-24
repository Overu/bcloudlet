package javax.ws.rs.core;

import java.net.URI;
import java.util.List;

public interface UriInfo {
    public MultivaluedMap<String, String> getQueryParameters();
    public MultivaluedMap<String, String> getQueryParameters(boolean decode);
}
