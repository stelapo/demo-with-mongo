package demo.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-11-27T12:30:07.687+01:00[Europe/Berlin]")
@Controller
@RequestMapping("${openapi.simpleUser.base-path:/lapo.pancani/lpancaniUser01/1.0.0}")
public class UsersApiController implements UsersApi {

    private final NativeWebRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

}
