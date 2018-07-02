package config

public class ApplicationConfig {
    static public String NameServerApp = "MDFSNameServer";
    static public String NodeServerApp = "MDFSNodeServer";
}

public class DataConfig {
    static public int backupNum = 1;
    static public int blockSize = 64; // in KB
    static public String blockSavingDir = "Blocks";
    static public int minBlocksPerSavingProcess = 10;
}

public class NameServerConfig {
    public static long eurekaCheckCycle = 5 * 1000; // in ms
    public static String fileMappingPersistFile = "fileMapping.json";
}

public class NodeServerConfig {
    public static String blockMappingPersistFile = "blockMapping.json";
    public static long backupCheckCycle = 5 * 1000; // in ms
    public static long backupInitWaitTime = 10 * 1000; // in ms
}

public class ProtoConfig {
    static public String pathSep = "/";
    static public String objFile = "File";
    static public String objDir = "Dir";
}