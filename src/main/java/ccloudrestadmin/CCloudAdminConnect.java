package ccloudrestadmin;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.KafkaFuture;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

class CCloudAdminConnect 
{ 
    // static variable single_instance of type Singleton 
    private static CCloudAdminConnect single_instance = null; 
  
    // variable of type String 
    private AdminClient admin; 
  
    // private constructor restricted to this class itself 
    private CCloudAdminConnect() 
    { 
        admin = null;
        // Load ccloud config file
        Properties properties = new Properties();

        try {
            String cfgFileName = System.getenv("CCLOUD_CONFIG");
            if (cfgFileName == null || cfgFileName.isEmpty())
            {
                cfgFileName = System.getenv("HOME") + "/.ccloud/config";
            }
			File file = new File(cfgFileName);
			FileInputStream fileInput = new FileInputStream(file);
			properties.load(fileInput);
			fileInput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
        }
        
        admin = AdminClient.create(properties);
    } 
  
    // static method to create instance of Singleton class 
    public static CCloudAdminConnect getInstance() 
    { 
        if (single_instance == null) 
            single_instance = new CCloudAdminConnect(); 
  
        return single_instance; 
    } 

    public List<Topic> getTopicsList()
    {
        if (admin != null)
        {
            try {
                Collection<TopicListing> listing =  admin.listTopics().listings().get();
                List<Topic> topicList = new ArrayList<>();
                for (TopicListing t : listing) {
                    if (!t.isInternal()) {
                        TopicDescription desc = admin.describeTopics(Collections.singleton(t.name())).values().get(t.name()).get();
                        short nr = (short)desc.partitions().get(0).replicas().size();
                        topicList.add(new Topic(t.name(),desc.partitions().size(),nr));
                    }
                        
                }
                return topicList;
            }
            catch(InterruptedException | ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        
        return null;
    }

    public String createTopic(Topic topic)
    {
        if (admin != null)
        {
            final NewTopic newTopic = new NewTopic(topic.getName(),topic.getPartitions(), topic.getReplicationFactor());
            final CreateTopicsResult createTopicsResult = admin.createTopics(Collections.singleton(newTopic));

            KafkaFuture<Void> all = createTopicsResult.all();

            try {
                all.get();
                return "OK";
            } catch (InterruptedException | ExecutionException e) {
                return "FAIL : " + e.getLocalizedMessage();
            }
        
        }
        
        return "FAIL : UNKNOWN ERROR";
    }

    public String deleteTopic(String name)
    {
        if (admin != null)
        {
            final DeleteTopicsResult deleteTopicsResult = admin.deleteTopics(Collections.singleton(name));

            KafkaFuture<Void> all = deleteTopicsResult.all();

            try {
                all.get();
                return "OK";
            } catch (InterruptedException | ExecutionException e) {
                return "FAIL : " + e.getLocalizedMessage();
            }
        
        }
        
        return "FAIL : UNKNOWN ERROR";
    }

} 