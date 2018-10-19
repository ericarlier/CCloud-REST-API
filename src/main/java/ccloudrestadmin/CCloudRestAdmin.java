package ccloudrestadmin;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
public class CCloudRestAdmin {
    
    @RequestMapping(method = RequestMethod.GET, value="/topics")
    public List<Topic> getTopics() {
        CCloudAdminConnect admin = CCloudAdminConnect.getInstance();
        // List all topics
        if (admin != null)
            return admin.getTopicsList();
          
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value="/topics")
    public String createTopic(@RequestBody Topic topic) {
        CCloudAdminConnect admin = CCloudAdminConnect.getInstance();
        if (admin != null)
            return admin.createTopic(topic);     
        return "FAIL";
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/topics")
    public String deleteTopic(@RequestParam(value="name") String name ) {
        CCloudAdminConnect admin = CCloudAdminConnect.getInstance();
        if (admin != null)
        {
            if (name != null && !name.isEmpty())
                return admin.deleteTopic(name);   
            return "FAIL : Missing Mandatory parameter [name]";
        }    
        return "FAIL";
    }

}
