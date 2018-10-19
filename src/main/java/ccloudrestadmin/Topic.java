package ccloudrestadmin;

public class Topic {

    private final String name;
    private int partitions;
    private short replicationFactor;

    public Topic(String name) {
        this.name = name;
        this.partitions = 3;
        this.replicationFactor = 3;
    }

    public Topic(String name, int partitions, short replicationFactor) {
        this.name = name;
        this.partitions = partitions;
        this.replicationFactor = replicationFactor;
    }

    public String getName() {
        return name;
    }

    public int getPartitions() {
        return partitions;
    }
    public short getReplicationFactor() {
        return replicationFactor;
    }
}